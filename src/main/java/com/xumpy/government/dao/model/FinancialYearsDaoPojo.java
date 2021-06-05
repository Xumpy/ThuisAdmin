package com.xumpy.government.dao.model;

import com.xumpy.government.domain.FinancialYears;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TA_FINANCIAL_YEARS")
public class FinancialYearsDaoPojo implements FinancialYears {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="YEAR")
    private Integer year;

    @Column(name="REVENUE")
    private BigDecimal revenue;

    @Column(name="COSTS")
    private BigDecimal costs;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @Override
    public BigDecimal getCosts() {
        return costs;
    }

    public void setCosts(BigDecimal costs) {
        this.costs = costs;
    }

    public FinancialYearsDaoPojo(){}

    public FinancialYearsDaoPojo(FinancialYears financielYears){
        this.pkId = financielYears.getPkId();
        this.year = financielYears.getYear();
        this.revenue = financielYears.getRevenue();
        this.costs = financielYears.getCosts();
    }
}
