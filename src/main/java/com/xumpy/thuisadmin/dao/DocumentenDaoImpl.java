/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public class DocumentenDaoImpl implements DocumentenDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private Personen persoon;
    
    @Override
    public void save(Documenten document) {
        sessionFactory.getCurrentSession().save(document);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(Documenten document) {
        sessionFactory.getCurrentSession().update(document);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Documenten document) {
        sessionFactory.getCurrentSession().delete(document);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Integer getNewPkId() {
        Session session = sessionFactory.getCurrentSession();
        
        List<BigInteger> pkId = session.createSQLQuery("select seq_ta_bedrag_documenten.nextval as num from dual")
                .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();
        
        return pkId.get(0).intValue();
    }
    
    @Override
    public List<DocumentenReport> fetchDocumentenReport() {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("from Documenten where bedrag.persoon.pk_id = :persoonId");
        query.setInteger("persoonId", persoon.getPk_id());
        
        List<Documenten> documenten = query.list();
        
        List<DocumentenReport> documentReport = new ArrayList<DocumentenReport>();
        for(Documenten document: documenten){
          documentReport.add(new DocumentenReport(document));
        }
        return documentReport;
    }

    @Override
    public List<Documenten> fetchDocumentByBedrag(Integer bedragId){
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("from Documenten where bedrag.pk_id = :bedragId");
        query.setInteger("bedragId", bedragId);
        
        return query.list();
    }
    
    @Override
    public Documenten fetchDocument(Integer documentId) {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("select pk_id," +
                                             "       fk_bedrag_id," +
                                             "       omschrijving," +
                                             "       document," +
                                             "       document_naam," +
                                             "       document_mime" +
                                             " from ta_bedrag_documenten" +
                                             " where pk_id = :pk_id").addEntity(Documenten.class);
        query.setInteger("pk_id", documentId);
        
        return (Documenten)query.list().get(0);
    }
}
