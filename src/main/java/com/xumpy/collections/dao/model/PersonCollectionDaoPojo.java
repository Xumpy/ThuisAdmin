/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.model;

import com.xumpy.collections.domain.CollectionDetail;
import com.xumpy.collections.domain.PersonCollection;
import com.xumpy.collections.domain.PersonCollectionStatus;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.domain.Personen;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author nico
 */
@Entity
@Table(name="TA_PERSON_COLLECTIONS")
public class PersonCollectionDaoPojo implements PersonCollection, Serializable{
    @Id @Column(name="PK_ID") @GeneratedValue(strategy=GenerationType.AUTO) private Integer pkId;
    @ManyToOne @JoinColumn(name="FK_PERSON_ID") private PersonenDaoPojo person;
    @ManyToOne @JoinColumn(name="FK_COLLECTION_DETAIL_ID") private CollectionDetailDaoPojo collectionDetail;
    @ManyToOne @JoinColumn(name="FK_PERSON_COL_STATUS_ID") private PersonCollectionStatusDaoPojo personCollectionStatus;
    
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
        return this.personCollectionStatus;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public void setPerson(PersonenDaoPojo person) {
        this.person = person;
    }

    public void setCollectionDetail(CollectionDetailDaoPojo collectionDetail) {
        this.collectionDetail = collectionDetail;
    }

    public void setPersonCollectionStatus(PersonCollectionStatusDaoPojo personCollectionStatus) {
        this.personCollectionStatus = personCollectionStatus;
    }
}
