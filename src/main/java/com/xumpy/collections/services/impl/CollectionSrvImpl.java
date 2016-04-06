/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services.impl;

import com.xumpy.collections.dao.crud.CollectionDao;
import com.xumpy.collections.dao.model.CollectionDaoBuilder;
import com.xumpy.collections.domain.Collection;
import com.xumpy.collections.services.CollectionSrv;
import com.xumpy.collections.services.model.CollectionSrvPojo;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nico
 */
@Service
public class CollectionSrvImpl implements CollectionSrv{
    @Autowired CollectionDaoBuilder collectionDaoBuilder;
    @Autowired CollectionDao collectionDao;
    
    private static final Logger log = Logger.getLogger(CollectionSrvImpl.class);
    
    @Override
    @Transactional
    public List<CollectionSrvPojo> getMainCollections() {
        return CollectionSrvPojo.lstCollectionSrvPojo(collectionDao.getMainCollections());
    }

    @Override
    @Transactional
    public List<CollectionSrvPojo> getSubCollections(Integer mainCollectionId) {
        return CollectionSrvPojo.lstCollectionSrvPojo(collectionDao.getSubCollections(mainCollectionId));
    }

    @Override
    @Transactional
    public CollectionSrvPojo select(Integer pkId) {
        return new CollectionSrvPojo(collectionDao.findOne(pkId));
    }

    @Override
    @Transactional
    public CollectionSrvPojo save(Collection collection) {
        return new CollectionSrvPojo(collectionDao.save(collectionDaoBuilder.buildCollection(collection)));
    }

    @Override
    @Transactional
    public void delete(Collection collection) {
        collectionDao.delete(collectionDaoBuilder.buildCollection(collection));
    }
    
}
