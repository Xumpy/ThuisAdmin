/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Company;
import java.math.BigDecimal;

/**
 *
 * @author nicom
 */
public class CompanySrvPojo implements Company{
    private Integer pk_id;
    private String name;
    private BigDecimal dailyPayedHours;
    private String street;
    private String number;
    private String postalCode;
    private String country;
    private String city;
    private String vatNumber;
    private Boolean timeUnitDays;

    @Override
    public Boolean isTimeUnitDays() {
        return timeUnitDays;
    }

    public void setTimeUnitDays(Boolean timeUnitDays) {
        this.timeUnitDays = timeUnitDays;
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
    public Integer getPk_id() {
        return this.pk_id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigDecimal getDailyPayedHours() {
        return this.dailyPayedHours;
    }
    
    public CompanySrvPojo() { }
    
    public CompanySrvPojo(Company company){
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
