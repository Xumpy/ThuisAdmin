package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.services.implementations.yuki.model.*;
import com.xumpy.documenprovider.services.model.DocumentProviderDocumentsSrvPojo;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragAccountingDaoPojo;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class DocumentProviderYuki implements DocumentProviderSrv {
    @Autowired YukiBuilder yukiBuilder;
    @Autowired PostToYuki postToYuki;
    @Autowired GetAccountInfoFromYuki getAccountInfoFromYuki;
    @Autowired BedragenDaoImpl bedragenDao;
    @Autowired UploadDocumentFromYuki uploadDocumentFromYuki;

    private static final Integer NO_BEDRAG_FOUND = 0;
    private static final Integer MULTIPLE_BEDRAGEN_FOUND = -1;
    private static final Integer IGNORE_BEDRAG_FOUND = -2;

    public void setDaoWiring(DocumentProviderDocumentsImpl documentProviderDocuments, BedragAccountingDaoImpl bedragAccounting) {
        this.documentProviderDocuments = documentProviderDocuments;
        this.bedragAccounting = bedragAccounting;
    }

    @Autowired DocumentProviderDocumentsImpl documentProviderDocuments;
    @Autowired BedragAccountingDaoImpl bedragAccounting;

    @Override
    public Integer getDocumentProviderId() {
        return 1;
    }

    @Override
    public String process(Documenten document) {
        YukiPojo yukiPojo = yukiBuilder.build();
        yukiPojo.setFolder(Folder.ZelfTeOrdenen);
        if (document.getBedrag().getGroep().getCategory() != null){
            yukiPojo.getFolder().setOrdner(document.getBedrag().getGroep().getCategory());
        }
        yukiPojo.setFileName(document.getBedrag().getOmschrijving().replaceAll(" ", "") + ".pdf");
        yukiPojo.setFile(new ByteArrayInputStream(document.getDocument()));

        try {
            UploadReponse uploadReponse = postToYuki.post(yukiPojo);

            return "Yuki ID: " + uploadReponse.getDocumentID();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getDumpFromDocumentProvider(Date startDate, Date endDate) {
        try {
            String sessionId = getAccountInfoFromYuki.getYukiSession();

            return getAccountInfoFromYuki.getTransactionInfo(sessionId, startDate, endDate).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error occured while get dump from document provider");
        }
    }

    private Integer determineTransactionBedragByDocument(TransactionInfo transactionInfo){
        List<DocumentProviderDocumentsDaoPojo> documentProviderDocumentsDaoPojos = documentProviderDocuments.getDocumentProviderDocumentsByFeedback(transactionInfo.getDocumentID());

        if (documentProviderDocumentsDaoPojos.size() == 0){
            return NO_BEDRAG_FOUND;
        } else if (documentProviderDocumentsDaoPojos.size() > 1){
            return MULTIPLE_BEDRAGEN_FOUND;
        } else  {
            return documentProviderDocumentsDaoPojos.get(0).getDocumenten().getBedrag().getPk_id();
        }
    }

    private Integer determineTransactionBedragByDateAndBedrag(String sessionId, TransactionInfo transactionInfo) throws JAXBException, IOException {
        Date transactionDate = transactionInfo.getTransactionDate();
        BigDecimal transactionAmount = transactionInfo.getTransactionAmount().abs();

        LocalDate localTransactionDate = transactionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date startDate = java.util.Date.from(localTransactionDate.withDayOfMonth(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = java.util.Date.from(localTransactionDate.withDayOfMonth(localTransactionDate.lengthOfMonth()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        List<BedragenDaoPojo> bedragen = bedragenDao.getBedragByBedragInPeriod(transactionAmount, startDate, endDate, true);
        if (bedragen.size() == 0){
            return NO_BEDRAG_FOUND;
        } else if (bedragen.size() > 1){
            Integer bedragId = NO_BEDRAG_FOUND;
            for(BedragenDaoPojo bedrag: bedragen){
                if (bedrag.getDatum().equals(transactionDate)){
                    if (bedragId != NO_BEDRAG_FOUND) return MULTIPLE_BEDRAGEN_FOUND;
                    bedragId = bedrag.getPk_id();
                }
            }
            return bedragId;
        } else  {
            if (transactionInfo.getDocumentID() != null && sessionId != null){
                TransactionDocument transactionDocument = getAccountInfoFromYuki.getTransactionDocument(sessionId, transactionInfo.getId());
                if (transactionDocument.getFiledata() != null){
                    uploadDocumentFromYuki.upload(transactionInfo, transactionDocument, bedragen.get(0));
                }
            }
            return bedragen.get(0).getPk_id();
        }
    }

    private Integer determineTransactionBedragPkId(String sessionId, TransactionInfo transactionInfo) throws JAXBException, IOException {
        Integer byDocumentId = determineTransactionBedragByDocument(transactionInfo);
        if (byDocumentId != NO_BEDRAG_FOUND && byDocumentId != MULTIPLE_BEDRAGEN_FOUND){
            return byDocumentId;
        }
        return determineTransactionBedragByDateAndBedrag(sessionId, transactionInfo);
    }

    private Integer findBedragPerDocumentId(Map<Integer, List<TransactionInfo>> bedragIDMap, String documentId){
        for(Map.Entry<Integer, List<TransactionInfo>> bedragIdEntry: bedragIDMap.entrySet()){
            for(TransactionInfo transactionInfo: bedragIdEntry.getValue()){
                if (transactionInfo.getDocumentID() != null && transactionInfo.getDocumentID().equals(documentId) && bedragIdEntry.getKey() != NO_BEDRAG_FOUND){
                    return bedragIdEntry.getKey();
                }
            }
        }
        return NO_BEDRAG_FOUND;
    }

    private Map<Integer, List<TransactionInfo>> remapPerBedragID(Map<Integer, List<TransactionInfo>> bedragIDMap){
        List<TransactionInfo> transactionInfos = bedragIDMap.get(NO_BEDRAG_FOUND);
        List<TransactionInfo> remappedTransactions = new ArrayList<>();
        for(TransactionInfo transactionInfo: transactionInfos){
            Integer bedragId = findBedragPerDocumentId(bedragIDMap, transactionInfo.getDocumentID());
            if (bedragId != NO_BEDRAG_FOUND){
                bedragIDMap.get(bedragId).add(transactionInfo);
                remappedTransactions.add(transactionInfo);
            }
        }
        transactionInfos.removeAll(remappedTransactions);

        return bedragIDMap;
    }

    private Boolean ignoreTransaction(TransactionInfo transactionInfo){
        if (transactionInfo.getGlAccountCode().equals("550001")) return true; // VISA transactions
        if (transactionInfo.getGlAccountCode().equals("580000")) return true; // VISA transactions

        return false;
    }

    private Map<Integer, List<TransactionInfo>> mapPerBedragID(String sessionId, ArrayOfTransactionInfo arrayOfTransactionInfo) throws JAXBException, IOException {
        Map<Integer, List<TransactionInfo>> bedragIDMap = new HashMap<>();

        for(TransactionInfo transactionInfo: arrayOfTransactionInfo.getTransactionInfo()){
            Integer bedragPkId;

            if (ignoreTransaction(transactionInfo)){
                bedragPkId = IGNORE_BEDRAG_FOUND;
            } else {
                bedragPkId = determineTransactionBedragPkId(sessionId, transactionInfo);
            }

            if (bedragIDMap.containsKey(bedragPkId)){
                bedragIDMap.get(bedragPkId).add(transactionInfo);
            } else {
                List<TransactionInfo> transactionInfos = new ArrayList<>();
                transactionInfos.add(transactionInfo);
                bedragIDMap.put(bedragPkId, transactionInfos);
            }
        }

        return remapPerBedragID(bedragIDMap);
    }

    private void processBedragAccounting(Integer bedragId, List<TransactionInfo> transactionInfos){
        List<BedragAccountingDaoPojo> cleanupAccountingBedragen = bedragAccounting.getAccountantBedragenByBedrag(bedragId);
        for(BedragAccountingDaoPojo cleanupAccountingBedrag: cleanupAccountingBedragen){
            bedragAccounting.delete(cleanupAccountingBedrag);
        }

        for(TransactionInfo transactionInfo: transactionInfos){
            BedragAccountingDaoPojo bedragAccountingDaoPojo = new BedragAccountingDaoPojo();
            bedragAccountingDaoPojo.setBedrag(bedragenDao.findById(bedragId).get());
            bedragAccountingDaoPojo.setDatum(transactionInfo.getTransactionDate());
            bedragAccountingDaoPojo.setAccountBedrag(transactionInfo.getTransactionAmount());
            bedragAccountingDaoPojo.setAccountCode(transactionInfo.getGlAccountCode());
            bedragAccountingDaoPojo.setOmschrijving(transactionInfo.getDescription());
            bedragAccountingDaoPojo.setCustomerName(transactionInfo.getFullName());
            bedragAccountingDaoPojo.setVatNumber(transactionInfo.getVatNumber());
            bedragAccountingDaoPojo.setTaxDescription(transactionInfo.getTaxCodeDescription());

            bedragAccounting.save(bedragAccountingDaoPojo);
        }
    }

    @Override
    public String processDumpToBedragAccounting(String dump) {
        String sessionId = null;
        try {
            sessionId = getAccountInfoFromYuki.getYukiSession();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            Map<Integer, List<TransactionInfo>> mapPerBedragID = mapPerBedragID(sessionId, new ArrayOfTransactionInfo(dump));

            List<TransactionInfo> unmatchedTransactions = new ArrayList<>();
            List<TransactionInfo> multipelBedragenTransactions = new ArrayList<>();
            List<TransactionInfo> ignoreBedragenTransactinos = new ArrayList<>();

            Integer matchedTransactions = 0;

            for(Map.Entry<Integer, List<TransactionInfo>> documentIdEntry: mapPerBedragID.entrySet()){
                Integer bedragId = documentIdEntry.getKey();

                if (bedragId == NO_BEDRAG_FOUND){
                    unmatchedTransactions.addAll(documentIdEntry.getValue());
                } else if (bedragId == MULTIPLE_BEDRAGEN_FOUND) {
                    multipelBedragenTransactions.addAll(documentIdEntry.getValue());
                } else if (bedragId == IGNORE_BEDRAG_FOUND){
                    ignoreBedragenTransactinos.addAll(documentIdEntry.getValue());
                } else {
                    matchedTransactions = matchedTransactions + documentIdEntry.getValue().size();
                    processBedragAccounting(bedragId, documentIdEntry.getValue());
                }
            }

            return generateReport(unmatchedTransactions, multipelBedragenTransactions, ignoreBedragenTransactinos, matchedTransactions);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private  String createOutputtext(String remark, TransactionInfo transactionInfo){
        return remark + " at " + transactionInfo.getTransactionDate() +
                " for bedrag: " + transactionInfo.getTransactionAmount() +
                " for document ID: " + transactionInfo.getDocumentID() +
                " with glAccountCode: " + transactionInfo.getGlAccountCode() +
                " with customer: " + transactionInfo.getFullName() +
                " with vat number: " + transactionInfo.getVatNumber() +
                " with description: " + transactionInfo.getDescription().replace("\r", "").replace("\n", " ") +
                "\n";
    }
    private String generateReport(List<TransactionInfo> unmatchedTransactions,
                                  List<TransactionInfo> multipelBedragenTransactions,
                                  List<TransactionInfo> ignoreBedragenTransactinos,
                                  Integer matchedTransactions){


        String report = "------- Generated Report ------ \n";

        for(TransactionInfo transactionInfo: unmatchedTransactions){
            report = report + createOutputtext("No match",  transactionInfo);
        }

        for(TransactionInfo transactionInfo: multipelBedragenTransactions){
            report = report + createOutputtext("Multiple matches",  transactionInfo);
        }

        for(TransactionInfo transactionInfo: ignoreBedragenTransactinos){
            report = report + createOutputtext("Ignored transactions",  transactionInfo);
        }

        report = report + "matched transactions: " + matchedTransactions + "\n";
        report = report + "------------------------------------";

        return report;
    }

    @Override
    public List<DPDocument> updateFeedback(Documenten document, Map securityKeys) throws PinNotValidException {
        throw new RuntimeException("Not supported for yuki");
    }

    @Override
    public void updateAccountingBedragen(DocumentProviderDocumentsSrvPojo documentProviderDocumentsSrvPojo, Map securityKeys) throws PinNotValidException {
        throw new RuntimeException("Not supported for yuki");
    }
}
