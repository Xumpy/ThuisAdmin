package com.xumpy.government.dao.model;

import com.xumpy.government.domain.GovernmentCost;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="TA_GOVERNMENT_COSTS")
public class GovernmentCostDaoPojo implements GovernmentCost {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="START_AMOUNT")
    private BigDecimal startAmount;

    @Column(name="TAX_PERC")
    private BigDecimal taxPercentage;

    @ManyToOne
    @JoinColumn(name="FK_GOVERNMENT_COST_TYPE_ID")
    @NotNull
    private GovernmentCostTypeDaoPojo governmentCostType;

    @ManyToOne
    @JoinColumn(name="FK_BUSINESS_FORM_ID")
    @NotNull
    private BusinessFormDaoPojo businessForm;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public BigDecimal getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
    }

    @Override
    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Override
    public GovernmentCostTypeDaoPojo getGovernmentCostType() {
        return governmentCostType;
    }

    public void setGovernmentCostType(GovernmentCostTypeDaoPojo governmentCostType) {
        this.governmentCostType = governmentCostType;
    }

    @Override
    public BusinessFormDaoPojo getBusinessForm() {
        return businessForm;
    }

    public void setBusinessForm(BusinessFormDaoPojo businessForm) {
        this.businessForm = businessForm;
    }

    public GovernmentCostDaoPojo(){}
    public GovernmentCostDaoPojo(GovernmentCost governmentCost){
        this.pkId = governmentCost.getPkId();
        this.startAmount = governmentCost.getStartAmount();
        this.taxPercentage = governmentCost.getTaxPercentage();
        this.governmentCostType = governmentCost.getGovernmentCostType() != null ? new GovernmentCostTypeDaoPojo(governmentCost.getGovernmentCostType()) : null;
        this.businessForm = governmentCost.getBusinessForm() != null ? new BusinessFormDaoPojo(governmentCost.getBusinessForm()) : null;
    }
}
