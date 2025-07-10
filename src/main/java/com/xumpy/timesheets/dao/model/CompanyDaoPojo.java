/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.Company;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author nicom
 */
@Entity
@Table(name="TA_COMPANY")
public class CompanyDaoPojo implements Company, Serializable{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="DAILY_PAYED_HOURS")
    private BigDecimal dailyPayedHours;

    @Column(name="STREET")
    private String street;

    @Column(name="NUMBER")
    private String number;

    @Column(name="POSTAL_CODE")
    private String postalCode;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="CITY")
    private String city;

    @Column(name="VAT_NUMBER")
    private String vatNumber;

    @Column(name="TIME_UNIT_DAYS")
    private Boolean timeUnitDays;

    public Boolean isTimeUnitDays() {
        return timeUnitDays;
    }

    public void setTimeUnitDays(Boolean timeUnitDays) {
        this.timeUnitDays = timeUnitDays;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDailyPayedHours(BigDecimal dailyPayedHours) {
        this.dailyPayedHours = dailyPayedHours;
    }

    @Override
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getDailyPayedHours() {
        return dailyPayedHours;
    }
    
    public CompanyDaoPojo() { }
    
    public CompanyDaoPojo(Company company){
        this.dailyPayedHours = company.getDailyPayedHours();
        this.name = company.getName();
        this.pk_id = company.getPk_id();
        this.city = company.getCity();
        this.country = company.getCountry();
        this.street = company.getStreet();
        this.number = company.getNumber();
        this.postalCode = company.getPostalCode();
        this.vatNumber = company.getVatNumber();
        this.timeUnitDays = company.isTimeUnitDays();
    }
}
