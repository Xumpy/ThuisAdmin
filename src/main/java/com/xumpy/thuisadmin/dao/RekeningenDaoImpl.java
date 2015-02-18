/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Repository
public class RekeningenDaoImpl implements RekeningenDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void save(Rekeningen rekeningen) {
        sessionFactory.getCurrentSession().save(rekeningen);
    }

    @Override
    public void update(Rekeningen rekeningen) {
        sessionFactory.getCurrentSession().update(rekeningen);
    }

    @Override
    public void delete(Rekeningen rekeningen) {
        sessionFactory.getCurrentSession().delete(rekeningen);
    }
    
    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        List<BigInteger> list = session.createSQLQuery("select seq_ta_rekeningen.nextval as num from dual")
                .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();
        
        Integer newPkId = list.get(0).intValue();
        
        return newPkId;
        
    }
    
    @Override
    public List<Rekeningen> findAllRekeningen() {
        Session session = sessionFactory.getCurrentSession();
        
        List list = session.createQuery("from Rekeningen").list();
        
        return list;
    }
    
    @Override
    public Rekeningen findRekening(Integer rekeningId){
        Session session = sessionFactory.getCurrentSession();
        
        Rekeningen rekening = (Rekeningen)session.get(Rekeningen.class, rekeningId);
        
        return rekening;
    }
}
