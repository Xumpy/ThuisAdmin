/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.CompanyDao;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import com.xumpy.timesheets.domain.Company;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public class CompanyDaoImpl implements CompanyDao{

    @Autowired SessionFactory sessionFactory;
    
    @Override
    public Company select(Integer pk_id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from CompanyDaoPojo where pk_id = :pk_id");
        query.setInteger("pk_id", pk_id);
        
        return (CompanyDaoPojo) query.list().get(0);
    }

    @Override
    public List<Company> selectAll() {
        return sessionFactory.getCurrentSession().createQuery("from CompanyDaoPojo").list();
    }

    @Override
    public void save(Company company) {
        sessionFactory.getCurrentSession().merge(new CompanyDaoPojo(company));
    }

    @Override
    public void delete(Company company) {
        sessionFactory.getCurrentSession().delete(new CompanyDaoPojo(company));
    }

    @Override
    public Integer getNextPkId() {
        return ((Integer) sessionFactory.getCurrentSession().createQuery("select coalesce(max(pk_id),0) as pk_id from CompanyDaoPojo").uniqueResult()) + 1;
    }
    
}
