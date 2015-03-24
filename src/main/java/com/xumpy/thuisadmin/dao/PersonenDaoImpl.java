/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.view.RegisterUserPage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
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
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(Personen personen) {
        sessionFactory.getCurrentSession().update(personen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Personen personen) {
        sessionFactory.getCurrentSession().delete(personen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
                
        Dialect dialect= ((SessionFactoryImplementor) sessionFactory).getDialect();
        
        if (dialect instanceof Oracle10gDialect){
            List<BigInteger> list = session.createSQLQuery("select seq_ta_personen.nextval as num from dual")
                    .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();

            return list.get(0).intValue();
        } else {
            Query query = session.createQuery("select max(pk_id) as pk_id from Personen");
            Integer maxPkId = (Integer)query.list().get(0);
            return maxPkId + 1;
        }
    }

    @Override
    public List<Personen> findAllPersonen() {
        return sessionFactory.getCurrentSession().createQuery("from Personen").list();
    }

    @Override
    public Personen findPersoon(Integer persoonId) {
        return (Personen)sessionFactory.getCurrentSession().get(Personen.class, persoonId);
    }
    
    @Override
    public Personen findPersoonByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Personen where username = :username");
        query.setString("username", username);
        
        return (Personen)query.list().get(0);
    }
}
