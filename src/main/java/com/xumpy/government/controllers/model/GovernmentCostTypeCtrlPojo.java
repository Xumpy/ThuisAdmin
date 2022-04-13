package com.xumpy.government.controllers.model;

import com.xumpy.government.domain.GovernmentCostType;
import com.xumpy.thuisadmin.controllers.model.GroepenCtrlPojo;
import com.xumpy.thuisadmin.controllers.rest.GroepenCtrl;
import com.xumpy.thuisadmin.services.implementations.GroepCodesSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GovernmentCostTypeCtrlPojo implements GovernmentCostType{
    private Integer pkId;
    private String type;
    private Integer level;
    private GroepenCtrlPojo groep;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public GroepenCtrlPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenCtrlPojo groep) {
        this.groep = groep;
    }

    public GovernmentCostTypeCtrlPojo(){}
    public GovernmentCostTypeCtrlPojo(GovernmentCostType governmentCostType){
        this.pkId = governmentCostType.getPkId();
        this.type = governmentCostType.getType();
        this.level = governmentCostType.getLevel();
        this.groep = governmentCostType.getGroep() != null ? new GroepenCtrlPojo(governmentCostType.getGroep()) : null;
    }
}
