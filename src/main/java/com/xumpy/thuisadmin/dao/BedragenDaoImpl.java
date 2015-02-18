/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.RekeningOverzicht;
import com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public class BedragenDaoImpl implements BedragenDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Bedragen bedragen) {
        sessionFactory.getCurrentSession().save(bedragen);
    }

    @Override
    public void update(Bedragen bedragen) {
        sessionFactory.getCurrentSession().update(bedragen);
    }

    @Override
    public void delete(Bedragen bedragen) {
        sessionFactory.getCurrentSession().delete(bedragen);
    }

    
    @Override
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select to_char(datum, 'DD-MM-RRRR') as label," +
                                          "       rekening_bedrag as value " +
                                          "from table(pkg_ta_rekening.fun_overzicht(" +
                                          "            in_fk_rekening_id => ?," +
                                          "            in_start_datum => ?," +
                                          "            in_eind_datum => ?," +
                                          "            in_interval => 1)) " +
                                          "order by datum asc");
        
        query.setInteger(0, rekening == null ? -1 : rekening.getPk_id()); 

        query.setDate(1, beginDate);
        query.setDate(2, eindDate);
        
        return query.list();
    }

    @Override
    public List<OverzichtGroep> graphiekOverzichtGroep(Date beginDate, Date eindDate) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select ttg.pk_id as groepId," +
                                             "       ttg.naam," +
                                             "       nvl(tot.totaal_bedrag_opbrengsten,0) as totaal_opbrengsten," + 
                                             "       nvl(tot.totaal_bedrag_kosten, 0) as totaal_kosten " +
                                             " from table(pkg_ta_rekening.fun_bedrag_groep(" +
                                             "  in_fk_hoofd_type_groep_id => null," +
                                             "  in_start_datum => ?," +
                                             "  in_eind_datum => ?)) tot " +
                                             " join ta_type_groep ttg" +
                                             "  on (tot.fk_type_groep_id = ttg.pk_id)").addEntity(OverzichtGroep.class);
        query.setDate(0, beginDate);
        query.setDate(1, eindDate);
        
        return query.list();
    }

    @Override
    public List<OverzichtGroepBedragen> rapportOverzichtGroepBedragen(Integer typeGroepId, Integer negatief, Date beginDate, Date eindDate) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select tb.pk_id as pk_id," +
                                             "       ttg.naam as type_naam," +
                                             "       tb.bedrag as bedrag," +
                                             "       to_char(tb.datum, 'yyyy-mm-dd') as datum," +
                                             "       tb.omschrijving as omschrijving," +
                                             "       ttg.pk_id as fk_type_groep_id" +
                                             " from ta_bedragen tb" +
                                             " join ta_type_groep ttg" +
                                             "   on (tb.fk_type_groep_id = ttg.pk_id)" +
                                             " where pkg_ta_rekening.fun_groep_in_groep(in_groep => fk_type_groep_id," +
                                             "                                          in_hoofd_groep => ?) = 1" +
                                             "  and ttg.negatief = ?" +
                                             "  and datum between ? and ?").addEntity(OverzichtGroepBedragen.class);
        
        query.setInteger(0, typeGroepId);
        query.setInteger(1, negatief);
        query.setDate(2, beginDate);
        query.setDate(3, eindDate);

        return query.list();
    }

    @Override
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening, Integer offset) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select tb.pk_id as pk_id," +
                                             "       tt.pk_id as fk_type_groep_id," +
                                             "       tt.naam as type_groep," +
                                             "       tr.naam as rekening," +
                                             "       tp.voornaam || ' ' || tp.naam as persoon," +
                                             "       tb.bedrag as bedrag," +
                                             "       to_char(tb.datum, 'yyyy-mm-dd') as datum," +
                                             "       tb.omschrijving as omschrijving" +
                                             " from ta_bedragen tb" +
                                             " join ta_personen tp" +
                                             "   on (tb.fk_persoon_id = tp.pk_id)" +
                                             " join ta_rekeningen tr" +
                                             "   on (tb.fk_rekening_id = tr.pk_id)" +
                                             " join ta_type_groep tt" +
                                             "   on (tb.fk_type_groep_id = tt.pk_id)" +
                                             " where tb.fk_rekening_id = :rekeningId" + 
                                             " order by tb.datum desc, pk_id desc" +
                                             " offset " + 10 * offset + " rows fetch next 10 rows only").addEntity(BeheerBedragenReport.class);
        
        query.setInteger("rekeningId", rekening.getPk_id());
        return query.list();
    }

    @Override
    public Integer getNewPkId() {
        return ((BigDecimal)sessionFactory.getCurrentSession().createSQLQuery("select seq_ta_bedragen.nextval from dual").list().get(0)).intValue();
    }
    
    @Override
    public Bedragen findBedrag(Integer bedragId){
        return (Bedragen)sessionFactory.getCurrentSession().get(Bedragen.class, bedragId);
    }
    
    @Override
    public List<OverzichtBedrag> findAllBedragen(){
        return sessionFactory.getCurrentSession().createQuery("select new com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag(b.datum, b.bedrag, b.groep.negatief) " +
                                                              "from Bedragen b order by datum asc").list();
    }

    @Override
    public List<OverzichtBedrag> findAllBedragen(Date startDate, Date endDate){
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("select new com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag(b.datum, b.bedrag, b.groep.negatief) " +
                                          "from Bedragen b where datum between :start and :end order by datum asc");
        
        query.setDate("start", startDate);
        query.setDate("end", endDate);
        
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
        
        //Query query = session.createQuery("from Bedragen where rekening.pk_id = :rekeningId and datum between :start and :end order by datum asc");
        
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
    public BigDecimal somBedragDatum(Date datum) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select sum( case b.groep.negatief when 1 then (b.bedrag * -1) else b.bedrag end) " +
                                          " from Bedragen b where datum >= :datum");
        query.setDate("datum", datum);
        
        return (BigDecimal)query.list().get(0);
    }
    
    @Override
    public Bedragen getBedrag(Integer pk_id){
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("from Bedragen where pk_id = :pk_id");
        query.setInteger("pk_id", pk_id);
        
        return (Bedragen)query.list().get(0);
    }
}
