/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.RekeningenDao;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
public class RekeningenDaoImpl implements RekeningenDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private UserInfo userInfo;
    
    @Override
    public void save(RekeningenDaoPojo rekeningen) {
        sessionFactory.getCurrentSession().save(rekeningen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(RekeningenDaoPojo rekeningen) {
        sessionFactory.getCurrentSession().merge(rekeningen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(RekeningenDaoPojo rekeningen) {
        sessionFactory.getCurrentSession().delete(rekeningen);
        sessionFactory.getCurrentSession().flush();
    }
    
    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
                
        Dialect dialect= ((SessionFactoryImplementor) sessionFactory).getDialect();
        
        if (dialect instanceof Oracle10gDialect){
            List<BigInteger> list = session.createSQLQuery("select seq_ta_rekeningen.nextval as num from dual")
                    .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();

            Integer newPkId = list.get(0).intValue();

            return newPkId;
        } else {
            Query query = session.createQuery("select max(pk_id) as pk_id from RekeningenDaoPojo");
            Integer maxPkId = (Integer)query.list().get(0);
            return maxPkId + 1;
        }
    }
    
    @Override
    public List<RekeningenDaoPojo> findAllRekeningen() {
        Session session = sessionFactory.getCurrentSession();
        
        Criteria criteria = session.createCriteria(RekeningenDaoPojo.class);
        criteria.add(Restrictions.eq("persoon.pk_id", userInfo.getPersoon().getPk_id()));
        
        
        List list = criteria.list();
        
        return list;
    }
    
    @Override
    public RekeningenDaoPojo findRekening(Integer rekeningId){
        Session session = sessionFactory.getCurrentSession();
        
        RekeningenDaoPojo rekening = (RekeningenDaoPojo)session.get(RekeningenDaoPojo.class, rekeningId);
        
        return rekening;
    }
    
    public BigDecimal totalAllRekeningen(){
        Session session = sessionFactory.getCurrentSession();
        
        Criteria criteria = session.createCriteria(RekeningenDaoPojo.class);
        criteria.add(Restrictions.eq("persoon.pk_id", userInfo.getPersoon().getPk_id()));
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("waarde"));
        criteria.setProjection(projectionList);
        BigDecimal result = (BigDecimal)criteria.list().get(0);
        
        return result;
    }
}
