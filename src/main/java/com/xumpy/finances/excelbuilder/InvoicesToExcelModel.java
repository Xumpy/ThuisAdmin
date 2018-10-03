package com.xumpy.finances.excelbuilder;

import com.xumpy.finances.model.excel.Costs;
import com.xumpy.finances.model.excel.Document;
import com.xumpy.finances.model.excel.ExcelModel;
import com.xumpy.finances.model.excel.Invoices;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.implementations.DocumentenSrvImpl;
import com.xumpy.thuisadmin.services.implementations.InvoicesSrvImpl;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;
import com.xumpy.thuisadmin.services.model.InvoicesSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
public class InvoicesToExcelModel {
    @Autowired InvoicesSrvImpl invoicesSrv;
    @Autowired DocumentenSrvImpl documentenSrv;
    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;
    @Autowired BedragenDaoImpl bedragenDao;
    @Autowired RekeningenDaoImpl rekeningenDao;
    @Autowired DocumentenDaoImpl documentenDao;

    private String returnExtension(String mime){
        Map<String, String> mimeTypes = new HashMap<String, String>();

        mimeTypes.put("application/pdf", "pdf");
        mimeTypes.put("application/zip", "zip");
        mimeTypes.put("image/jpeg", "jpg");

        return mimeTypes.get(mime);
    }

    private Document generateDocument(Documenten document){
        Document doc = new Document();
        doc.setDocumentLocations(document.getPk_id() + "_" + document.getOmschrijving() + "." + returnExtension(document.getDocument_mime()));
        doc.setDocument(document.getDocument());

        return doc;
    }

    private Invoices createExcelInvoice(BedragenSrvPojo bedragen){
        Invoices invoice = new Invoices();
        List<? extends Documenten> documenten = documentenSrv.fetchDocumentByBedrag(bedragen.getPk_id());

        invoice.setAmount(bedragen.getBedrag().doubleValue());
        invoice.setDate(bedragen.getInvoice().getInvoiceDate());
        invoice.setInvoiceId(bedragen.getInvoice().getInvoiceId());

        List<Document> documents = new ArrayList<>();
        for(Documenten document: documenten){
            documents.add(generateDocument(document));
        }
        invoice.setDocuments(documents);

        return invoice;
    }

    private Costs getCostPerBedragId(List<Costs> costs, Integer bedragId){
        for(Costs cost: costs){
            if (cost.getBedragId().equals(bedragId)){
                return cost;
            }
        }
        return new Costs();
    }

    private Costs transformDocumentToCost(Documenten document){
        Costs cost = new Costs();

        cost.setDate(document.getBedrag().getDatum());
        cost.setAmount(document.getBedrag().getBedrag().doubleValue());
        cost.setBedragId(document.getBedrag().getPk_id());
        cost.setDescription(document.getBedrag().getOmschrijving());
        cost.setExpectedWeight(document.getBedrag().getWeightAccountancy().doubleValue());

        List<Document> documents = new ArrayList<>();
        documents.add(generateDocument(document));

        cost.setDocuments(documents);

        return cost;
    }

    private List<Costs> createExcelCosts(List<DocumentenDaoPojo> documents){
        List<Costs> excelCosts = new ArrayList<>();
        for(DocumentenDaoPojo document: documents){
            Costs cost = getCostPerBedragId(excelCosts, document.getBedrag().getPk_id());
            if (cost.getBedragId() == null){
                excelCosts.add(transformDocumentToCost(document));
            } else {
                cost.getDocuments().add(generateDocument(document));
            }

        }
        return excelCosts;
    }

    private List<BedragenSrvPojo> findBedragenForInvoices(LocalDate startDate, LocalDate endDate){
        List<BedragenSrvPojo> invoicedBedragen = new ArrayList<>();

        for(InvoicesSrvPojo invoice: invoicesSrv.findAllInvoicesBetween(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate))){
            for(BedragenDaoPojo bedragenDaoPojo: bedragenDao.getBedragenFromInvoice(invoice.getPkId())){
                invoicedBedragen.add(new BedragenSrvPojo(bedragenDaoPojo));
            }
        }

        return invoicedBedragen;
    }

    private Boolean isDocumentValidCost(DocumentenDaoPojo document){
        if (document.getBedrag().getWeightAccountancy() != null && document.getBedrag().getWeightAccountancy().compareTo(new BigDecimal(0)) > 0){
            return true;
        } else {
            return false;
        }
    }

    private List<DocumentenDaoPojo> findCostDocuments(LocalDate startDate, LocalDate endDate){
        List<DocumentenDaoPojo>  validCostDocuments = new ArrayList<>();

        for(RekeningenDaoPojo rekening: rekeningenDao.findAll()){
            if (rekening.getProfessional() != null && rekening.getProfessional()){
                for(DocumentenDaoPojo document: documentenDao.fetchDocumentsForBedragenInPeriodeOnRekening(java.sql.Date.valueOf(startDate),
                        java.sql.Date.valueOf(endDate), rekening.getPk_id())){
                    if (isDocumentValidCost(document)){
                        validCostDocuments.add(document);
                    }
                }
            }
        }

        return validCostDocuments;
    }

    public ExcelModel buildExcelInvoices(LocalDate startDate, LocalDate endDate){
        ExcelModel excelModel = new ExcelModel();
        List<BedragenSrvPojo> invoiceBedragen = findBedragenForInvoices(startDate, endDate);

        List<Invoices> invoices = new ArrayList<>();
        for(BedragenSrvPojo invoiceBedrag: invoiceBedragen){
            invoices.add(createExcelInvoice(invoiceBedrag));
        }

        excelModel.setInvoices(invoices);
        excelModel.setCosts(createExcelCosts(findCostDocuments(startDate, endDate)));

        return excelModel;
    }
}
