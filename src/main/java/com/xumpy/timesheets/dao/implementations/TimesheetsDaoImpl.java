/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.TimesheetsDao;
import com.xumpy.timesheets.dao.model.TimesheetsDaoPojo;
import com.xumpy.timesheets.domain.Timesheets;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nico
 */
public class TimesheetsDaoImpl implements TimesheetsDao{

    @Autowired SessionFactory sessionFactory;
    
    @Override
    public Timesheets select(Integer pk_id) {
        return (TimesheetsDaoPojo) sessionFactory.getCurrentSession().get(TimesheetsDaoPojo.class, pk_id);
    }

    @Override
    public List<Timesheets> selectAllTimesheets() {
        return sessionFactory.getCurrentSession().createQuery("from TimesheetsDaoPojo").list();
    }

    @Override
    public void save(Timesheets timesheets) {
        sessionFactory.getCurrentSession().merge(timesheets);
    }

    @Override
    public void delete(Timesheets timesheets) {
        sessionFactory.getCurrentSession().delete(timesheets);
    }

    @Override
    public Integer getNewPkId() {
        return ((Integer) sessionFactory.getCurrentSession().createQuery("select coalesce(max(pk_id),0) as pk_id from TimesheetsDaoPojo").list().get(0)) + 1;
    }
    
}
