package com.xumpy.documenprovider.services.implementations.yuki.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionInfo {
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "id") private String id;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "hID") private Integer hID;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "transactionDate") private Date transactionDate;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "description") private String description;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "transactionAmount") private BigDecimal transactionAmount;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "transactionAmountForeignCurrency") private BigDecimal transactionAmountForeignCurrency;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "currencyRate") private BigDecimal currencyRate;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "currency") private String currency;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "taxCodeDescription") private String taxCodeDescription;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "fullName") private String fullName;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "VATNumber") private String vatNumber;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "contactHID") private Integer contactHID;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "contactCountry") private String contactCountry;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "contactZipcode") private String contactZipcode;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "glAccountCode") private String glAccountCode;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "documentID") private String documentID;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "documentReference") private Integer documentReference;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "documentType") private String documentType;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "documentFolder") private String documentFolder;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "documentFolderTab") private String documentFolderTab;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "contactID") private String contactID;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "periodId") private Integer periodId;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "company") private String company;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "mutationUser") private String mutationUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer gethID() {
        return hID;
    }

    public void sethID(Integer hID) {
        this.hID = hID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getTransactionAmountForeignCurrency() {
        return transactionAmountForeignCurrency;
    }

    public void setTransactionAmountForeignCurrency(BigDecimal transactionAmountForeignCurrency) {
        this.transactionAmountForeignCurrency = transactionAmountForeignCurrency;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(BigDecimal currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTaxCodeDescription() {
        return taxCodeDescription;
    }

    public void setTaxCodeDescription(String taxCodeDescription) {
        this.taxCodeDescription = taxCodeDescription;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Integer getContactHID() {
        return contactHID;
    }

    public void setContactHID(Integer contactHID) {
        this.contactHID = contactHID;
    }

    public String getContactCountry() {
        return contactCountry;
    }

    public void setContactCountry(String contactCountry) {
        this.contactCountry = contactCountry;
    }

    public String getContactZipcode() {
        return contactZipcode;
    }

    public void setContactZipcode(String contactZipcode) {
        this.contactZipcode = contactZipcode;
    }

    public String getGlAccountCode() {
        return glAccountCode;
    }

    public void setGlAccountCode(String glAccountCode) {
        this.glAccountCode = glAccountCode;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Integer getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(Integer documentReference) {
        this.documentReference = documentReference;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentFolder() {
        return documentFolder;
    }

    public void setDocumentFolder(String documentFolder) {
        this.documentFolder = documentFolder;
    }

    public String getDocumentFolderTab() {
        return documentFolderTab;
    }

    public void setDocumentFolderTab(String documentFolderTab) {
        this.documentFolderTab = documentFolderTab;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMutationUser() {
        return mutationUser;
    }

    public void setMutationUser(String mutationUser) {
        this.mutationUser = mutationUser;
    }
}
