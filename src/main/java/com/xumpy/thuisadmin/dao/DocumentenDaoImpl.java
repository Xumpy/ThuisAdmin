/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Repository
public class DocumentenDaoImpl implements DocumentenDao{

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
    public List<DocumentenReport> fetchDocumentenReport() {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("select bd.pk_id," +
                                             "       tg.naam as typeGroep," +
                                             "       to_char(bd.datum, 'yyyy/mm/dd') as datum," +
                                             "       b.bedrag as bedrag," +
                                             "       nvl(bd.omschrijving, b.omschrijving) as omschrijving" +
                                             " from ta_bedrag_documenten bd" +
                                             " left join ta_bedragen b" +
                                             "   on (b.pk_id = bd.fk_bedrag_id)" +
                                             " left join ta_type_groep tg" +
                                             "   on (tg.pk_id = bd.fk_type_groep_id)" +
                                             " order by nvl(datum, to_date('19001201', 'yyyymmdd')) desc").addEntity(DocumentenReport.class);
        
        return query.list();
    }

    @Override
    public Documenten fetchDocument(Integer documentId) {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("select pk_id," +
                                             "       fk_bedrag_id," +
                                             "       omschrijving," +
                                             "       document" +
                                             " from ta_bedrag_documenten" +
                                             " where pk_id = :pk_id").addEntity(Documenten.class);
        query.setInteger("pk_id", documentId);
        
        return (Documenten)query.list().get(0);
    }
}
