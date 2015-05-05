/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.JobsDao;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public class JobsDaoImpl implements JobsDao{

    @Autowired
    private SessionFactory sessionFactory;
        
    @Override
    public Jobs select(Integer pk_id) {
        return (JobsDaoPojo)sessionFactory.getCurrentSession().get(JobsDaoPojo.class, pk_id);
    }

    @Override
    public void save(Jobs jobs) {
        JobsDaoPojo jobsDaoPojo = new JobsDaoPojo(jobs);
        sessionFactory.getCurrentSession().merge(jobsDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Jobs jobs) {
        JobsDaoPojo jobsDaoPojo = new JobsDaoPojo(jobs);
        sessionFactory.getCurrentSession().delete(jobsDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<Jobs> selectDate(Date date) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from JobsDaoPojo where jobDate = :date order by pk_id");
        query.setDate("date", date);
        
        return query.list();
    }

    @Override
    public List<Jobs> selectPeriode(Date startDate, Date endDate) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from JobsDaoPojo where jobDate >= :startDate and jobDate <= :endDate order by pk_id");
        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        
        return query.list();
    }
    
    @Override
    public Integer getNewPkId(){
        return ((Integer) sessionFactory.getCurrentSession().createQuery("select max(pk_id) as pk_id from JobsDaoPojo").list().get(0)) + 1;
    }
}
