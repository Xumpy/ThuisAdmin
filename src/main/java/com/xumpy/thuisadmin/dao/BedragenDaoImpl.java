/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
    private Personen persoon;
    
    @Override
    public void save(Bedragen bedragen) {
        sessionFactory.getCurrentSession().save(bedragen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(Bedragen bedragen) {
        sessionFactory.getCurrentSession().merge(bedragen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Bedragen bedragen) {
        sessionFactory.getCurrentSession().delete(bedragen);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening, Integer offset, String searchText) {
        Session session = sessionFactory.openSession();
        
        Criteria criteria = session.createCriteria(Bedragen.class);
        
        if (rekening != null){
            criteria.add(Restrictions.eq("rekening", rekening));
        }
        criteria.add(Restrictions.eq("persoon.pk_id", persoon.getPk_id()));
        
        searchText = "%" + searchText + "%";
        System.out.println(searchText);
        
        criteria.createAlias("groep", "groep");
        criteria.createAlias("rekening", "rekening");
        criteria.createAlias("persoon", "persoon");

        criteria.add(Restrictions.or(
                Restrictions.ilike("groep.naam", searchText),
                Restrictions.ilike("rekening.naam", searchText),
                Restrictions.ilike("persoon.naam", searchText),
                Restrictions.ilike("persoon.voornaam", searchText),
                Restrictions.("cast(bedrag as char)", searchText),
                Restrictions.ilike("cast(datum as char)", searchText),
                Restrictions.ilike("omschrijving", searchText)
        ));
        
        criteria.setFirstResult(offset * 10);
        criteria.setMaxResults(10);
        
        criteria.addOrder(Order.desc("datum"));
        
        List<Bedragen> bedragen = criteria.list();
        
        List<BeheerBedragenReport> beheerBedragenReport = new ArrayList<BeheerBedragenReport>();
        
        for (Bedragen bedrag: bedragen){
            beheerBedragenReport.add(new BeheerBedragenReport(bedrag));
        }
        
        return beheerBedragenReport;
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        List<BigInteger> list = session.createSQLQuery("select seq_ta_bedragen.nextval as num from dual")
                .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();
        
        return list.get(0).intValue();
    }
    
    @Override
    public Bedragen findBedrag(Integer bedragId){
        return (Bedragen)sessionFactory.getCurrentSession().get(Bedragen.class, bedragId);
    }
    
    @Override
    public List<OverzichtBedrag> findAllBedragen(){
        Query query = sessionFactory.getCurrentSession().createQuery("select new com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag(b.datum, b.bedrag, b.groep.negatief) " +
                                                                     "from Bedragen b where b.persoon.pk_id = :persoonId order by datum asc");
        query.setInteger("persoonId", persoon.getPk_id());
        
        return query.list();
    }

    @Override
    public List<OverzichtBedrag> findAllBedragen(Date startDate, Date endDate){
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("select new com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag(b.datum, b.bedrag, b.groep.negatief) " +
                                          "from Bedragen b where datum between :start and :end and b.persoon.pk_id = :persoonId order by datum asc");
        
        query.setDate("start", startDate);
        query.setDate("end", endDate);
        query.setInteger("persoonId", persoon.getPk_id());
        
        return query.list();
    }
    
    @Override
    public List<OverzichtBedrag> findBedragenRekening(Rekeningen rekening) {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("select new com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag(b.datum, b.bedrag, b.groep.negatief) " +
                                          "from Bedragen b where rekening.pk_id = :rekeningId order by datum asc, pk_id asc");
        query.setInteger("rekeningId", rekening.getPk_id());
        
        return query.list();
    }
    
    @Override
    public List<OverzichtBedrag> findBedragenRekening(Rekeningen rekening, Date startDate, Date endDate) {
        StatelessSession session = sessionFactory.openStatelessSession();

        Query query = session.createQuery("select new com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag(b.datum, b.bedrag, b.groep.negatief) " +
                                          "from Bedragen b" +
                                          " where rekening.pk_id = :rekeningId " +
                                          "   and datum between :start and :end order by datum asc, pk_id asc");
        
        query.setParameter("rekeningId", rekening.getPk_id());
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);
        
        return query.list();
    }

    @Override
    public BigDecimal somBedragDatum(Rekeningen rekening, Date datum) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select sum( case b.groep.negatief when 1 then (b.bedrag * -1) else b.bedrag end) " +
                                          " from Bedragen b where datum >= :datum and b.rekening.pk_id = :rekeningId");
        query.setDate("datum", datum);
        query.setInteger("rekeningId", rekening.getPk_id());
        
        return (BigDecimal)query.list().get(0);
    }

    @Override
    public Bedragen getBedrag(Integer pk_id){
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("from Bedragen where pk_id = :pk_id");
        query.setInteger("pk_id", pk_id);
        
        if(query.list().size()>0){
            return (Bedragen)query.list().get(0);
        } else {
            return null;
        }
    }

    public List<Bedragen> BedragInPeriode(Date startDate, Date endDate, Rekeningen rekening){
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(Bedragen.class);
        
        criteria.add(Restrictions.ge("datum", startDate));
        criteria.add(Restrictions.le("datum", endDate));
        if (rekening != null){
            criteria.add(Restrictions.eq("rekening", rekening));
        }
        
        criteria.addOrder(Order.asc("datum"));
        
        return criteria.list();
    }
    
    public BigDecimal getBedragAtDate(Date date, Rekeningen rekening){
        BigDecimal rekeningStand = new BigDecimal(0);
        
        Session session = sessionFactory.getCurrentSession();
        
        Criteria criteria = session.createCriteria(Bedragen.class);
        criteria.add(Restrictions.gt("datum", date));
        
        if (rekening != null){
            rekeningStand = rekening.getWaarde();
            criteria.add(Restrictions.eq("rekening", rekening));
        } else {
            rekeningStand = rekeningenDao.totalAllRekeningen();
        }
        
        List<Bedragen> lstBedragen = criteria.list();
        
        for (Bedragen bedrag: lstBedragen){
            if (bedrag.getGroep().getNegatief().equals(1)){
                rekeningStand = rekeningStand.add(bedrag.getBedrag());
            }
            if (bedrag.getGroep().getNegatief().equals(0)){
                rekeningStand = rekeningStand.subtract(bedrag.getBedrag());
            }
        }
        
        return rekeningStand;
    }
    
    public BigDecimal getTotalRekeningBedragen(List<Bedragen> bedragen){
        BigDecimal totaalRekeningen = new BigDecimal(0);
        List<Rekeningen> rekeningen = new ArrayList<Rekeningen>();
        
        for(Bedragen bedrag: bedragen){
            if (!rekeningen.contains(bedrag.getRekening())){
                rekeningen.add(bedrag.getRekening());
            }
        }
        
        for(Rekeningen rekening: rekeningen){
            totaalRekeningen = totaalRekeningen.add(rekening.getWaarde());
        }
        
        return totaalRekeningen;
    }
    
    public boolean isRekeningUnique(List<Bedragen> bedragen){
        List<Rekeningen> rekeningen = new ArrayList<Rekeningen>();
        
        for(Bedragen bedrag: bedragen){
            if (!rekeningen.contains(bedrag.getRekening())){
                rekeningen.add(bedrag.getRekening());
            }
        }
        
        if (rekeningen.size() == 1){
            return true;
        } else {
            return false;
        }
    }
    
    public Map OverviewRekeningData(List<Bedragen> bedragen){
        Map overviewRekeningData = new LinkedHashMap();

        Collections.sort(bedragen);
        
        BigDecimal rekeningStand = new BigDecimal(0);
        if (isRekeningUnique(bedragen)){
            rekeningStand = getBedragAtDate(bedragen.get(0).getDatum(), bedragen.get(0).getRekening());
        } else {
            rekeningStand = getBedragAtDate(bedragen.get(0).getDatum(), null);
        }
        
        overviewRekeningData.put(bedragen.get(0).getDatum(), rekeningStand);
        
        for (Integer i=1; i<bedragen.size(); i++){
            if (!bedragen.get(i).getDatum().equals(bedragen.get(0).getDatum())){
                if (bedragen.get(i).getGroep().getNegatief().equals(1)){
                    rekeningStand = rekeningStand.subtract(bedragen.get(i).getBedrag());
                }
                if (bedragen.get(i).getGroep().getNegatief().equals(0)){
                    rekeningStand = rekeningStand.add(bedragen.get(i).getBedrag());
                }
                overviewRekeningData.put(bedragen.get(i).getDatum(), rekeningStand);
            }
        }

        return overviewRekeningData;
    }

    public Map<Groepen, Map> OverviewRekeningGroep(List<Bedragen> bedragen){
        Map<Groepen, Map> overviewRekeningGroep = new LinkedHashMap<Groepen, Map>();
        
        for (Bedragen bedrag: bedragen){
            if (bedrag.getGroep().getCodeId() == null || !bedrag.getGroep().getCodeId().equals("INTER_REKENING")){
                Groepen hoofdGroep =  GroepenDaoImpl.getHoofdGroep(bedrag.getGroep());
                Map<String, BigDecimal> bedragInGroep = (Map)overviewRekeningGroep.get(hoofdGroep);

                if (bedragInGroep == null){
                    bedragInGroep = new LinkedHashMap<String, BigDecimal>();
                    bedragInGroep.put(POSITIEF, new BigDecimal(0));
                    bedragInGroep.put(NEGATIEF, new BigDecimal(0));
                }

                BigDecimal bedragNegatief = (BigDecimal)bedragInGroep.get(NEGATIEF);
                BigDecimal bedragPositief = (BigDecimal)bedragInGroep.get(POSITIEF);

                if (bedrag.getGroep().getNegatief().equals(1)){
                    bedragNegatief = bedragNegatief.add(bedrag.getBedrag());
                } else {
                    bedragPositief = bedragPositief.add(bedrag.getBedrag());
                }

                bedragInGroep.put(POSITIEF, bedragPositief);
                bedragInGroep.put(NEGATIEF, bedragNegatief);

                overviewRekeningGroep.put(hoofdGroep, bedragInGroep);   
            }
        }
        
        return overviewRekeningGroep;
    }
    
    public List<Bedragen> getBedragenInGroep(List<Bedragen> bedragen, Groepen hoofdGroep, Integer negatief){
        List<Bedragen> bedragenInGroep = new ArrayList<Bedragen>();
        
        for(Bedragen bedrag: bedragen){
            if (GroepenDaoImpl.getHoofdGroep(bedrag.getGroep()).equals(hoofdGroep)  && bedrag.getGroep().getNegatief().equals(negatief)){
                bedragenInGroep.add(bedrag);
            }
        }
        
        return bedragenInGroep;
    }
}
