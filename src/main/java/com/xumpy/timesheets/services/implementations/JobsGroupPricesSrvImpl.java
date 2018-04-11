/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.implementations.JobsGroupPricesDaoImpl;
import com.xumpy.timesheets.dao.model.JobsGroupPricesDaoPojo;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.JobsGroupPrices;
import com.xumpy.timesheets.services.JobsGroupPricesSrv;
import com.xumpy.timesheets.services.model.JobsGroupPricesSrvPojo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@Service
public class JobsGroupPricesSrvImpl implements JobsGroupPricesSrv{

    @Autowired JobsGroupPricesDaoImpl jobsGroupPricesDao;
    
    @Override
    @Transactional
    public JobsGroupPrices select(Integer pk_id) {
        return jobsGroupPricesDao.findById(pk_id).get();
    }

    @Override
    @Transactional
    public List<? extends JobsGroupPrices> selectAllJobGroupPrices() {
        return jobsGroupPricesDao.selectAll();
    }

    @Override
    @Transactional
    public List<? extends JobsGroupPrices> selectAllJobGroupPrices(JobsGroup jobsGroup) {
        return jobsGroupPricesDao.selectAllJobGroupPrices(jobsGroup.getPk_id());
    }

    @Override
    @Transactional
    public JobsGroupPrices save(JobsGroupPrices jobsGroupPrices) {
        JobsGroupPricesSrvPojo jobsGroupPricesSrv = new JobsGroupPricesSrvPojo(jobsGroupPrices);
        
        if (jobsGroupPricesSrv.getPk_id() == null){
            jobsGroupPricesSrv.setPk_id(jobsGroupPricesDao.getNextPkId());
        }
        
        jobsGroupPricesDao.save(new JobsGroupPricesDaoPojo(jobsGroupPricesSrv));
        return jobsGroupPricesSrv;
    }

    @Override
    @Transactional
    public void delete(JobsGroupPrices jobsGroupPrices) {
        jobsGroupPricesDao.delete(new JobsGroupPricesDaoPojo(jobsGroupPrices));
    }
    
}
