/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nicom
 */
public interface Bedragen {
    BigDecimal getBedrag();
    Date getDatum();
    Groepen getGroep();
    String getOmschrijving();
    Personen getPersoon();
    Integer getPk_id();
    Rekeningen getRekening();
    Invoices getInvoice();
    BigDecimal getTaxPercentagePaid();
    BigDecimal getWeightAccountancy();
    Boolean getProcessed();
    Boolean getManagedByAccountant();
    Boolean getCourant();
}
