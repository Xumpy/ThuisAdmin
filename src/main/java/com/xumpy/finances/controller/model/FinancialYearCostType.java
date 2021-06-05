package com.xumpy.finances.controller.model;

import com.xumpy.government.domain.GovernmentCostType;

import java.math.BigDecimal;

public class FinancialYearCostType {
    private Integer level;
    private GovernmentCostType costType;
    private BigDecimal selectedCosts;
    private BigDecimal actualCosts;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public GovernmentCostType getCostType() {
        return costType;
    }

    public void setCostType(GovernmentCostType costType) {
        this.costType = costType;
    }

    public BigDecimal getSelectedCosts() {
        return selectedCosts;
    }

    public void setSelectedCosts(BigDecimal selectedCosts) {
        this.selectedCosts = selectedCosts;
    }

    public BigDecimal getActualCosts() {
        return actualCosts;
    }

    public void setActualCosts(BigDecimal actualCosts) {
        this.actualCosts = actualCosts;
    }
}
