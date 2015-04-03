/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.PersonenDao;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.model.Personen;
import java.math.BigInteger;
import java.util.List;
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
    public Personen save(Personen personen) {
        PersonenDaoPojo personenDaoPojo = new PersonenDaoPojo(personen);
        sessionFactory.getCurrentSession().save(personenDaoPojo);
        sessionFactory.getCurrentSession().flush();
        return personenDaoPojo;
    }

    @Override
    public Personen update(Personen personen) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update PersonenDaoPojo set naam = :naam," +
                                          "                       voornaam = :voornaam," +
                                          "                       username = :username," +
                                          "                       md5_password = coalesce(:password, md5_password) " + 
                                          "where pk_id = :pk_id");
        query.setString("naam", personen.getNaam());
        query.setString("voornaam", personen.getVoornaam());
        query.setString("username", personen.getUsername());
        if (personen.getMd5_password().isEmpty()){
            query.setString("password", null);
        } else {
            query.setString("password", personen.getMd5_password());
        }
        query.setInteger("pk_id", personen.getPk_id());
        
        query.executeUpdate();
        
        return personen;
    }

    @Override
    public Personen delete(Personen personen) {
        PersonenDaoPojo personenDaoPojo = new PersonenDaoPojo(personen);
        sessionFactory.getCurrentSession().delete(personenDaoPojo);
        sessionFactory.getCurrentSession().flush();
        return personenDaoPojo;
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
            Query query = session.createQuery("select max(pk_id) as pk_id from PersonenDaoPojo");
            Integer maxPkId = (Integer)query.list().get(0);
            return maxPkId + 1;
        }
    }

    @Override
    public List<Personen> findAllPersonen() {
        return sessionFactory.getCurrentSession().createQuery("from PersonenDaoPojo").list();
    }

    @Override
    public Personen findPersoon(Integer persoonId) {
        return (PersonenDaoPojo)sessionFactory.getCurrentSession().get(PersonenDaoPojo.class, persoonId);
    }
    
    @Override
    public Personen findPersoonByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from PersonenDaoPojo where username = :username");
        query.setString("username", username);
        
        return (PersonenDaoPojo)query.list().get(0);
    }
}
