package com.xumpy.documenprovider.services.implementations.billit;

import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.timesheets.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BillitBuilder {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;

    private String countryCode(String country){
        if (country.toLowerCase().startsWith("belg")){
            return "BE";
        }
        if (country.toLowerCase().contains("netherlands")){
            return "NL";
        }
        if (country.toLowerCase().contains("nederland")){
            return "NL";
        }
        throw new RuntimeException("Unrecognized country code for country: " + country);
    }

    private Map<Integer, List<InvoiceJobsDaoPojo>> createInvoiceLinesPerGroup(List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList){
        Map<Integer, List<InvoiceJobsDaoPojo>> jobsPerGroupId = new HashMap<>();

        for(InvoiceJobsDaoPojo invoiceJobsDaoPojo: invoiceJobsDaoPojoList){
            if (jobsPerGroupId.containsKey(invoiceJobsDaoPojo.getJob().getJobsGroup().getPk_id())){
                List<InvoiceJobsDaoPojo> invoiceJobsDaoPojos = jobsPerGroupId.get(invoiceJobsDaoPojo.getJob().getJobsGroup().getPk_id());
                invoiceJobsDaoPojos.add(invoiceJobsDaoPojo);
                jobsPerGroupId.put(invoiceJobsDaoPojo.getJob().getJobsGroup().getPk_id(), invoiceJobsDaoPojos);
            } else {
                List<InvoiceJobsDaoPojo> invoiceJobsDaoPojos = new ArrayList<>();
                invoiceJobsDaoPojos.add(invoiceJobsDaoPojo);
                jobsPerGroupId.put(invoiceJobsDaoPojo.getJob().getJobsGroup().getPk_id(), invoiceJobsDaoPojos);
            }
        }
        return jobsPerGroupId;
    }

    private Map<String, Object> createIdentiefier(String identifierType, String identifier, Boolean preferred){
        Map<String, Object> identifierMap = new HashMap<>();

        identifierMap.put("IdentifierType", identifierType);
        identifierMap.put("Identifier", identifier);
        identifierMap.put("Preferred", preferred);

        return identifierMap;
    }

    private List<Map<String, Object>> createIdentifiers(Company company){
        List<Map<String, Object>> identifiers = new ArrayList<>();

        if (company.getKvk() != null && !company.getKvk().isEmpty()){
            identifiers.add(createIdentiefier("KVK", company.getKvk(), false));
        }

        return identifiers;
    }

    private Map<String, Object> createCustomer(Company company, String vatNumber, BigDecimal vatAmount){
        Map<String, Object> customer = new HashMap<>();

        customer.put("Name", company.getShortName());
        customer.put("VATNumber",  vatNumber);
        customer.put("PartyType", "Customer");

        List<Map<String, Object>> addresses = new ArrayList<>();
        Map<String, Object> address = new HashMap<>();
        address.put("AddressType", "InvoiceAddress");
        address.put("Name", company.getName());
        address.put("Street", company.getStreet());
        address.put("StreetNumber", company.getNumber());
        address.put("City", company.getCity());
        address.put("Zipcode", company.getPostalCode());
        address.put("CountryCode", countryCode(company.getCountry()));
        addresses.add(address);
        customer.put("Addresses", addresses);
        customer.put("Email", company.getEmail());
        customer.put("DefaultExpiryOffset", "40");
        customer.put("VentilationCode", getVentilationCode(vatAmount));
        customer.put("SendUBL", false);
        customer.put("SendPDF", true);

        List<Map<String, Object>> identifiers = createIdentifiers(company);
        if (!identifiers.isEmpty()){
            customer.put("Identifiers", identifiers);
        }

        return customer;
    }

    private List<Map<String, Object>> createOrderLines(Map<Integer, List<InvoiceJobsDaoPojo>> invoiceLinesPerGroup){
        List<Map<String, Object>> orderLines = new ArrayList<>();

        for(Map.Entry<Integer, List<InvoiceJobsDaoPojo>> entry: invoiceLinesPerGroup.entrySet()){
            Map<String, Object> orderLine = new HashMap<>();

            BigDecimal quantity = BigDecimal.ZERO;
            BigDecimal unitPriceExcl = entry.getValue().get(0).getAmount();
            BigDecimal vatPercentage = entry.getValue().get(0).getInvoice().getVatAmount();
            for(InvoiceJobsDaoPojo invoiceJobsDaoPojo: entry.getValue()){
                quantity = quantity.add(invoiceJobsDaoPojo.getJob().getWorkedHours());
            }

            orderLine.put("Quantity", quantity);
            orderLine.put("UnitPriceExcl", unitPriceExcl);
            orderLine.put("Description", entry.getValue().get(0).getDescription());
            orderLine.put("VatPercentage", vatPercentage);

            orderLines.add(orderLine);
        }

        return orderLines;
    }

    private Map<String, String> createOrderPDF(Documenten document){
        Map<String, String> orderPDF = new HashMap<>();

        orderPDF.put("MimeType", document.getDocument_mime());
        orderPDF.put("FileContent", new String(Base64.getEncoder().encode(document.getDocument())));
        orderPDF.put("FileName", document.getDocument_naam());

        return orderPDF;
    }

    private String getVentilationCode(BigDecimal vatAmount){
        if (vatAmount.compareTo(new BigDecimal(0)) == 0){
            return "22";
        }
        if (vatAmount.compareTo(new BigDecimal(6)) == 0){
            return "2";
        }
        if (vatAmount.compareTo(new BigDecimal(12)) == 0){
            return "3";
        }
        return "4";
    }

    public Map<String, Object> createInvoice(Documenten document){
        Map<String, Object> invoice = new HashMap<>();

        InvoicesDaoPojo invoicesDaoPojo = new InvoicesDaoPojo(document.getBedrag().getInvoice());

        List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList = invoiceJobsDao.findAllJobsByInvoice(invoicesDaoPojo.getPkId());
        Map<Integer, List<InvoiceJobsDaoPojo>> invoiceLinesPerGroup = createInvoiceLinesPerGroup(invoiceJobsDaoPojoList);

        invoice.put("OrderType", "Invoice");
        invoice.put("OrderDirection", "Income");
        invoice.put("OrderNumber", invoicesDaoPojo.getInvoiceId());
        invoice.put("VentilationCode", getVentilationCode(invoicesDaoPojo.getVatAmount()));
        invoice.put("OrderDate", simpleDateFormat.format(invoicesDaoPojo.getInvoiceDate()));
        invoice.put("ExpiryDate", simpleDateFormat.format(invoicesDaoPojo.getInvoiceDueDate()));
        invoice.put("Customer", createCustomer(invoiceJobsDaoPojoList.get(0).getJob().getJobsGroup().getCompany(), invoicesDaoPojo.getVatNumber(), invoicesDaoPojo.getVatAmount()));
        invoice.put("OrderPDF", createOrderPDF(document));
        invoice.put("OrderLines", createOrderLines(invoiceLinesPerGroup));

        return invoice;
    }
}
