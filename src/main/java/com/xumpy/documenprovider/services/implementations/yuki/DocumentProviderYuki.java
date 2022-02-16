package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.domain.DocumentProviderDocuments;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.implementations.yuki.model.ArrayOfTransactionInfo;
import com.xumpy.documenprovider.services.implementations.yuki.model.TransactionInfo;
import com.xumpy.documenprovider.services.implementations.yuki.model.UploadReponse;
import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragAccountingDaoPojo;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;

@Service
public class DocumentProviderYuki implements DocumentProviderSrv {
    @Autowired YukiBuilder yukiBuilder;
    @Autowired DocumentenSrv documentenSrv;
    @Autowired PostToYuki postToYuki;
    @Autowired GetAccountInfoFromYuki getAccountInfoFromYuki;

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

    private Map<String, List<TransactionInfo>> mapPerDocumentID(ArrayOfTransactionInfo arrayOfTransactionInfo){
        Map<String, List<TransactionInfo>> documentIDMap = new HashMap<>();

        for(TransactionInfo transactionInfo: arrayOfTransactionInfo.getTransactionInfo()){
            if (transactionInfo.getDocumentID() != null){
                if (documentIDMap.containsKey(transactionInfo.getDocumentID())){
                    documentIDMap.get(transactionInfo.getDocumentID()).add(transactionInfo);
                } else {
                    List<TransactionInfo> transactionInfos = new ArrayList<>();
                    transactionInfos.add(transactionInfo);
                    documentIDMap.put(transactionInfo.getDocumentID(), transactionInfos);
                }
            }
        }

        return documentIDMap;
    }

    private void processBedragAccounting(Bedragen bedrag, List<TransactionInfo> transactionInfos){
        List<BedragAccountingDaoPojo> cleanupAccountingBedragen = bedragAccounting.getAccountantBedragenByBedrag(bedrag.getPk_id());
        for(BedragAccountingDaoPojo cleanupAccountingBedrag: cleanupAccountingBedragen){
            bedragAccounting.delete(cleanupAccountingBedrag);
        }

        for(TransactionInfo transactionInfo: transactionInfos){
            BedragAccountingDaoPojo bedragAccountingDaoPojo = new BedragAccountingDaoPojo();
            bedragAccountingDaoPojo.setBedrag(new BedragenDaoPojo(bedrag));
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

    private String generateReport(List<TransactionInfo> unmatchedTransactions, Integer matchedTransactions){
        String report = "------- Generated Report ------ \n";

        for(TransactionInfo transactionInfo: unmatchedTransactions){
            report = report + "No match at Date: " + transactionInfo.getTransactionDate() + " for document ID: " + transactionInfo.getDocumentID() + " with description: " + transactionInfo.getDescription() + "\n";
        }

        report = report + "matched transactions: " + matchedTransactions + "\n";
        report = report + "------------------------------------";

        return report;
    }

    @Override
    public String processDumpToBedragAccounting(String dump) {
        Map<String, List<TransactionInfo>> mapPerDocumentID =  mapPerDocumentID(new ArrayOfTransactionInfo(dump));

        List<TransactionInfo> unmatchedTransactions = new ArrayList<>();
        Integer matchedTransactions = 0;

        for(Map.Entry<String, List<TransactionInfo>> documentIdEntry: mapPerDocumentID.entrySet()){
            String documentId = documentIdEntry.getKey();

            List<DocumentProviderDocumentsDaoPojo> documentProviderDocumentsDaoPojos =
                    documentProviderDocuments.getDocumentProviderDocumentsByFeedback("Yuki ID: " + documentId);

            if (documentProviderDocumentsDaoPojos.size() == 0){
                for(TransactionInfo transactionInfo: documentIdEntry.getValue()){
                    unmatchedTransactions.add(transactionInfo);
                }
            } else if (documentProviderDocumentsDaoPojos.size() > 1){
                throw new RuntimeException("Multiple bedragen found for the same document. Invalid data in database");
            } else  {
                matchedTransactions = matchedTransactions + documentIdEntry.getValue().size();
                processBedragAccounting(documentProviderDocumentsDaoPojos.get(0).getDocumenten().getBedrag(), documentIdEntry.getValue());
            }
        }

        return generateReport(unmatchedTransactions, matchedTransactions);
    }
}
