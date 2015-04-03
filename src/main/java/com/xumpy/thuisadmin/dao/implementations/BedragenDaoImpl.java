/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.BedragenDao;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.model.Bedragen;
import com.xumpy.thuisadmin.model.Rekeningen;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
public class BedragenDaoImpl implements BedragenDao{

    public static final String NEGATIEF = "NEGATIEF";
    public static final String POSITIEF = "POSITIEF";
    
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RekeningenDaoImpl rekeningenDao;
    
    @Autowired
    private UserInfo userInfo;
    
    @Override
    public void save(Bedragen bedragen) {
        BedragenDaoPojo bedragenDaoPojo = new BedragenDaoPojo(bedragen);
        
        sessionFactory.getCurrentSession().merge(bedragenDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(Bedragen bedragen) {
        BedragenDaoPojo bedragenDaoPojo = new BedragenDaoPojo(bedragen);
        
        sessionFactory.getCurrentSession().merge(bedragenDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Bedragen bedragen) {
        BedragenDaoPojo bedragenDaoPojo = new BedragenDaoPojo(bedragen);
        
        sessionFactory.getCurrentSession().delete(bedragenDaoPojo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<Bedragen> reportBedragen(Rekeningen rekening, Integer offset, String searchText) {
        Session session = sessionFactory.getCurrentSession();
        
        String queryBedragen = "from BedragenDaoPojo " +
                               "where persoon.pk_id = :persoonId ";
                
        if (rekening != null){
            queryBedragen = queryBedragen + "  and rekening.pk_id = :rekeningId ";
        }
        
        Query query;
        if (searchText != null){
            query = session.createQuery(queryBedragen +
                                              "  and (lower(groep.naam) like :searchText " +
                                              "  or lower(rekening.naam) like :searchText " +
                                              "  or lower(persoon.naam) like :searchText " +
                                              "  or lower(persoon.voornaam) like :searchText " +
                                              "  or cast(bedrag as string) like :searchText " +
                                              "  or cast(datum as string) like :searchText " +
                                              "  or lower(omschrijving) like :searchText)" + 
                                              "  order by datum desc");
            query.setString("searchText", "%" + searchText.toLowerCase() + "%");
        } else {
            query = session.createQuery(queryBedragen + 
                               " order by datum desc");
        }
        
        query.setInteger("persoonId", userInfo.getPersoon().getPk_id());
        
        if (rekening != null){
            query.setInteger("rekeningId", rekening.getPk_id());
        }
        
        query.setMaxResults(10);
        query.setFirstResult(offset * 10);
        
        List<Bedragen> bedragen = query.list();
        
        return bedragen;
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        Dialect dialect = ((SessionFactoryImplementor) session.getSessionFactory()).getDialect();
        
        if (Oracle10gDialect.class.isInstance(dialect)){
            List<BigInteger> list = session.createSQLQuery("select seq_ta_bedragen.nextval as num from dual")
                    .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();

            return list.get(0).intValue();
        } else {
            Query query = session.createQuery("select max(pk_id) as pk_id from BedragenDaoPojo");
            Integer maxPkId = (Integer)query.list().get(0);
            return maxPkId + 1;
        }
    }
    
    @Override
    public Bedragen findBedrag(Integer bedragId){
        return (BedragenDaoPojo)sessionFactory.getCurrentSession().get(BedragenDaoPojo.class, bedragId);
    }

    @Override
    public BigDecimal somBedragDatum(Rekeningen rekening, Date datum) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select sum( case b.groep.negatief when 1 then (b.bedrag * -1) else b.bedrag end) " +
                                          " from BedragenDaoPojo b where datum >= :datum and b.rekening.pk_id = :rekeningId");
        query.setDate("datum", datum);
        query.setInteger("rekeningId", rekening.getPk_id());
        
        return (BigDecimal)query.list().get(0);
    }

    @Override
    public List<Bedragen> BedragInPeriode(Date startDate, Date endDate, Rekeningen rekening, boolean showPublicGroepen){
        Session session = sessionFactory.getCurrentSession();
        
        Criteria criteria = session.createCriteria(BedragenDaoPojo.class).createAlias("groep", "groep");
        
        criteria.add(Restrictions.ge("datum", startDate));
        criteria.add(Restrictions.le("datum", endDate));
        if (showPublicGroepen){
            criteria.add(Restrictions.or(Restrictions.eq("persoon.pk_id", userInfo.getPersoon().getPk_id()),
                                         Restrictions.eq("groep.publicGroep", 1)));
        } else {
            criteria.add(Restrictions.eq("persoon.pk_id", userInfo.getPersoon().getPk_id()));
        }
        
        if (rekening != null){
            criteria.add(Restrictions.eq("rekening", new RekeningenDaoPojo(rekening)));
        }
        
        criteria.addOrder(Order.asc("datum"));
        
        return criteria.list();
    }
    
    @Override
    public BigDecimal getBedragAtDate(Date date, Rekeningen rekening){
        BigDecimal rekeningStand = new BigDecimal(0);
        
        Session session = sessionFactory.getCurrentSession();
        
        Criteria criteria = session.createCriteria(BedragenDaoPojo.class);
        criteria.add(Restrictions.gt("datum", date));
        criteria.add(Restrictions.eq("persoon.pk_id", userInfo.getPersoon().getPk_id()));
        
        if (rekening != null){
            rekeningStand = rekening.getWaarde();
            criteria.add(Restrictions.eq("rekening", rekening));
        } else {
            rekeningStand = rekeningenDao.totalAllRekeningen();
        }
        
        List<BedragenDaoPojo> lstBedragen = criteria.list();
        
        for (BedragenDaoPojo bedrag: lstBedragen){
            if (bedrag.getGroep().getNegatief().equals(1)){
                rekeningStand = rekeningStand.add(bedrag.getBedrag());
            }
            if (bedrag.getGroep().getNegatief().equals(0)){
                rekeningStand = rekeningStand.subtract(bedrag.getBedrag());
            }
        }
        
        return rekeningStand;
    }
}
