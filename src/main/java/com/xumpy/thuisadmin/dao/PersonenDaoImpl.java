/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Personen;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public class PersonenDaoImpl implements PersonenDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Personen personen) {
        sessionFactory.getCurrentSession().save(personen);
    }

    @Override
    public void update(Personen personen) {
        sessionFactory.getCurrentSession().update(personen);
    }

    @Override
    public void delete(Personen personen) {
        sessionFactory.getCurrentSession().delete(personen);
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        List<BigInteger> list = session.createSQLQuery("select seq_ta_personen.nextval as num from dual")
                .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();
        
        return list.get(0).intValue();
    }

    @Override
    public List<Personen> findAllPersonen() {
        return sessionFactory.getCurrentSession().createQuery("from Personen").list();
    }

    @Override
    public Personen findPersoon(Integer persoonId) {
        return (Personen)sessionFactory.getCurrentSession().get(Personen.class, persoonId);
    }
}
