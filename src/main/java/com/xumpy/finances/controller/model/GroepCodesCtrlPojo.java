package com.xumpy.finances.controller.model;

import com.xumpy.thuisadmin.controllers.model.GroepenCtrlPojo;
import com.xumpy.thuisadmin.domain.GroepCodes;

public class GroepCodesCtrlPojo implements GroepCodes {
    private Integer pkId;
    private GroepenCtrlPojo groep;
    private HoofdCodesCtrlPojo hoofdCode;
    private String codeId;
    private String description;
    private Integer year;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public GroepenCtrlPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenCtrlPojo groep) {
        this.groep = groep;
    }

    @Override
    public HoofdCodesCtrlPojo getHoofdCode() {
        return hoofdCode;
    }

    public void setHoofdCode(HoofdCodesCtrlPojo hoofdCode) {
        this.hoofdCode = hoofdCode;
    }

    @Override
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public GroepCodesCtrlPojo() {}

    public GroepCodesCtrlPojo(GroepCodes groepCodes){
        this.pkId = groepCodes.getPkId();
        this.groep = new GroepenCtrlPojo(groepCodes.getGroep());
        this.codeId = groepCodes.getCodeId();
        this.hoofdCode = new HoofdCodesCtrlPojo(groepCodes.getHoofdCode());
        this.year = groepCodes.getYear();
        this.description = groepCodes.getDescription();
    }
}
