/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.JobsGroupPricesDao;
import com.xumpy.timesheets.dao.model.JobsGroupPricesDaoPojo;
import com.xumpy.timesheets.domain.JobsGroupPrices;
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
public class JobsGroupPricesDaoImpl implements JobsGroupPricesDao{

    @Autowired SessionFactory sessionFactory;
    
    @Override
    public JobsGroupPrices select(Integer pk_id) {
        Query query = sessionFactory.getCurrentSession().createQuery("From JobsGroupPricesDaoPojo where pk_id = :pk_id");
        query.setInteger("pk_id", pk_id);
        
        return (JobsGroupPricesDaoPojo) query.list().get(0);
    }

    @Override
    public List<JobsGroupPrices> selectAll() {
        return sessionFactory.getCurrentSession().createQuery("From JobsGroupPricesDaoPojo").list();
    }

    @Override
    public void save(JobsGroupPrices jobsGroupPrices) {
        sessionFactory.getCurrentSession().merge(new JobsGroupPricesDaoPojo(jobsGroupPrices));
    }

    @Override
    public void delete(JobsGroupPrices jobsGroupPrices) {
        sessionFactory.getCurrentSession().delete(new JobsGroupPricesDaoPojo(jobsGroupPrices));
    }
    
    @Override
    public Integer getNextPkId() {
        return ((Integer) sessionFactory.getCurrentSession().createQuery("select coalesce(max(pk_id),0) as pk_id from JobsGroupPricesDaoPojo").uniqueResult()) + 1;
    }
}
