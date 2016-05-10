/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services.model;

import com.xumpy.collections.domain.Collection;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nico
 */
public class CollectionSrvPojo implements Collection{
    private Integer pkId;
    private Integer mainCollectionId;
    private List<CollectionSrvPojo> subCollections;
    private String name;
    private String description;
    private static Logger log = Logger.getLogger(CollectionSrvPojo.class);
    
    @Override
    public Integer getPkId() {
        return this.pkId;
    }
    
    @Override
    public Integer getMainCollectionId(){
        return this.mainCollectionId;
    }

    @Override
    public List<? extends Collection> getSubCollections() {
        return this.subCollections;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }
    
    public void setMainCollectionId(Integer mainCollectionId){
        this.mainCollectionId = mainCollectionId;
    }
    
    public void setSubCollections(List<CollectionSrvPojo> subCollections) {
        this.subCollections = subCollections;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public CollectionSrvPojo(){}
    
    public CollectionSrvPojo(Collection collection){
        this.pkId = collection.getPkId();
        this.name = collection.getName();
        this.description = collection.getDescription();
        this.mainCollectionId = collection.getMainCollectionId();
        this.subCollections = !collection.getSubCollections().isEmpty() ? lstCollectionSrvPojo(collection.getSubCollections()) : null;
    }
    public static List<CollectionSrvPojo> lstCollectionSrvPojo(List<? extends Collection> collections){
        List<CollectionSrvPojo> lstCollection = new ArrayList<CollectionSrvPojo>();
        
        log.info("Value collections: " + collections);
 
        for(Collection collection: collections){
            lstCollection.add(new CollectionSrvPojo(collection));
        }
        
        return lstCollection;
    }
}
