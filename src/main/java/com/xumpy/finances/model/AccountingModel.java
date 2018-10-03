package com.xumpy.finances.model;

import com.xumpy.finances.services.BedragWeightPojo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AccountingModel {
    private BedragWeightPojo revenue;
    private BedragWeightPojo costs;
    private Map<String, BigDecimal> governmentCosts;

    public AccountingModel(){
        this.revenue = new BedragWeightPojo();
        this.costs = new BedragWeightPojo();
        this.governmentCosts = new HashMap<>();
    }

    public BedragWeightPojo getRevenue() {
        return revenue;
    }

    public void setRevenue(BedragWeightPojo revenue) {
        this.revenue = revenue;
    }

    public BedragWeightPojo getCosts() {
        return costs;
    }

    public void setCosts(BedragWeightPojo costs) {
        this.costs = costs;
    }

    public Map<String, BigDecimal> getGovernmentCosts() {
        return governmentCosts;
    }

    public void setGovernmentCosts(Map<String, BigDecimal> governmentCosts) {
        this.governmentCosts = governmentCosts;
    }
}
