package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.HoofdCodes;

import jakarta.persistence.*;

@Entity
@Table(name="TA_CODE_HOOFD_GROEP")
public class HoofdCodesDaoPojo implements HoofdCodes {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="NAME")
    private String name;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HoofdCodesDaoPojo(){}

    public HoofdCodesDaoPojo(HoofdCodes hoofdCodes){
        this.pkId = hoofdCodes.getPkId();
        this.name = hoofdCodes.getName();
    }
}
