package com.xumpy.finances.controller.model;

import com.xumpy.thuisadmin.domain.HoofdCodes;

public class HoofdCodesCtrlPojo implements HoofdCodes{
    private Integer pkId;
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

    public HoofdCodesCtrlPojo(){}

    public HoofdCodesCtrlPojo(HoofdCodes hoofdCodes){
        this.name = hoofdCodes.getName();
        this.pkId = hoofdCodes.getPkId();
    }
}
