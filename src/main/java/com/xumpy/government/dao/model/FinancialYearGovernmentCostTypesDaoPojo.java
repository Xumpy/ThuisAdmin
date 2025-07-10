package com.xumpy.government.dao.model;

import com.xumpy.government.domain.FinancialYearGovernmentCostTypes;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TA_FIN_YEAR_GOV_COST_TYPES")
public class FinancialYearGovernmentCostTypesDaoPojo implements FinancialYearGovernmentCostTypes {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_FINANCIAL_YEAR_ID")
    private FinancialYearsDaoPojo financialYear;

    @ManyToOne
    @JoinColumn(name="FK_GOVERNMENT_COST_TYPE_ID")
    private GovernmentCostTypeDaoPojo governmentCostType;

    @Column(name="ACTUAL_COST")
    private BigDecimal actualCost;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public FinancialYearsDaoPojo getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(FinancialYearsDaoPojo financialYear) {
        this.financialYear = financialYear;
    }

    @Override
    public GovernmentCostTypeDaoPojo getGovernmentCostType() {
        return governmentCostType;
    }

    public void setGovernmentCostType(GovernmentCostTypeDaoPojo governmentCostType) {
        this.governmentCostType = governmentCostType;
    }

    @Override
    public BigDecimal getActualCost() {
        return actualCost;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public FinancialYearGovernmentCostTypesDaoPojo(){}

    public FinancialYearGovernmentCostTypesDaoPojo(FinancialYearGovernmentCostTypes financialYearGovernmentCostTypes){
        this.pkId = financialYearGovernmentCostTypes.getPkId();
        this.financialYear = new FinancialYearsDaoPojo(financialYearGovernmentCostTypes.getFinancialYear());
        this.governmentCostType = new GovernmentCostTypeDaoPojo(financialYearGovernmentCostTypes.getGovernmentCostType());
        this.actualCost = financialYearGovernmentCostTypes.getActualCost();
    }
}
