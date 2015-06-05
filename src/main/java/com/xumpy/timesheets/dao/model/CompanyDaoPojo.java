/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.Company;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    
    public CompanyDaoPojo() { }
    
    public CompanyDaoPojo(Company company){
        this.dailyPayedHours = company.getDailyPayedHours();
        this.name = company.getName();
        this.pk_id = company.getPk_id();
    }
}
