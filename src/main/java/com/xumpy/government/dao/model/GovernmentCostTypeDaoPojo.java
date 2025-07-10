package com.xumpy.government.dao.model;

import com.xumpy.government.domain.GovernmentCostType;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;

import jakarta.persistence.*;

@Entity
@Table(name="TA_GOVERNMENT_COST_TYPES")
public class GovernmentCostTypeDaoPojo implements GovernmentCostType{
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="TYPE")
    private String type;

    @Column(name="LEVEL")
    private Integer level;

    @ManyToOne
    @JoinColumn(name="FK_TYPE_GROEP_ID")
    private GroepenDaoPojo groep;

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
    public GroepenDaoPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenDaoPojo groep) {
        this.groep = groep;
    }

    public GovernmentCostTypeDaoPojo(){}
    public GovernmentCostTypeDaoPojo(GovernmentCostType governmentCostType){
        this.pkId = governmentCostType.getPkId();
        this.type = governmentCostType.getType();
        this.level = governmentCostType.getLevel();
        this.groep = new GroepenDaoPojo(governmentCostType.getGroep());
    }
}
