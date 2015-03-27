/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Personen;
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
public class GroepenDaoImpl implements GroepenDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private UserInfo userInfo;
    
    @Override
    public void save(Groepen groepen) {
        sessionFactory.getCurrentSession().save(groepen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(Groepen groepen) {
        sessionFactory.getCurrentSession().update(groepen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Groepen groepen) {
        sessionFactory.getCurrentSession().delete(groepen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        Dialect dialect= ((SessionFactoryImplementor) sessionFactory).getDialect();
        
        if (dialect instanceof Oracle10gDialect){
            List<BigInteger> pkId = session.createSQLQuery("select seq_ta_type_groep.nextval as num from dual")
                    .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();

            return pkId.get(0).intValue();
        } else {
            Query query = session.createQuery("select max(pk_id) as pk_id from Groepen");
            Integer maxPkId = (Integer)query.list().get(0);
            return maxPkId + 1;
        }
    }

    @Override
    public List<Groepen> findAllGroepen() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Groepen where persoon.pk_id = :personenId");
        query.setInteger("personenId", userInfo.getPersoon().getPk_id());
        return query.list();
        
    }

    @Override
    public Groepen findGroep(Integer groepId) {
        return (Groepen)sessionFactory.getCurrentSession().get(Groepen.class, groepId);
    }

    @Override
    public List<Groepen> findAllHoofdGroepen() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Groepen where fk_hoofd_type_groep_id is null and persoon.pk_id = :personenId");
        query.setInteger("personenId", userInfo.getPersoon().getPk_id());
        return query.list();
    }

    @Override
    public List<Groepen> findAllGroepen(Integer hoofdGroepId) {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("from Groepen where hoofdGroep.pk_id = ? order by negatief");
        
        query.setInteger(0, hoofdGroepId);
        
        return query.list();
    }
    
    public static Groepen getHoofdGroep(Groepen groep){
        if (groep.getHoofdGroep() == null){
            return groep;
        } else {
            Groepen hoofdGroep = groep.getHoofdGroep();
            
            while(true){
                if (hoofdGroep.getHoofdGroep() != null){
                    hoofdGroep = hoofdGroep.getHoofdGroep();
                } else {
                    return hoofdGroep;
                }
            }
        }
    }
    
}
