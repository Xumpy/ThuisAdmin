/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.JobsGroupDao;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.JobsGroup;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public class JobsGroupDaoImpl implements JobsGroupDao{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public JobsGroup select(Integer pk_id) {
        return (JobsGroupDaoPojo)sessionFactory.getCurrentSession().get(JobsGroupDaoPojo.class, pk_id);
    }

    @Override
    public void save(JobsGroup jobsGroup) {
        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo(jobsGroup);
        sessionFactory.getCurrentSession().merge(jobsGroupDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(JobsGroup jobsGroup) {
        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo(jobsGroup);
        sessionFactory.getCurrentSession().delete(jobsGroupDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<JobsGroup> selectAllJobGroups() {
        return sessionFactory.getCurrentSession().createQuery("from JobsGroupDaoPojo").list();
    }

    @Override
    public Integer getNewPkId(){
        return ((Integer) sessionFactory.getCurrentSession().createQuery("select max(pk_id) as pk_id from JobsGroupDaoPojo").list().get(0)) + 1;
    }
}
