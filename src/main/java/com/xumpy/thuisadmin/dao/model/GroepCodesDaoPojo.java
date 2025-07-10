package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.GroepCodes;

import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="FK_CODE_HOOFD_GROEP_ID")
    private HoofdCodesDaoPojo hoofdCode;

    @Column(name="CODE_ID")
    private String codeId;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="YEAR")
    private Integer year;

    @Column(name="NEGATIEF")
    private Boolean negatief;

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

    @Override
    public HoofdCodesDaoPojo getHoofdCode() {
        return hoofdCode;
    }

    public void setHoofdCode(HoofdCodesDaoPojo hoofdCode) {
        this.hoofdCode = hoofdCode;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean isNegatief() {
        return negatief;
    }

    public void setNegatief(Boolean negatief) {
        this.negatief = negatief;
    }

    public GroepCodesDaoPojo(){}

    public GroepCodesDaoPojo(GroepCodes groepCode){
        this.pkId = groepCode.getPkId();
        this.groep = new GroepenDaoPojo(groepCode.getGroep());
        this.codeId = groepCode.getCodeId();
        this.hoofdCode = new HoofdCodesDaoPojo(groepCode.getHoofdCode());
        this.year = groepCode.getYear();
        this.description = groepCode.getDescription();
        this.negatief = groepCode.isNegatief();
    }
}
