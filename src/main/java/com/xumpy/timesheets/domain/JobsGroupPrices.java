/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nicom
 */
public interface JobsGroupPrices {
    public Integer getPk_id();
    public JobsGroup getJobsGroup();
    public Date getStartDate();
    public Date getEndDate();
    public BigDecimal getPricePerHour();
}
