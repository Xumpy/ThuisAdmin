/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.model;

import com.xumpy.timesheets.domain.Company;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicom
 */
public class CompanyCtrlPojo implements Company, Serializable{
    private Integer pk_id;
    private String name;
    private BigDecimal dailyPayedHours;

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
    
    public CompanyCtrlPojo() { }
    
    public CompanyCtrlPojo(Company company) {
        this.dailyPayedHours = company.getDailyPayedHours();
        this.name = company.getName();
        this.pk_id = company.getPk_id();
    }
    
    public static List<CompanyCtrlPojo> allCompanies(List<Company> companies){
        List<CompanyCtrlPojo> lstCompanyCtrlPojo = new ArrayList<CompanyCtrlPojo>();
        
        for(Company company: companies){
            lstCompanyCtrlPojo.add(new CompanyCtrlPojo(company));
        }
        
        return lstCompanyCtrlPojo;
    }
}
