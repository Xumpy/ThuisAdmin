package com.xumpy.finances.domain;

import com.xumpy.finances.model.AccountingModel;
import com.xumpy.finances.services.BedragWeightPojo;

import java.math.BigDecimal;

/**
 * Created by nico on 17/09/2018.
 "revenue":{
     "bedragExclusiveTax":29750.000000,
     "bedragTax":4147.50000000,
     "bedragWeightExclusiveTax":19750.000000,
     "bedragWeightTax":4147.50000000,
     "bedragLeftOverExclusiveTax":10000.0000,
     "bedragLeftOverTax":0.0000
 },
 "costs":{
     "bedragExclusiveTax":7819.9381,
     "bedragTax":455.2419,
     "bedragWeightExclusiveTax":2293.6786,
     "bedragWeightTax":432.6366,
     "bedragLeftOverExclusiveTax":5526.2595,
     "bedragLeftOverTax":22.6053
 }
 */
public class AccountingModelMock {
    private static BedragWeightPojo createBedrag(BigDecimal bedragExclusiveTax,
                                                 BigDecimal bedragTax,
                                                 BigDecimal bedragWeightExclusiveTax,
                                                 BigDecimal bedragWeightTax,
                                                 BigDecimal bedragLeftOverExclusiveTax,
                                                 BigDecimal bedragLeftOverTax){
        BedragWeightPojo bedragWeightPojo = new BedragWeightPojo();

        bedragWeightPojo.setBedragExclusiveTax(bedragExclusiveTax);
        bedragWeightPojo.setBedragLeftOverExclusiveTax(bedragLeftOverExclusiveTax);
        bedragWeightPojo.setBedragLeftOverTax(bedragLeftOverTax);
        bedragWeightPojo.setBedragTax(bedragTax);
        bedragWeightPojo.setBedragWeightExclusiveTax(bedragWeightExclusiveTax);
        bedragWeightPojo.setBedragWeightTax(bedragWeightTax);

        return bedragWeightPojo;
    }
    public static AccountingModel createAccountingModelMock(){
        AccountingModel accountingModel = new AccountingModel();
        accountingModel.setRevenue(createBedrag(new BigDecimal(29750.000000),
                new BigDecimal(4147.50000000),
                new BigDecimal(19750.000000),
                new BigDecimal(4147.50000000),
                new BigDecimal(10000.0000),
                new BigDecimal(0.0000)));
        accountingModel.setCosts(createBedrag(new BigDecimal(7819.9381),
                new BigDecimal(455.2419),
                new BigDecimal(2293.6786),
                new BigDecimal(432.6366),
                new BigDecimal(5526.2595),
                new BigDecimal(22.6053)));

        return accountingModel;
    }
}
