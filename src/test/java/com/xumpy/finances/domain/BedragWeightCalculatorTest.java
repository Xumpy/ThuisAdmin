package com.xumpy.finances.domain;


import com.xumpy.finances.model.AccountingModel;
import com.xumpy.finances.services.AmountTaxAndWeightCalculator;
import com.xumpy.finances.services.BedragWeightPojo;
import com.xumpy.government.domain.GovernmentCost;
import com.xumpy.thuisadmin.domain.Bedragen;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class BedragWeightCalculatorTest {
    @Test
    public void testBedragWeightCalculatorWith75(){
        Bedragen bedrag = Mockito.mock(Bedragen.class);
        Mockito.when(bedrag.getBedrag()).thenReturn(new BigDecimal(115.56));
        Mockito.when(bedrag.getTaxPercentagePaid()).thenReturn(new BigDecimal(21));
        Mockito.when(bedrag.getWeightAccountancy()).thenReturn(new BigDecimal(75));

        BedragWeightPojo bedragWeightCalculator = new BedragWeightPojo(bedrag);

        Assert.assertEquals(new BigDecimal(95.5041).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragExclusiveTax());
        Assert.assertEquals(new BigDecimal(20.0559).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragTax());
        Assert.assertEquals(new BigDecimal(71.6281).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightExclusiveTax());
        Assert.assertEquals(new BigDecimal(15.0419).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightTax());
        Assert.assertEquals(new BigDecimal(23.8760).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverExclusiveTax());
        Assert.assertEquals(new BigDecimal(5.0140).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverTax());
    }

    @Test
    public void testBedragWeightCalculatorWith100(){
        Bedragen bedrag = Mockito.mock(Bedragen.class);
        Mockito.when(bedrag.getBedrag()).thenReturn(new BigDecimal(115.56));
        Mockito.when(bedrag.getTaxPercentagePaid()).thenReturn(new BigDecimal(21));
        Mockito.when(bedrag.getWeightAccountancy()).thenReturn(new BigDecimal(100));

        BedragWeightPojo bedragWeightCalculator = new BedragWeightPojo(bedrag);

        Assert.assertEquals(new BigDecimal(95.5041).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragExclusiveTax());
        Assert.assertEquals(new BigDecimal(20.0559).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragTax());
        Assert.assertEquals(new BigDecimal(95.5041).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightExclusiveTax());
        Assert.assertEquals(new BigDecimal(20.0559).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverExclusiveTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverTax());
    }

    @Test
    public void testBedragWeightCalculatorWith0(){
        Bedragen bedrag = Mockito.mock(Bedragen.class);
        Mockito.when(bedrag.getBedrag()).thenReturn(new BigDecimal(115.56));
        Mockito.when(bedrag.getTaxPercentagePaid()).thenReturn(new BigDecimal(21));
        Mockito.when(bedrag.getWeightAccountancy()).thenReturn(new BigDecimal(0));

        BedragWeightPojo bedragWeightCalculator = new BedragWeightPojo(bedrag);

        Assert.assertEquals(new BigDecimal(95.5041).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragExclusiveTax());
        Assert.assertEquals(new BigDecimal(20.0559).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightExclusiveTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightTax());
        Assert.assertEquals(new BigDecimal(95.5041).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverExclusiveTax());
        Assert.assertEquals(new BigDecimal(20.0559).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverTax());
    }

    @Test
    public void testBedragWeightCalculatorWithNull(){
        Bedragen bedrag = Mockito.mock(Bedragen.class);
        Mockito.when(bedrag.getBedrag()).thenReturn(new BigDecimal(115.56));
        Mockito.when(bedrag.getTaxPercentagePaid()).thenReturn(null);
        Mockito.when(bedrag.getWeightAccountancy()).thenReturn(null);

        BedragWeightPojo bedragWeightCalculator = new BedragWeightPojo(bedrag);

        Assert.assertEquals(new BigDecimal(115.56).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragExclusiveTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightExclusiveTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragWeightTax());
        Assert.assertEquals(new BigDecimal(115.56).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverExclusiveTax());
        Assert.assertEquals(new BigDecimal(0).setScale(4, RoundingMode.HALF_UP), bedragWeightCalculator.getBedragLeftOverTax());
    }

    @Test
    public void testCalculateGovernmentCostsBTW(){
        List<GovernmentCost> governmentCosts = GovernmentCostMock.createGovernmentCostMock();
        AccountingModel accountingModel = AccountingModelMock.createAccountingModelMock();
        Map<String, BigDecimal> governmentCostMap = AmountTaxAndWeightCalculator.calculateGovernmentCosts(accountingModel, governmentCosts);

        Assert.assertEquals(new BigDecimal(3714.8634).setScale(8, RoundingMode.HALF_UP), governmentCostMap.get("BTW").setScale(8, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateGovernmentCostsBelastingen(){
        List<GovernmentCost> governmentCosts = GovernmentCostMock.createGovernmentCostMock();
        AccountingModel accountingModel = AccountingModelMock.createAccountingModelMock();
        Map<String, BigDecimal> governmentCostMap = AmountTaxAndWeightCalculator.calculateGovernmentCosts(accountingModel, governmentCosts);

        Assert.assertEquals(new BigDecimal(4713.74970520).setScale(8, RoundingMode.HALF_UP), governmentCostMap.get("Belastingen").setScale(8, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateGovernmentCostsRSZ(){
        List<GovernmentCost> governmentCosts = GovernmentCostMock.createGovernmentCostMock();
        AccountingModel accountingModel = AccountingModelMock.createAccountingModelMock();
        Map<String, BigDecimal> governmentCostMap = AmountTaxAndWeightCalculator.calculateGovernmentCosts(accountingModel, governmentCosts);

        Assert.assertEquals(new BigDecimal(800.693387).setScale(8, RoundingMode.HALF_UP), governmentCostMap.get("RSZ").setScale(8, RoundingMode.HALF_UP));
    }
}
