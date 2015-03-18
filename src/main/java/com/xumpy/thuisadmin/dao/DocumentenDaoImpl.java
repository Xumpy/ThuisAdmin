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
        
        Dialect dialect= ((SessionFactoryImplementor) sessionFactory).getDialect();
        
        if (dialect instanceof Oracle10gDialect){
            List<BigInteger> pkId = session.createSQLQuery("select seq_ta_bedrag_documenten.nextval as num from dual")
                    .addScalar("num", StandardBasicTypes.BIG_INTEGER).list();

            return pkId.get(0).intValue();
        } else {
            Query query = session.createQuery("select max(pk_id) as pk_id from Documenten");
            Integer maxPkId = (Integer)query.list().get(0);
            return maxPkId + 1;
        }
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
        
        Query query = session.createQuery("from Documenten where pk_id = :pk_id");
        query.setInteger("pk_id", documentId);
        
        return (Documenten)query.list().get(0);
    }
}
