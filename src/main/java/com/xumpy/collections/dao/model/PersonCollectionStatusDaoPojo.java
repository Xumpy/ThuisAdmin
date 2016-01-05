/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.dao.model;

import com.xumpy.collections.domain.PersonCollectionStatus;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author nico
 */
@Entity
@Table(name="TA_PERSON_COL_STATUS")
public class PersonCollectionStatusDaoPojo implements PersonCollectionStatus, Serializable{
    @Id @Column(name="PK_ID") @GeneratedValue(strategy=GenerationType.AUTO) private Integer pkId;
    @Column(name="NAME") private String name;
    @Column(name="DESCRIPTION") private String description;
    
    @Override
    public Integer getPkId() {
        return this.pkId;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public PersonCollectionStatusDaoPojo(){}
    public PersonCollectionStatusDaoPojo(PersonCollectionStatus personCollectionStatus){
        this.pkId = personCollectionStatus.getPkId();
        this.name = personCollectionStatus.getName();
        this.description = personCollectionStatus.getDescription();
    }
}
