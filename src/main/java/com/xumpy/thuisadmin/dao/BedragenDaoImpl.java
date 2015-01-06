/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
public class BedragenDaoImpl extends HibernateDaoSupport implements BedragenDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Bedragen bedragen) {
        getHibernateTemplate().save(bedragen);
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Bedragen bedragen) {
        getHibernateTemplate().update(bedragen);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Bedragen bedragen) {
        getHibernateTemplate().delete(bedragen);
    }

    @Override
    public List<Bedragen> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select to_char(datum, 'DD-MM-RRRR') as label," +
                                          "       rekening_bedrag as value " +
                                          "from table(pkg_ta_rekening.fun_overzicht(" +
                                          "            in_fk_rekening_id => ?," +
                                          "            in_start_datum => ?," +
                                          "            in_eind_datum => ?," +
                                          "            in_interval => 1)) " +
                                          "order by datum asc");
        query.setInteger(0, rekening.getPk_id());
        query.setDate(1, beginDate);
        query.setDate(2, eindDate);
        
        return query.list();
    }
}
