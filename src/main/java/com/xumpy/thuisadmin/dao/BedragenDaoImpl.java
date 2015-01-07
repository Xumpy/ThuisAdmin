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
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
public class BedragenDaoImpl extends HibernateDaoSupport implements BedragenDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Bedragen bedragen) {
        getHibernateTemplate().save(bedragen);
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Bedragen bedragen) {
        getHibernateTemplate().update(bedragen);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Bedragen bedragen) {
        getHibernateTemplate().delete(bedragen);
    }

    @Override
    public List<Bedragen> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select to_char(datum, 'DD-MM-RRRR') as label," +
                                          "       rekening_bedrag as value " +
                                          "from table(pkg_ta_rekening.fun_overzicht(" +
                                          "            in_fk_rekening_id => ?," +
                                          "            in_start_datum => ?," +
                                          "            in_eind_datum => ?," +
                                          "            in_interval => 1)) " +
                                          "order by datum asc");
        if (rekening == null){
            query.setInteger(0, -1);
        } else {
            query.setInteger(0, rekening.getPk_id());
        }
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
                                             "       to_char(tb.datum, 'yyyy/mm/dd') as datum," +
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
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select tb.pk_id as pk_id," +
                                             "       tt.pk_id as fk_type_groep_id," +
                                             "       tt.naam as type_groep," +
                                             "       tr.naam as rekening," +
                                             "       tp.voornaam || ' ' || tp.naam as persoon," +
                                             "       tb.bedrag as bedrag," +
                                             "       to_char(tb.datum, 'dd/mm/yyyy') as datum," +
                                             "       tb.omschrijving as omschrijving" +
                                             " from ta_bedragen tb" +
                                             " join ta_personen tp" +
                                             "   on (tb.fk_persoon_id = tp.pk_id)" +
                                             " join ta_rekeningen tr" +
                                             "   on (tb.fk_rekening_id = tr.pk_id)" +
                                             " join ta_type_groep tt" +
                                             "   on (tb.fk_type_groep_id = tt.pk_id)" +
                                             " where tb.fk_rekening_id = :rekeningId" + 
                                             " order by tb.datum desc").addEntity(BeheerBedragenReport.class);
        
        query.setInteger("rekeningId", rekening.getPk_id());
        return query.list();
    }
}
