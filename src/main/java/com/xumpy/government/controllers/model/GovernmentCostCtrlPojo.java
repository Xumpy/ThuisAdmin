package com.xumpy.government.controllers.model;

import com.xumpy.government.domain.GovernmentCost;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GovernmentCostCtrlPojo implements GovernmentCost{
    private Integer pkId;
    private BigDecimal startAmount;
    private BigDecimal taxPercentage;
    private GovernmentCostTypeCtrlPojo governmentCostType;
    private BusinessFormCtrlPojo businessForm;

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
    public GovernmentCostTypeCtrlPojo getGovernmentCostType() {
        return governmentCostType;
    }

    public void setGovernmentCostType(GovernmentCostTypeCtrlPojo governmentCostType) {
        this.governmentCostType = governmentCostType;
    }

    @Override
    public BusinessFormCtrlPojo getBusinessForm() {
        return businessForm;
    }

    public void setBusinessForm(BusinessFormCtrlPojo businessForm) {
        this.businessForm = businessForm;
    }

    public GovernmentCostCtrlPojo(){}
    public GovernmentCostCtrlPojo(GovernmentCost governmentCost){
        this.pkId = governmentCost.getPkId();
        this.startAmount = governmentCost.getStartAmount();
        this.taxPercentage = governmentCost.getTaxPercentage();
        this.governmentCostType = governmentCost.getGovernmentCostType() != null ? new GovernmentCostTypeCtrlPojo(governmentCost.getGovernmentCostType()) : null;
        this.businessForm = governmentCost.getBusinessForm() != null ? new BusinessFormCtrlPojo(governmentCost.getBusinessForm()) : null;
    }
}
