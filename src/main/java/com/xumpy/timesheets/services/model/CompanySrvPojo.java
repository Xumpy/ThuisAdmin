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
    }
}
