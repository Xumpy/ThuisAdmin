/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.controllers.model.DocumentenReport;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */

@Service
public class DocumentenSrvImpl implements DocumentenSrv, Serializable{

    @Autowired
    private DocumentenDaoImpl documentenDao;
    
    @Autowired UserInfo userInfo;
    
    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void save(Documenten document) {
        DocumentenSrvPojo documentenSrvPojo = new DocumentenSrvPojo(document);
        
        if (documentenSrvPojo.getPk_id() == null){
            documentenSrvPojo.setPk_id(documentenDao.getNewPkId());
            documentenDao.save(new DocumentenDaoPojo(documentenSrvPojo));
        } else {
            documentenDao.save(new DocumentenDaoPojo(documentenSrvPojo));
        }
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void update(Documenten document) {
        documentenDao.save(new DocumentenDaoPojo(document));
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void delete(Documenten document) {
        documentenDao.delete(new DocumentenDaoPojo(document));
    }

    @Override
    @Transactional(value="transactionManager")
    public List<DocumentenReport> fetchDocumentenReport() {
        List<? extends Documenten> documenten = documentenDao.fetchDocumentenReport(userInfo.getPersoon().getPk_id());
        
        List<DocumentenReport> documentReport = new ArrayList<DocumentenReport>();
        for(Documenten document: documenten){
          documentReport.add(new DocumentenReport(new DocumentenDaoPojo(document)));
        }
        return documentReport;
    }

    @Override
    @Transactional(value="transactionManager")
    public Documenten fetchDocument(Integer documentId){
        return documentenDao.findOne(documentId);
    }
    
    @Override
    @Transactional(value="transactionManager")
    public List<? extends Documenten> fetchDocumentByBedrag(Integer bedragId){
        return documentenDao.fetchDocumentByBedrag(bedragId);
    }
}
