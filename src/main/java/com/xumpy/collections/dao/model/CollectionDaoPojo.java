/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.model;

import com.xumpy.collections.domain.Collection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nico
 */
@Entity
@Table(name="TA_COLLECTIONS")
public class CollectionDaoPojo implements Collection, Serializable{
    @Id @Column(name="PK_ID") @GeneratedValue(strategy=GenerationType.AUTO) private Integer pkId;
    @ManyToOne @JoinColumn(name="FK_MAIN_COLLECTION_ID") private CollectionDaoPojo mainCollection;
    @OneToMany(mappedBy="mainCollection") private List<CollectionDaoPojo> subCollections;
    @Column(name="NAME") private String name;
    @Column(name="DESCRIPTION") private String description;
    
    @Override
    public Integer getPkId() {
        return this.pkId;
    }

    @Override
    public Collection getMainCollection() {
        return this.mainCollection;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<? extends Collection> getSubCollections() {
        return this.subCollections;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public void setMainCollection(CollectionDaoPojo mainCollection) {
        this.mainCollection = mainCollection;
    }

    public void setSubCollections(List<CollectionDaoPojo> subCollections) {
        this.subCollections = subCollections;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public CollectionDaoPojo(){}
    public CollectionDaoPojo(Collection collection){
        this.pkId = collection.getPkId();
        this.name = collection.getName();
        this.description = collection.getDescription();
        this.mainCollection = collection.getMainCollection() != null ? new CollectionDaoPojo(collection.getMainCollection()) : null;
        this.subCollections = !collection.getSubCollections().isEmpty() ? CollectionDaoPojo.lstCollectionDaoPojo(collection.getSubCollections()) : null;
    }
    public static List<CollectionDaoPojo> lstCollectionDaoPojo(List<? extends Collection> collections){
        List<CollectionDaoPojo> lstCollection = new ArrayList<CollectionDaoPojo>();
        for(Collection collection: collections){
            lstCollection.add(new CollectionDaoPojo(collection));
        }
        
        return lstCollection;
    }
}
