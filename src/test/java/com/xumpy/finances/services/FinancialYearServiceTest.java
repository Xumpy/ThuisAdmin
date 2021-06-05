package com.xumpy.finances.services;

import com.xumpy.finances.controller.model.FinancialYear;
import com.xumpy.government.dao.FinancialYearGovernmentCostTypesDao;
import com.xumpy.government.dao.FinancialYearsDao;
import com.xumpy.government.dao.model.FinancialYearGovernmentCostTypesDaoPojo;
import com.xumpy.government.dao.model.FinancialYearsDaoPojo;
import com.xumpy.government.dao.model.GovernmentCostTypeDaoPojo;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FinancialYearServiceTest {
    @Mock FinancialYearsDao financialYearsDao;
    @Mock FinancialYearGovernmentCostTypesDao financialYearGovernmentCostTypesDao;
    @Mock BedragenDaoImpl bedragenDao;
    @Mock InvoicesDaoImpl invoicesDao;
    @Mock InvoiceJobsDaoImpl invoiceJobsDao;

    @InjectMocks FinancialYearService financialYearService;

    private static final Integer TEST_YEAR = 2020;

    private GroepenDaoPojo groep20ProcentBelastingen = createMockedGroepenDaoPojo(1, "Belastingen 20%", true);
    private GroepenDaoPojo groep15ProcentDividend = createMockedGroepenDaoPojo(2, "Dividend 15%", true);

    private FinancialYearsDaoPojo createdMockedFinancialYearsDaoPojo(BigDecimal costs, BigDecimal revenue){
        FinancialYearsDaoPojo financialYearsDaoPojo = new FinancialYearsDaoPojo();

        financialYearsDaoPojo.setYear(TEST_YEAR);
        financialYearsDaoPojo.setCosts(costs);
        financialYearsDaoPojo.setRevenue(revenue);

        return financialYearsDaoPojo;
    }

    private GovernmentCostTypeDaoPojo createMockedGovernmentCostTypeDaoPojo(String type, Integer level, GroepenDaoPojo groepenDaoPojo){
        GovernmentCostTypeDaoPojo governmentCostTypeDaoPojo = new GovernmentCostTypeDaoPojo();

        governmentCostTypeDaoPojo.setType(type);
        governmentCostTypeDaoPojo.setLevel(level);
        governmentCostTypeDaoPojo.setGroep(groepenDaoPojo);

        return governmentCostTypeDaoPojo;
    }

    private FinancialYearGovernmentCostTypesDaoPojo createMockedfinancialYearGovernmentCostTypesDaoPojo(FinancialYearsDaoPojo financialYearsDaoPojo, Integer level, GroepenDaoPojo groepenDaoPojo, BigDecimal actualCost){
        FinancialYearGovernmentCostTypesDaoPojo financialYearGovernmentCostTypesDaoPojo = new FinancialYearGovernmentCostTypesDaoPojo();

        financialYearGovernmentCostTypesDaoPojo.setFinancialYear(financialYearsDaoPojo);
        financialYearGovernmentCostTypesDaoPojo.setGovernmentCostType(createMockedGovernmentCostTypeDaoPojo(groepenDaoPojo.getNaam(), level, groepenDaoPojo));
        financialYearGovernmentCostTypesDaoPojo.setActualCost(actualCost);

        return financialYearGovernmentCostTypesDaoPojo;
    }

    private GroepenDaoPojo createMockedGroepenDaoPojo(Integer pkId, String naam, Boolean negatief){
        GroepenDaoPojo groepenDaoPojo = new GroepenDaoPojo();
        groepenDaoPojo.setPk_id(pkId);
        groepenDaoPojo.setNaam(naam);
        groepenDaoPojo.setNegatief(negatief ? 1 : 0);
        return  groepenDaoPojo;
    }

    private BedragenDaoPojo createMockedBedragenDaoPojo(GroepenDaoPojo groepenDaoPojo, BigDecimal bedrag){
        BedragenDaoPojo bedragenDaoPojo = new BedragenDaoPojo();

        bedragenDaoPojo.setGroep(groepenDaoPojo);
        bedragenDaoPojo.setBedrag(bedrag);

        return bedragenDaoPojo;
    }

    private List<BedragenDaoPojo> mockedBedragenDaoPojos(GroepenDaoPojo groep20ProcentBelastingen, GroepenDaoPojo groep15ProcentDividend){
        List<BedragenDaoPojo> bedragenDaoPojos = new ArrayList<>();
        bedragenDaoPojos.add(createMockedBedragenDaoPojo(createMockedGroepenDaoPojo(3, "Factuurke", false), new BigDecimal(75000)));
        bedragenDaoPojos.add(createMockedBedragenDaoPojo(createMockedGroepenDaoPojo(4, "Shizzle", true), new BigDecimal(40000)));
        bedragenDaoPojos.add(createMockedBedragenDaoPojo(createMockedGroepenDaoPojo(5, "Nog wa shizzle", true), new BigDecimal(45000)));
        bedragenDaoPojos.add(createMockedBedragenDaoPojo(groep20ProcentBelastingen, new BigDecimal(10000)));
        bedragenDaoPojos.add(createMockedBedragenDaoPojo(groep15ProcentDividend, new BigDecimal(5000)));

        return bedragenDaoPojos;
    }

    private List<InvoicesDaoPojo> createdMockedInvoicesDaoPojos(){
        List<InvoicesDaoPojo> invoices = new ArrayList<>();
        InvoicesDaoPojo invoice1 = new InvoicesDaoPojo();
        InvoicesDaoPojo invoice2 = new InvoicesDaoPojo();
        invoices.add(invoice1);
        invoices.add(invoice2);

        invoice1.setPkId(1);
        invoice2.setPkId(2);

        return invoices;
    }

    private void mockFinancialYearComplete(){
        FinancialYearsDaoPojo mockedFinancialYearsDaoPojo = createdMockedFinancialYearsDaoPojo(new BigDecimal(90000), new BigDecimal(137280));
        List<FinancialYearGovernmentCostTypesDaoPojo> financialYearGovernmentCostTypesDaoPojos = new ArrayList<>();

        financialYearGovernmentCostTypesDaoPojos.add(createMockedfinancialYearGovernmentCostTypesDaoPojo(mockedFinancialYearsDaoPojo,1, groep20ProcentBelastingen, new BigDecimal(9456)));
        financialYearGovernmentCostTypesDaoPojos.add(createMockedfinancialYearGovernmentCostTypesDaoPojo(mockedFinancialYearsDaoPojo,2, groep15ProcentDividend, new BigDecimal(5673.6)));

        Mockito.when(financialYearsDao.findFinancialYearByYear(TEST_YEAR)).thenReturn(mockedFinancialYearsDaoPojo);
        Mockito.when(financialYearGovernmentCostTypesDao.findFinancialYearGovernmentCostTypesByFinancialYear(mockedFinancialYearsDaoPojo.getPkId()))
                .thenReturn(financialYearGovernmentCostTypesDaoPojos);
    }

    private void mockFinancialYearWithGovernmentCostsButNoData(){
        FinancialYearsDaoPojo mockedFinancialYearsDaoPojo = createdMockedFinancialYearsDaoPojo(null, null);
        List<FinancialYearGovernmentCostTypesDaoPojo> financialYearGovernmentCostTypesDaoPojos = new ArrayList<>();

        financialYearGovernmentCostTypesDaoPojos.add(createMockedfinancialYearGovernmentCostTypesDaoPojo(mockedFinancialYearsDaoPojo,1, groep20ProcentBelastingen, null));
        financialYearGovernmentCostTypesDaoPojos.add(createMockedfinancialYearGovernmentCostTypesDaoPojo(mockedFinancialYearsDaoPojo,2, groep15ProcentDividend, null));

        Mockito.when(financialYearsDao.findFinancialYearByYear(TEST_YEAR)).thenReturn(mockedFinancialYearsDaoPojo);
        Mockito.when(financialYearGovernmentCostTypesDao.findFinancialYearGovernmentCostTypesByFinancialYear(mockedFinancialYearsDaoPojo.getPkId()))
                .thenReturn(financialYearGovernmentCostTypesDaoPojos);
    }

    @Before
    public void mockData(){
        Mockito.when(invoicesDao.findAllInvoicesBetween(Mockito.any(), Mockito.any())).thenReturn(createdMockedInvoicesDaoPojos());
        Mockito.when(invoiceJobsDao.sumAmount(1)).thenReturn(new BigDecimal(75000));
        Mockito.when(invoiceJobsDao.sumAmount(2)).thenReturn(new BigDecimal(65000));

        Mockito.when(bedragenDao.allProfesionalBedragenInDate(Mockito.any(), Mockito.any())).thenReturn(mockedBedragenDaoPojos(groep20ProcentBelastingen, groep15ProcentDividend));
    }

    @Test
    public void testFinancialYearComplete(){
        mockFinancialYearComplete();
        FinancialYear financialYear = financialYearService.financialYear(TEST_YEAR);

        Assert.assertEquals(new BigDecimal(140000).setScale(2, RoundingMode.HALF_UP), financialYear.getSelectedRevenue());
        Assert.assertEquals(new BigDecimal(137280).setScale(2, RoundingMode.HALF_UP), financialYear.getActualRevenue());
        Assert.assertEquals(new BigDecimal(85000).setScale(2, RoundingMode.HALF_UP), financialYear.getSelectedCosts());
        Assert.assertEquals(new BigDecimal(90000).setScale(2, RoundingMode.HALF_UP), financialYear.getActualCosts());

        Assert.assertEquals(new BigDecimal(10000).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(0).getSelectedCosts());
        Assert.assertEquals(new BigDecimal(9456).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(0).getActualCosts());

        Assert.assertEquals(new BigDecimal(5000).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(1).getSelectedCosts());
        Assert.assertEquals(new BigDecimal(5673.60).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(1).getActualCosts());

        Assert.assertEquals(new BigDecimal(32150.4).setScale(2, RoundingMode.HALF_UP), financialYear.getTotal());
    }

    @Test
    public void testFinancialYearNotFound(){
        FinancialYear financialYear = financialYearService.financialYear(TEST_YEAR);

        Assert.assertEquals(new BigDecimal(140000).setScale(2, RoundingMode.HALF_UP), financialYear.getSelectedRevenue());
        Assert.assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), financialYear.getActualRevenue());
        Assert.assertEquals(new BigDecimal(100000).setScale(2, RoundingMode.HALF_UP), financialYear.getSelectedCosts());
        Assert.assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), financialYear.getActualCosts());

        Assert.assertEquals(new BigDecimal(40000).setScale(2, RoundingMode.HALF_UP), financialYear.getTotal());
    }

    @Test
    public void testFinancialYearWithGovernmentCostsButNoData(){
        mockFinancialYearWithGovernmentCostsButNoData();
        FinancialYear financialYear = financialYearService.financialYear(TEST_YEAR);

        Assert.assertEquals(new BigDecimal(140000).setScale(2, RoundingMode.HALF_UP), financialYear.getSelectedRevenue());
        Assert.assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), financialYear.getActualRevenue());
        Assert.assertEquals(new BigDecimal(85000).setScale(2, RoundingMode.HALF_UP), financialYear.getSelectedCosts());
        Assert.assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), financialYear.getActualCosts());

        Assert.assertEquals(new BigDecimal(10000).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(0).getSelectedCosts());
        Assert.assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(0).getActualCosts());

        Assert.assertEquals(new BigDecimal(5000).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(1).getSelectedCosts());
        Assert.assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), financialYear.getFinancialYearCostTypes().get(1).getActualCosts());

        Assert.assertEquals(new BigDecimal(40000).setScale(2, RoundingMode.HALF_UP), financialYear.getTotal());
    }
}