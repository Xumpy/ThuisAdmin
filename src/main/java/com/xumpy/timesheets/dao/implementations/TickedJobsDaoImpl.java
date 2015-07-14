/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.TickedJobsDao;
import com.xumpy.timesheets.dao.model.TickedJobsDaoPojo;
import com.xumpy.timesheets.domain.TickedJobs;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public class TickedJobsDaoImpl implements TickedJobsDao{

    @Autowired SessionFactory sessionFactory;
    
    @Override
    public TickedJobs select(Integer pk_id) {
        return (TickedJobsDaoPojo) sessionFactory.getCurrentSession().get(TickedJobsDaoPojo.class, pk_id);
    }

    @Override
    public List<TickedJobs> selectAllTickedJobs() {
        return sessionFactory.getCurrentSession().createQuery("from TickedJobsDaoPojo").list();
    }

    @Override
    public void save(TickedJobs tickedJobs) {
        sessionFactory.getCurrentSession().merge(tickedJobs);
    }

    @Override
    public void delete(TickedJobs tickedJobs) {
        sessionFactory.getCurrentSession().delete(tickedJobs);
    }

    @Override
    public Integer getNewPkId() {
        return ((Integer) sessionFactory.getCurrentSession().createQuery("select max(pk_id) as pk_id from TickedJobsDaoPojo").list().get(0)) + 1;
    }
    
}
