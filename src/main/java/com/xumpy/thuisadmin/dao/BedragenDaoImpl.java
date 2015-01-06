/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
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
}
