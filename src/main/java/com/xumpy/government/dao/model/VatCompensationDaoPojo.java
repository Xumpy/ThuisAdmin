package com.xumpy.government.dao.model;

import com.xumpy.government.domain.VatCompensation;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TA_VAT_COMPENSATIONS")
public class VatCompensationDaoPojo implements VatCompensation{
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;


    @Column(name="NAME")
    private String name;

    @Column(name="UNIT_PRICE")
    private BigDecimal unitPrice;

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

    @Override
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public VatCompensationDaoPojo(){}

    public VatCompensationDaoPojo(VatCompensation vatCompensation){
        this.pkId = vatCompensation.getPkId();
        this.name = vatCompensation.getName();
        this.unitPrice = vatCompensation.getUnitPrice();
    }
}
