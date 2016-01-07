/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services;

import com.xumpy.collections.dao.crud.CollectionDao;
import com.xumpy.collections.dao.crud.CollectionDetailDao;
import com.xumpy.collections.dao.crud.PersonCollectionDao;
import com.xumpy.collections.dao.crud.PersonCollectionStatusDao;
import com.xumpy.collections.dao.model.CollectionDaoPojo;
import com.xumpy.collections.dao.model.CollectionDetailDaoPojo;
import com.xumpy.collections.dao.model.PersonCollectionDaoPojo;
import com.xumpy.collections.dao.model.PersonCollectionStatusDaoPojo;
import com.xumpy.migration.collections.mysql.WriteJeromCSV;
import static com.xumpy.migration.collections.mysql.WriteJeromCSV.build;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.domain.Personen;
import com.xumpy.thuisadmin.services.PersonenSrv;
import java.io.FileInputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nico
 */
@Service
public class MigrationSrv {
    @Autowired PersonenSrv personenSrv;
    @Autowired CollectionDao collectionDao;
    @Autowired PersonCollectionDao personCollectionDao;
    @Autowired CollectionDetailDao collectionDetailDao;
    @Autowired PersonCollectionStatusDao personCollectionStatusDao;

    private static Logger log = Logger.getLogger(MigrationSrv.class);
        
    public void migrate() throws Exception{
        Personen persoon = personenSrv.findPersoonByUsername("NICO");
        migrate(persoon);
    }
    
    @Transactional(readOnly=false)
    public void migrate(Personen persoon) throws Exception{
        FileInputStream inputStream = new FileInputStream(WriteJeromCSV.class.getClassLoader().getResource("testData/verzameling.csv").getPath());
        String content = IOUtils.toString(inputStream);

        List<WriteJeromCSV.Lijst> lstLijst = build(content);

        CollectionDaoPojo colStrip = new CollectionDaoPojo();
        CollectionDaoPojo colJerom = new CollectionDaoPojo();
        
        colStrip.setName("Strips");
        colStrip = collectionDao.save(colStrip);
        
        colJerom.setName("Jerom");
        colJerom.setMainCollection(colStrip);
        colJerom = collectionDao.save(colJerom);
        
        PersonCollectionStatusDaoPojo notOwned = personCollectionStatusDao.findOne(1);
        PersonCollectionStatusDaoPojo owned = personCollectionStatusDao.findOne(2);
        
        for (WriteJeromCSV.Lijst lijst: lstLijst){
            CollectionDetailDaoPojo collectionDetail = new CollectionDetailDaoPojo();
            
            collectionDetail.setCode(lijst.getCode());
            collectionDetail.setCollection(colJerom);
            collectionDetail.setName(lijst.getName());
            
            collectionDetail = collectionDetailDao.save(collectionDetail);
            
            PersonCollectionDaoPojo personCollection = new PersonCollectionDaoPojo();
            personCollection.setCollectionDetail(collectionDetail);
            personCollection.setPerson(new PersonenDaoPojo(persoon));
            if (lijst.getInMijnBezit().equals("1")){
                personCollection.setPersonCollectionStatus(owned);
            } else {
                personCollection.setPersonCollectionStatus(notOwned);
            }
            
            personCollectionDao.save(personCollection);
        }
    }
}
