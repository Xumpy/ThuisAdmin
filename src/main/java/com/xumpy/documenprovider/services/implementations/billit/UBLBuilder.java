package com.xumpy.documenprovider.services.implementations.billit;

import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.timesheets.domain.Company;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.*;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class UBLBuilder {
    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;

    private static final String COMMERCIAL_INVOICE_TYPE_CODE = "380";
    private static final String DOCUMENT_CURRENCY_CODE = "EUR";
    private static final String PAYMENT_TERMS = "40 days after invoice date";

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return new Date(dateToConvert.getTime()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private PartyType createPartyType(String name,
                                      String vatNumber,
                                      String companyNumber,
                                      String street,
                                      String number,
                                      String postalCode,
                                      String city,
                                      String countryCode){
        PartyType partyType = new PartyType();

        EndpointIDType endpointIDType = new EndpointIDType();
        endpointIDType.setSchemeID("0208");
        endpointIDType.setValue(companyNumber);

        partyType.setEndpointID(endpointIDType);
        PartyNameType partyName = new PartyNameType();
        partyName.setName(new NameType(name));
        partyType.addPartyName(partyName);

        PartyIdentificationType vatID = new PartyIdentificationType();
        vatID.setID(new IDType(companyNumber));
        partyType.addPartyIdentification(vatID);

        AddressType address = new AddressType();
        address.setStreetName(new StreetNameType(street));
        address.setBuildingNumber(new BuildingNumberType(number));
        address.setPostalZone(new PostalZoneType(postalCode));
        address.setCityName(new CityNameType(city));
        CountryType country = new CountryType();
        country.setIdentificationCode(new IdentificationCodeType(countryCode));
        address.setCountry(country);

        partyType.setPostalAddress(address);

        PartyTaxSchemeType partyTaxSchemeType = new PartyTaxSchemeType();
        partyTaxSchemeType.setCompanyID(vatNumber);
        TaxSchemeType taxSchemeType = new TaxSchemeType();
        taxSchemeType.setID("VAT");
        partyTaxSchemeType.setTaxScheme(taxSchemeType);

        partyType.setPartyTaxScheme(Arrays.asList(partyTaxSchemeType));

        PartyLegalEntityType legalEntity = new PartyLegalEntityType();
        legalEntity.setRegistrationName(new RegistrationNameType(name));
        legalEntity.setCompanyID(new CompanyIDType(companyNumber));

        partyType.addPartyLegalEntity(legalEntity);

        return partyType;
    }

    private PartyType createLeverencierNMConsultancy(){
        return createPartyType("NM Consultancy",
                "BE0715962146",
                "0715962146",
                "Turfstraat",
                "9",
                "3900",
                "Pelt",
                "BE");
    }

    private FinancialAccountType createFinancialAccountType(){
        FinancialAccountType financialAccountType = new FinancialAccountType();

        financialAccountType.setID("BE70737056694725");
        BranchType branchType = new BranchType();
        branchType.setID("KREDBEBBXXX");
        financialAccountType.setFinancialInstitutionBranch(branchType);

        return financialAccountType;
    }

    private PaymentMeansType createPaymentMeans(String invoiceId){
        PaymentMeansType paymentMeansType = new PaymentMeansType();
        paymentMeansType.setPaymentMeansCode("30");
        PaymentIDType paymentIDType = new PaymentIDType(invoiceId);
        paymentMeansType.setPaymentID(Arrays.asList(paymentIDType));
        paymentMeansType.setPayeeFinancialAccount(createFinancialAccountType());

        return paymentMeansType;
    }

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

    private PartyType createCustomer(List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList){
        Company company = invoiceJobsDaoPojoList.get(0).getJob().getJobsGroup().getCompany();
        return createPartyType(company.getName(),
                company.getVatNumber(),
                company.getVatNumber(),
                company.getStreet(),
                company.getNumber(),
                company.getPostalCode(),
                company.getCity(),
                countryCode(company.getCountry()));
    }

    private oasis.names.specification.ubl.schema.xsd.unqualifieddatatypes_21.AmountType setCurrency(
            oasis.names.specification.ubl.schema.xsd.unqualifieddatatypes_21.AmountType amountType){
        amountType.setValue(amountType.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
        amountType.setCurrencyID(DOCUMENT_CURRENCY_CODE);
        return amountType;
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

    private TaxCategoryType createTaxCategory(BigDecimal taxAmount){
        TaxCategoryType taxCategoryType = new TaxCategoryType();
        taxCategoryType.setID(taxAmount.compareTo(new BigDecimal(0)) == 0 ? "E" : "S");
        taxCategoryType.setPercent(taxAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        TaxSchemeType taxSchemeType = new TaxSchemeType();
        taxSchemeType.setID("VAT");
        taxCategoryType.setTaxScheme(taxSchemeType);

        return taxCategoryType;
    }

    private TaxTotalType createTaxTotalType(Map<Integer, List<InvoiceJobsDaoPojo>> invoiceLinesPerGroup) {
        TaxTotalType taxTotalType = new TaxTotalType();
        BigDecimal taxTotal = new BigDecimal(0);
        for (Map.Entry<Integer, List<InvoiceJobsDaoPojo>> entry : invoiceLinesPerGroup.entrySet()) {
            TaxSubtotalType taxSubtotalType = new TaxSubtotalType();
            BigDecimal taxValue = createInvoiceTotalAmountExclVAT(entry.getValue());
            BigDecimal tax = createInvoiceVAT(entry.getValue());

            taxSubtotalType.setTaxableAmount((TaxableAmountType) setCurrency(new TaxableAmountType(taxValue)));
            taxSubtotalType.setTaxAmount((TaxAmountType) setCurrency(new TaxAmountType(tax)));
            taxSubtotalType.setTaxCategory(createTaxCategory(tax));

            taxTotalType.addTaxSubtotal(taxSubtotalType);

            taxTotal.add(tax);
        }
        taxTotalType.setTaxAmount((TaxAmountType) setCurrency(new TaxAmountType(taxTotal)));

        return taxTotalType;
    }

    private List<InvoiceLineType> createInvoiceLines(Map<Integer, List<InvoiceJobsDaoPojo>> invoiceLinesPerGroup){
        List<InvoiceLineType> invoiceLineTypes = new ArrayList<>();

        Integer jobId = 0;
        for(Map.Entry<Integer, List<InvoiceJobsDaoPojo>> entry: invoiceLinesPerGroup.entrySet()){
            jobId++;
            InvoiceLineType invoiceLine = new InvoiceLineType();
            invoiceLine.setID(new IDType(jobId.toString()));

            BigDecimal workedHours = new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal amountLine = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);

            for (InvoiceJobsDaoPojo invoiceJobsDaoPojo : entry.getValue()) {
                workedHours = workedHours.add(invoiceJobsDaoPojo.getJob().getWorkedHours());
                amountLine = amountLine.add(entry.getValue().get(0).getAmount().multiply(invoiceJobsDaoPojo.getJob().getWorkedHours()));
            }
            invoiceLine.setInvoicedQuantity(new InvoicedQuantityType(workedHours));
            invoiceLine.setLineExtensionAmount((LineExtensionAmountType) setCurrency(new LineExtensionAmountType(amountLine)));

            ItemType item = new ItemType();
            item.setName(new NameType(entry.getValue().get(0).getJob().getJobsGroup().getName()));
            item.setDescription(Arrays.asList(new DescriptionType(entry.getValue().get(0).getJob().getJobsGroup().getDescription())));
            item.setClassifiedTaxCategory(Arrays.asList(createTaxCategory(createInvoiceVAT(entry.getValue()))));
            invoiceLine.setItem(item);

            PriceType price = new PriceType();
            price.setPriceAmount((PriceAmountType) setCurrency(new PriceAmountType(entry.getValue().get(0).getAmount())));
            invoiceLine.setPrice(price);

            invoiceLineTypes.add(invoiceLine);
        }
        return invoiceLineTypes;
    }

    private BigDecimal createInvoiceTotalAmountExclVAT(List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList) {
        BigDecimal totalAmountExclVAT = new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_UP);;
        for (InvoiceJobsDaoPojo invoiceJobsDaoPojo : invoiceJobsDaoPojoList) {
            totalAmountExclVAT = totalAmountExclVAT.add(invoiceJobsDaoPojo.getAmount().multiply(invoiceJobsDaoPojo.getJob().getWorkedHours()));
        }
        return totalAmountExclVAT;
    }

    private BigDecimal createInvoiceVAT(List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList) {
        BigDecimal totalAmountExclVAT = createInvoiceTotalAmountExclVAT(invoiceJobsDaoPojoList);

        return totalAmountExclVAT.divide(new BigDecimal(100)).multiply(invoiceJobsDaoPojoList.get(0).getInvoice().getVatAmount());
    }

    private BigDecimal createInvoiceTotalAmountInclVAT(List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList){
        BigDecimal totalAmountExclVAT = createInvoiceTotalAmountExclVAT(invoiceJobsDaoPojoList);
        BigDecimal vat = createInvoiceVAT(invoiceJobsDaoPojoList);

        return totalAmountExclVAT.add(vat);
    }


   public InvoiceType createUBLInvoice (InvoicesDaoPojo invoicesDaoPojo){
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setCustomizationID("urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0");
        invoiceType.setProfileID("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0");

        invoiceType.setID(invoicesDaoPojo.getInvoiceId());
        invoiceType.setIssueDate(convertToLocalDateViaInstant(invoicesDaoPojo.getInvoiceDate()));
        invoiceType.setDueDate(convertToLocalDateViaInstant(invoicesDaoPojo.getInvoiceDueDate()));
        invoiceType.setInvoiceTypeCode(COMMERCIAL_INVOICE_TYPE_CODE);
        invoiceType.setDocumentCurrencyCode(DOCUMENT_CURRENCY_CODE);
        invoiceType.setNote(Arrays.asList(new NoteType(invoicesDaoPojo.getDescription())));

        OrderReferenceType orderReferenceType = new OrderReferenceType();
        orderReferenceType.setID(invoicesDaoPojo.getInvoiceRef());
        invoiceType.setOrderReference(orderReferenceType);

        SupplierPartyType accountingSupplier = new SupplierPartyType();

        accountingSupplier.setParty(createLeverencierNMConsultancy());
        invoiceType.setAccountingSupplierParty(accountingSupplier);
        invoiceType.setPaymentMeans(Arrays.asList(createPaymentMeans(invoicesDaoPojo.getInvoiceId())));

        List<InvoiceJobsDaoPojo> invoiceJobsDaoPojoList = invoiceJobsDao.findAllJobsByInvoice(invoicesDaoPojo.getPkId());
        if (invoiceJobsDaoPojoList.isEmpty()){
            throw new RuntimeException("No Jobs Found in Invoice. Nothing to invoice");
        }

        CustomerPartyType accountingCustomer = new CustomerPartyType();
        accountingCustomer.setParty(createCustomer(invoiceJobsDaoPojoList));
        invoiceType.setAccountingCustomerParty(accountingCustomer);
        Map<Integer, List<InvoiceJobsDaoPojo>> invoiceLinesPerGroup = createInvoiceLinesPerGroup(invoiceJobsDaoPojoList);

       invoiceType.setTaxTotal(Arrays.asList(createTaxTotalType(invoiceLinesPerGroup)));

       for(InvoiceLineType invoiceLineType: createInvoiceLines(invoiceLinesPerGroup)){
            invoiceType.addInvoiceLine(invoiceLineType);
        }

       PaymentTermsType paymentTermsType = new PaymentTermsType();
       paymentTermsType.setNote(Arrays.asList(new NoteType(PAYMENT_TERMS)));
       invoiceType.setPaymentTerms(Arrays.asList(paymentTermsType));

       MonetaryTotalType monetaryTotal = new MonetaryTotalType();
       monetaryTotal.setLineExtensionAmount((LineExtensionAmountType) setCurrency(new LineExtensionAmountType(createInvoiceTotalAmountExclVAT(invoiceJobsDaoPojoList))));
       monetaryTotal.setTaxExclusiveAmount((TaxExclusiveAmountType) setCurrency(new TaxExclusiveAmountType(createInvoiceTotalAmountExclVAT(invoiceJobsDaoPojoList))));
       monetaryTotal.setTaxInclusiveAmount((TaxInclusiveAmountType) setCurrency(new TaxInclusiveAmountType(createInvoiceTotalAmountInclVAT(invoiceJobsDaoPojoList))));
       monetaryTotal.setPayableAmount((PayableAmountType) setCurrency(new PayableAmountType(createInvoiceTotalAmountInclVAT(invoiceJobsDaoPojoList))));

       invoiceType.setLegalMonetaryTotal(monetaryTotal);

       return invoiceType;
   }
}
