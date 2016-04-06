/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services;

import com.xumpy.collections.domain.Collection;
import com.xumpy.collections.services.model.CollectionSrvPojo;
import java.util.List;

/**
 *
 * @author nico
 */
public interface CollectionSrv {
    public List<CollectionSrvPojo> getMainCollections();
    public List<CollectionSrvPojo> getSubCollections(Integer mainCollectionId);
    
    public CollectionSrvPojo select(Integer pkId);
    public CollectionSrvPojo save(Collection collection);
    public void delete(Collection collection);
}
