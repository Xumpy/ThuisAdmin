/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.crud;

import com.xumpy.collections.dao.model.CollectionDaoPojo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nico
 */
public interface CollectionDao  extends CrudRepository<CollectionDaoPojo, Integer>{
    @Query("select c from CollectionDaoPojo c where mainCollection is null")
    public List<CollectionDaoPojo> getMainCollections();
    
    @Query("select c from CollectionDaoPojo c where mainCollection.pkId = :fk_main_collection_id")
    public List<CollectionDaoPojo> getSubCollections(@Param("fk_main_collection_id") Integer mainCollectionId);
}
