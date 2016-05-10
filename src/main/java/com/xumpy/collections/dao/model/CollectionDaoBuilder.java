/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.model;

import com.xumpy.collections.dao.crud.CollectionDao;
import com.xumpy.collections.domain.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author nico
 */
@Component
public class CollectionDaoBuilder {
    @Autowired CollectionDao collectionDao;
    
    public CollectionDaoPojo buildCollection(Collection collection){
        CollectionDaoPojo collectionDaoPojo = new CollectionDaoPojo();
        
        collectionDaoPojo.setPkId(collection.getPkId());
        collectionDaoPojo.setMainCollection(collectionDao.findOne(collection.getMainCollectionId()));
        collectionDaoPojo.setName(collection.getName());
        collectionDaoPojo.setSubCollections(collectionDao.getSubCollections(collection.getPkId()));
        
        return collectionDaoPojo;
    }
}
