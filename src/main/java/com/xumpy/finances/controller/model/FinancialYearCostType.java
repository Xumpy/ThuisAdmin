package com.xumpy.finances.controller.model;

import com.xumpy.government.controllers.model.GovernmentCostTypeCtrlPojo;
import com.xumpy.government.domain.GovernmentCostType;

import java.math.BigDecimal;

public class FinancialYearCostType {
    private GovernmentCostTypeCtrlPojo costType;
    private BigDecimal selectedCosts;
    private String actualCosts;

    public GovernmentCostTypeCtrlPojo getCostType() {
        return costType;
    }

    public void setCostType(GovernmentCostTypeCtrlPojo costType) {
        this.costType = costType;
    }

    public BigDecimal getSelectedCosts() {
        return selectedCosts;
    }

    public void setSelectedCosts(BigDecimal selectedCosts) {
        this.selectedCosts = selectedCosts;
    }

    public String getActualCosts() {
        return actualCosts;
    }

    public void setActualCosts(String actualCosts) {
        this.actualCosts = actualCosts;
    }
}
