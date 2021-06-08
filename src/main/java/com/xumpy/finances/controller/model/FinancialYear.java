package com.xumpy.finances.controller.model;

import java.math.BigDecimal;
import java.util.List;

public class FinancialYear {
    private Integer year;
    private BigDecimal selectedRevenue;
    private String actualRevenue;
    private BigDecimal selectedCosts;
    private String actualCosts;
    private List<FinancialYearCostType> financialYearCostTypes;
    private BigDecimal total;

    public String getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(String actualRevenue) {
        this.actualRevenue = actualRevenue;
    }

    public String getActualCosts() {
        return actualCosts;
    }

    public void setActualCosts(String actualCosts) {
        this.actualCosts = actualCosts;
    }

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

    public BigDecimal getSelectedCosts() {
        return selectedCosts;
    }

    public void setSelectedCosts(BigDecimal selectedCosts) {
        this.selectedCosts = selectedCosts;
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
