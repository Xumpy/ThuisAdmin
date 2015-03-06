/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
    
    @Autowired
    private Personen persoon;
    
    @Override
    public void save(Rekeningen rekeningen) {
        sessionFactory.getCurrentSession().save(rekeningen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(Rekeningen rekeningen) {
        sessionFactory.getCurrentSession().merge(rekeningen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Rekeningen rekeningen) {
        sessionFactory.getCurrentSession().delete(rekeningen);
        sessionFactory.getCurrentSession().flush();
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
        
        Criteria criteria = session.createCriteria(Rekeningen.class);
        criteria.add(Restrictions.eq("persoon.pk_id", persoon.getPk_id()));
        
        
        List list = criteria.list();
        
        return list;
    }
    
    @Override
    public Rekeningen findRekening(Integer rekeningId){
        Session session = sessionFactory.getCurrentSession();
        
        Rekeningen rekening = (Rekeningen)session.get(Rekeningen.class, rekeningId);
        
        return rekening;
    }
    
    public BigDecimal totalAllRekeningen(){
        Session session = sessionFactory.getCurrentSession();
        
        Criteria criteria = session.createCriteria(Rekeningen.class);
        criteria.add(Restrictions.eq("persoon.pk_id", persoon.getPk_id()));
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("waarde"));
        criteria.setProjection(projectionList);
        BigDecimal result = (BigDecimal)criteria.list().get(0);
        
        return result;
    }
}
