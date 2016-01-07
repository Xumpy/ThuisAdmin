/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.CompanyDao;
import com.xumpy.timesheets.domain.Company;
import com.xumpy.timesheets.services.CompanySrv;
import com.xumpy.timesheets.services.model.CompanySrvPojo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@Service
public class CompanySrvImpl implements CompanySrv{

    @Autowired CompanyDao companyDao;
    
    @Override
    @Transactional(value="transactionManager")
    public Company select(Integer pk_id) {
        return companyDao.select(pk_id);
    }

    @Override
    @Transactional(value="transactionManager")
    public List<Company> selectAll() {
        return companyDao.selectAll();
    }

    @Override
    @Transactional(value="transactionManager")
    public Company save(Company company) {
        CompanySrvPojo companySrvPojo = new CompanySrvPojo(company);
        
        if (companySrvPojo.getPk_id() == null){
            companySrvPojo.setPk_id(companyDao.getNextPkId());
        }
        companyDao.save(companySrvPojo);
        
        return companySrvPojo;
    }

    @Override
    @Transactional(value="transactionManager")
    public void delete(Company company) {
        companyDao.delete(company);
    }
    
}
