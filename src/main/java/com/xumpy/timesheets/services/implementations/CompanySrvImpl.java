/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.implementations.CompanyDaoImpl;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
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

    @Autowired CompanyDaoImpl companyDao;
    
    @Override
    @Transactional
    public Company select(Integer pk_id) {
        return companyDao.findOne(pk_id);
    }

    @Override
    @Transactional
    public List<? extends Company> selectAll() {
        return companyDao.selectAll();
    }

    @Override
    @Transactional
    public Company save(Company company) {
        CompanySrvPojo companySrvPojo = new CompanySrvPojo(company);
        
        if (companySrvPojo.getPk_id() == null){
            companySrvPojo.setPk_id(companyDao.getNextPkId());
        }
        companyDao.save(new CompanyDaoPojo(companySrvPojo));
        
        return companySrvPojo;
    }

    @Override
    @Transactional
    public void delete(Company company) {
        companyDao.delete(new CompanyDaoPojo(company));
    }
    
}
