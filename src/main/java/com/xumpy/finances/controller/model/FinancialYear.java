package com.xumpy.finances.controller.model;

import java.math.BigDecimal;
import java.util.List;

public class FinancialYear {
    private Integer year;
    private BigDecimal selectedRevenue;
    private BigDecimal actualRevenue;
    private BigDecimal selectedCosts;
    private BigDecimal actualCosts;
    private List<FinancialYearCostType> financialYearCostTypes;
    private BigDecimal total;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getSelectedRevenue() {
        return selectedRevenue;
    }

    public void setSelectedRevenue(BigDecimal selectedRevenue) {
        this.selectedRevenue = selectedRevenue;
    }

    public BigDecimal getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(BigDecimal actualRevenue) {
        this.actualRevenue = actualRevenue;
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

    public List<FinancialYearCostType> getFinancialYearCostTypes() {
        return financialYearCostTypes;
    }

    public void setFinancialYearCostTypes(List<FinancialYearCostType> financialYearCostTypes) {
        this.financialYearCostTypes = financialYearCostTypes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
