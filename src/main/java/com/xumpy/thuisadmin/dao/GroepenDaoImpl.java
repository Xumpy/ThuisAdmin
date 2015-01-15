/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.view.GroepenTree;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public class GroepenDaoImpl implements GroepenDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void save(Groepen groepen) {
        sessionFactory.getCurrentSession().save(groepen);
    }

    @Override
    public void update(Groepen groepen) {
        sessionFactory.getCurrentSession().update(groepen);
    }

    @Override
    public void delete(Groepen groepen) {
        sessionFactory.getCurrentSession().delete(groepen);
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        BigDecimal pkId = (BigDecimal)session.createSQLQuery("select seq_ta_type_groep.nextval from dual").list().get(0);
        
        return pkId.intValue();
    }

    @Override
    public List<Groepen> findAllGroepen() {
        return sessionFactory.getCurrentSession().createQuery("from Groepen").list();
    }

    @Override
    public Groepen findGroep(Integer groepId) {
        return (Groepen)sessionFactory.getCurrentSession().get(Groepen.class, groepId);
    }

    @Override
    public List<Groepen> findAllHoofdGroepen() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select pk_id," +
                                             "       fk_hoofd_type_groep_id," +
                                             "       naam," +
                                             "       omschrijving," +
                                             "       negatief," +
                                             "       fk_personen_id," +
                                             "       code_id from ta_type_groep" +
                                             " where fk_hoofd_type_groep_id is null").addEntity(Groepen.class);

        return query.list();
    }

    @Override
    public List<Groepen> findAllGroepen(Integer hoofdGroepId) {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("from Groepen where hoofdGroep.pk_id = ? order by negatief");
        
        query.setInteger(0, hoofdGroepId);
        
        return query.list();
    }
}
