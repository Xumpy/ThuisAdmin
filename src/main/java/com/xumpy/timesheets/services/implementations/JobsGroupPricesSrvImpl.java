/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.implementations.JobsGroupPricesDaoImpl;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.JobsGroupPrices;
import com.xumpy.timesheets.services.JobsGroupPricesSrv;
import com.xumpy.timesheets.services.model.JobsGroupPricesSrvPojo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
public class JobsGroupPricesSrvImpl implements JobsGroupPricesSrv{

    @Autowired JobsGroupPricesDaoImpl jobsGroupPricesDao;
    
    @Override
    @Transactional(value="transactionManager")
    public JobsGroupPrices select(Integer pk_id) {
        return jobsGroupPricesDao.select(pk_id);
    }

    @Override
    @Transactional(value="transactionManager")
    public List<JobsGroupPrices> selectAllJobGroupPrices() {
        return jobsGroupPricesDao.selectAll();
    }

    @Override
    @Transactional(value="transactionManager")
    public List<JobsGroupPrices> selectAllJobGroupPrices(JobsGroup jobsGroup) {
        return jobsGroupPricesDao.selectAllJobGroupPrices(jobsGroup);
    }

    @Override
    @Transactional(value="transactionManager")
    public JobsGroupPrices save(JobsGroupPrices jobsGroupPrices) {
        JobsGroupPricesSrvPojo jobsGroupPricesSrv = new JobsGroupPricesSrvPojo(jobsGroupPrices);
        
        if (jobsGroupPricesSrv.getPk_id() == null){
            jobsGroupPricesSrv.setPk_id(jobsGroupPricesDao.getNextPkId());
        }
        
        jobsGroupPricesDao.save(jobsGroupPricesSrv);
        return jobsGroupPricesSrv;
    }

    @Override
    @Transactional(value="transactionManager")
    public void delete(JobsGroupPrices jobsGroupPrices) {
        jobsGroupPricesDao.delete(jobsGroupPrices);
    }
    
}
