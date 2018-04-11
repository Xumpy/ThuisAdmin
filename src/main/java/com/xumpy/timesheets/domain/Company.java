/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain;

import java.math.BigDecimal;

/**
 *
 * @author nicom
 */
public interface Company {
    public Integer getPk_id();
    public String getName();
    public BigDecimal getDailyPayedHours();
    public String getStreet();
    public String getNumber();
    public String getPostalCode();
    public String getCountry();
    public String getCity();
    public String getVatNumber();
    public Boolean isTimeUnitDays();
}
