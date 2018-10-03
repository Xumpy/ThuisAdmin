package com.xumpy.finances.domain;

import com.xumpy.government.controllers.model.BusinessFormCtrlPojo;
import com.xumpy.government.controllers.model.GovernmentCostCtrlPojo;
import com.xumpy.government.controllers.model.GovernmentCostTypeCtrlPojo;
import com.xumpy.government.domain.GovernmentCost;
import com.xumpy.government.domain.GovernmentCostType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GovernmentCostMock {
    private static GovernmentCost governmentCost(BigDecimal amount, BigDecimal tax, GovernmentCostType governmentCostType){
        BusinessFormCtrlPojo businessForm = new BusinessFormCtrlPojo();
        businessForm.setBusinessForm("TEST");

        GovernmentCostCtrlPojo governmentCost = new GovernmentCostCtrlPojo();
        governmentCost.setBusinessForm(businessForm);
        governmentCost.setGovernmentCostType(new GovernmentCostTypeCtrlPojo(governmentCostType));
        governmentCost.setStartAmount(amount);
        governmentCost.setTaxPercentage(tax);

        return governmentCost;
    }

    private static GovernmentCost governmentCostRSZ(BigDecimal amount, BigDecimal tax){
        GovernmentCostTypeCtrlPojo governmentCostType = new GovernmentCostTypeCtrlPojo();

        governmentCostType.setLevel(1);
        governmentCostType.setType("RSZ");

        return governmentCost(amount, tax, governmentCostType);
    }

    private static GovernmentCost governmentCostBelasting(BigDecimal amount, BigDecimal tax){
        GovernmentCostTypeCtrlPojo governmentCostType = new GovernmentCostTypeCtrlPojo();

        governmentCostType.setLevel(2);
        governmentCostType.setType("Belastingen");

        return governmentCost(amount, tax, governmentCostType);
    }

    public static List<GovernmentCost> createGovernmentCostMock(){
        List<GovernmentCost> governmentCosts = new ArrayList<>();

        governmentCosts.add(governmentCostRSZ(new BigDecimal(13550.50), new BigDecimal(20.50)));
        governmentCosts.add(governmentCostRSZ(new BigDecimal(58513.59), new BigDecimal(14.16)));
        governmentCosts.add(governmentCostRSZ(new BigDecimal(86230.52), new BigDecimal(0.00)));

        governmentCosts.add(governmentCostBelasting(new BigDecimal(0), new BigDecimal(25.00)));
        governmentCosts.add(governmentCostBelasting(new BigDecimal(12990.01), new BigDecimal(40.00)));
        governmentCosts.add(governmentCostBelasting(new BigDecimal(22290.01), new BigDecimal(45.00)));
        governmentCosts.add(governmentCostBelasting(new BigDecimal(39660.01), new BigDecimal(50.00)));
        return governmentCosts;
    }
}
