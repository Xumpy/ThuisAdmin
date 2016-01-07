/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.services.model;

import com.xumpy.collections.domain.PersonCollectionStatus;

/**
 *
 * @author nico
 */
public class PersonCollectionStatusSrvPojo implements PersonCollectionStatus{
    private Integer pkId;
    private String name;
    private String description;
    
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
    
    public PersonCollectionStatusSrvPojo(){}
    
    public PersonCollectionStatusSrvPojo(PersonCollectionStatus personCollectionStatus){
        this.pkId = personCollectionStatus.getPkId();
        this.name = personCollectionStatus.getName();
        this.description = personCollectionStatus.getDescription();
    }
}
