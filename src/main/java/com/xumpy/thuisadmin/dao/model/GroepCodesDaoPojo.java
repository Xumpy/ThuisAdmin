package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.GroepCodes;

import javax.persistence.*;

@Entity
@Table(name="TA_CODE_TYPE_GROEP")
public class GroepCodesDaoPojo implements GroepCodes {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_TYPE_GROEP_ID")
    private GroepenDaoPojo groep;


    @Column(name="CODE_ID")
    private String codeId;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public GroepenDaoPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenDaoPojo groep) {
        this.groep = groep;
    }

    @Override
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public GroepCodesDaoPojo(){}

    public GroepCodesDaoPojo(GroepCodes groepCode){
        this.pkId = groepCode.getPkId();
        this.groep = new GroepenDaoPojo(groepCode.getGroep());
        this.codeId = groepCode.getCodeId();
    }
}
