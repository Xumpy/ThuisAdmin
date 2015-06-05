/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.timesheets.domain.JobsGroupPrices;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsGroupPricesDao {
    public JobsGroupPrices select(Integer pk_id);
    public List<JobsGroupPrices> selectAll();
    public Integer getNextPkId();
    public void save(JobsGroupPrices jobsGroupPrices);
    public void delete(JobsGroupPrices jobsGroupPrices);
}
