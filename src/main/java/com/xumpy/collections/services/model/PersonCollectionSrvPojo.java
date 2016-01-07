/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services.model;

import com.xumpy.collections.domain.CollectionDetail;
import com.xumpy.collections.domain.PersonCollection;
import com.xumpy.collections.domain.PersonCollectionStatus;
import com.xumpy.thuisadmin.domain.Personen;
import com.xumpy.thuisadmin.services.model.PersonenSrvPojo;

/**
 *
 * @author nico
 */
public class PersonCollectionSrvPojo implements PersonCollection{
    private Integer pkId;
    private PersonenSrvPojo person;
    private CollectionDetailSrvPojo collectionDetail;
    private PersonCollectionStatusSrvPojo collectionStatus;
    
    @Override
    public Integer getPkId() {
        return this.pkId;
    }

    @Override
    public Personen getPerson() {
        return this.person;
    }

    @Override
    public CollectionDetail getCollectionDetail() {
        return this.collectionDetail;
    }

    @Override
    public PersonCollectionStatus getPersonCollectionStatus() {
        return this.collectionStatus;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public void setPerson(PersonenSrvPojo person) {
        this.person = person;
    }

    public void setCollectionDetail(CollectionDetailSrvPojo collectionDetail) {
        this.collectionDetail = collectionDetail;
    }

    public void setCollectionStatus(PersonCollectionStatusSrvPojo collectionStatus) {
        this.collectionStatus = collectionStatus;
    }
    
    public PersonCollectionSrvPojo(){}
    public PersonCollectionSrvPojo(PersonCollection personCollection){
        this.pkId = personCollection.getPkId();
        this.person = new PersonenSrvPojo(personCollection.getPerson());
        this.collectionDetail = new CollectionDetailSrvPojo(personCollection.getCollectionDetail());
        this.collectionStatus = new PersonCollectionStatusSrvPojo(personCollection.getPersonCollectionStatus());
    }
}
