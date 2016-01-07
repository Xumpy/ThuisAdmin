/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services.model;

import com.xumpy.collections.domain.Collection;
import com.xumpy.collections.domain.CollectionDetail;

/**
 *
 * @author nico
 */
public class CollectionDetailSrvPojo implements CollectionDetail{
    private Integer pkId;
    private CollectionSrvPojo collection;
    private String code;
    private String name;
    private String description;
    private byte[] image;
    private String imageName;
    private String imageMime;
    
    @Override
    public Integer getPkId() {
        return this.pkId;
    }

    @Override
    public Collection getCollection() {
        return this.collection;
    }

    @Override
    public String getCode() {
        return this.code;
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
    public byte[] getImage() {
        return this.image;
    }

    @Override
    public String getImageName() {
        return this.imageName;
    }

    @Override
    public String getImageMime() {
        return this.imageMime;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public void setCollection(CollectionSrvPojo collection) {
        this.collection = collection;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageMime(String imageMime) {
        this.imageMime = imageMime;
    }
    
    public CollectionDetailSrvPojo(){}
    
    public CollectionDetailSrvPojo(CollectionDetail collectionDetail){
        this.pkId = collectionDetail.getPkId();
        this.code = collectionDetail.getCode();
        this.name = collectionDetail.getName();
        this.collection = new CollectionSrvPojo(collectionDetail.getCollection());
        this.description = collectionDetail.getDescription();
        this.image = collectionDetail.getImage();
        this.imageMime = collectionDetail.getImageMime();
        this.imageName = collectionDetail.getImageName();
    }
}
