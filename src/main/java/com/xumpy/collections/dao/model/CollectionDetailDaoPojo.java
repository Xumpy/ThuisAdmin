/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.model;

import com.xumpy.collections.domain.Collection;
import com.xumpy.collections.domain.CollectionDetail;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author nico
 */
@Entity
@Table(name="TA_COLLECTION_DETAILS")
public class CollectionDetailDaoPojo implements CollectionDetail, Serializable{
    @Id @Column(name="PK_ID") @GeneratedValue(strategy=GenerationType.AUTO) private Integer pkId;
    @ManyToOne @JoinColumn(name="FK_COLLECTION_ID") private CollectionDaoPojo collection;
    @Column(name="CODE") private String code;
    @Column(name="NAME") private String name;
    @Column(name="DESCRIPTION") private String description;
    @Column(name="IMAGE") private byte[] image;
    @Column(name="IMAGE_NAME") private String imageName;
    @Column(name="IMAGE_MIME") private String imageMime;
    
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

    public void setCollection(CollectionDaoPojo collection) {
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
    
    public CollectionDetailDaoPojo(){}
    public CollectionDetailDaoPojo(CollectionDetail collectionDetail){
        this.pkId = collectionDetail.getPkId();
        this.collection = new CollectionDaoPojo(collectionDetail.getCollection());
        this.description = collectionDetail.getDescription();
        this.name = collectionDetail.getName();
        this.code = collectionDetail.getCode();
        this.image = collectionDetail.getImage();
        this.imageName = collectionDetail.getImageName();
        this.imageMime = collectionDetail.getImageMime();
    }
}
