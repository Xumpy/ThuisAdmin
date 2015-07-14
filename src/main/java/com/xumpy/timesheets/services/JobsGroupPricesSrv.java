/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.JobsGroupPrices;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsGroupPricesSrv {
    JobsGroupPrices select(Integer pk_id);
    List<JobsGroupPrices> selectAllJobGroupPrices();
    List<JobsGroupPrices> selectAllJobGroupPrices(JobsGroup jobsGroup);
    JobsGroupPrices save(JobsGroupPrices jobsGroupPrices);
    void delete(JobsGroupPrices jobsGroupPrices);
}
