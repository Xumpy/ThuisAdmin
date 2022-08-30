package com.xumpy.finances.services;

import com.xumpy.finances.controller.model.FinancialYear;
import com.xumpy.finances.controller.model.FinancialYearCostType;
import com.xumpy.government.controllers.model.GovernmentCostTypeCtrlPojo;
import com.xumpy.government.dao.FinancialYearGovernmentCostTypesDao;
import com.xumpy.government.dao.FinancialYearsDao;
import com.xumpy.government.dao.model.FinancialYearGovernmentCostTypesDaoPojo;
import com.xumpy.government.dao.model.FinancialYearsDaoPojo;
import com.xumpy.government.dao.model.GovernmentCostTypeDaoPojo;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.thuisadmin.dao.model.MonthlyValue;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class FinancialYearService {
    @Autowired FinancialYearsDao financialYearsDao;
    @Autowired FinancialYearGovernmentCostTypesDao financialYearGovernmentCostTypesDao;
    @Autowired BedragenDaoImpl bedragenDao;
    @Autowired InvoicesDaoImpl invoicesDao;
    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;
    @Autowired BedragAccountingDaoImpl bedragAccountingDao;

    private static final Integer SCALE = 2;

    private Date firstDayOfYear(Integer year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    private Date lastDayOfYear(Integer year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        return cal.getTime();
    }

    private BigDecimal setBigDecimal(BigDecimal value){
        return value == null ? setBigDecimal(new BigDecimal(0)) : value.setScale(SCALE, RoundingMode.HALF_UP);
    }

    private Boolean isBedragInGovernmentCostType(BedragenDaoPojo bedragenDaoPojo, List<FinancialYearGovernmentCostTypesDaoPojo> financialYearGovernmentCostTypesDaoPojos){
        for(FinancialYearGovernmentCostTypesDaoPojo financialYearGovernmentCostTypesDaoPojo: financialYearGovernmentCostTypesDaoPojos){
            if (financialYearGovernmentCostTypesDaoPojo.getGovernmentCostType().getGroep().getPk_id().equals(bedragenDaoPojo.getGroep().getPk_id())){
                return true;
            }
        }
        return false;
    }

    private BigDecimal getTotalInvoiceBetween(Date startDate, Date endDate){
        BigDecimal totalInvoiced = setBigDecimal(new BigDecimal(0));
        List<InvoicesDaoPojo> invoices = invoicesDao.findAllInvoicesBetween(startDate, endDate);
        for(InvoicesDaoPojo invoice: invoices){
            totalInvoiced = totalInvoiced.add(invoiceJobsDao.sumAmount(invoice.getPkId()));
        }
        return totalInvoiced;
    }

    private BigDecimal getSelectedCostsWithoutGovnment(List<BedragenDaoPojo> bedragenDaoPojos,
                                                       List<FinancialYearGovernmentCostTypesDaoPojo> financialYearGovernmentCostTypesDaoPojos){
        BigDecimal selectedBedrag = setBigDecimal(new BigDecimal(0));

        for (BedragenDaoPojo bedragenDaoPojo: bedragenDaoPojos){
            if (bedragenDaoPojo.getGroep().getNegatief() == 1 &&
                    !isBedragInGovernmentCostType(bedragenDaoPojo, financialYearGovernmentCostTypesDaoPojos)) {
                selectedBedrag = selectedBedrag.add(bedragenDaoPojo.getBedrag());
            }
        }

        return selectedBedrag;
    }

    private BigDecimal getSelectedCostFromGroup(List<BedragenDaoPojo> bedragenDaoPojos, GroepenDaoPojo groepenDaoPojo){
        BigDecimal selectedCosts = setBigDecimal(new BigDecimal(0));

        for (BedragenDaoPojo bedragenDaoPojo: bedragenDaoPojos){
            if (bedragenDaoPojo.getGroep().getPk_id().equals(groepenDaoPojo.getPk_id())){
                selectedCosts = selectedCosts.add(bedragenDaoPojo.getBedrag());
            }
        }

        return selectedCosts;
    }

    private BigDecimal determineValue(BigDecimal actualValue, BigDecimal selectedValue) {
        return actualValue.equals(setBigDecimal(new BigDecimal(0))) ? selectedValue : actualValue;
    }

    private BigDecimal determineTotal(FinancialYear financialYear){
        BigDecimal total = determineValue(BedragenSrvImpl.convertComma(financialYear.getActualRevenue()), financialYear.getSelectedRevenue()).subtract(
                determineValue(BedragenSrvImpl.convertComma(financialYear.getActualCosts()), financialYear.getSelectedCosts()));

        for(FinancialYearCostType financialYearCostType: financialYear.getFinancialYearCostTypes()){
            total = total.subtract(determineValue(BedragenSrvImpl.convertComma(financialYearCostType.getActualCosts()), financialYearCostType.getSelectedCosts()));
        }

        return total;
    }

    public FinancialYear financialYear(Integer year){
        List<BedragenDaoPojo> bedragenDaoPojos = bedragenDao.allProfesionalBedragenInDate(firstDayOfYear(year), lastDayOfYear(year));

        FinancialYearsDaoPojo financialYearsDaoPojo = financialYearsDao.findFinancialYearByYear(year);
        List<FinancialYearGovernmentCostTypesDaoPojo> financialYearGovernmentCostTypes = new ArrayList<>();

        FinancialYear financialYear = new FinancialYear();
        financialYear.setYear(year);
        if (financialYearsDaoPojo != null){
            financialYearGovernmentCostTypes = financialYearGovernmentCostTypesDao.findFinancialYearGovernmentCostTypesByFinancialYear(financialYearsDaoPojo.getPkId());
            financialYear.setActualCosts(setBigDecimal(financialYearsDaoPojo.getCosts()).toString());
            financialYear.setActualRevenue(setBigDecimal(financialYearsDaoPojo.getRevenue()).toString());
        } else {
            financialYear.setActualCosts(setBigDecimal(new BigDecimal(0)).toString());
            financialYear.setActualRevenue(setBigDecimal(new BigDecimal(0)).toString());
        }
        financialYear.setSelectedCosts(getSelectedCostsWithoutGovnment(bedragenDaoPojos, financialYearGovernmentCostTypes));
        financialYear.setSelectedRevenue(getTotalInvoiceBetween(firstDayOfYear(year), lastDayOfYear(year)));

        List<FinancialYearCostType> financialYearCostTypes = new ArrayList<>();
        financialYear.setFinancialYearCostTypes(financialYearCostTypes);

        for (FinancialYearGovernmentCostTypesDaoPojo financialYearGovernmentCostType: financialYearGovernmentCostTypes){
            FinancialYearCostType financialYearCostType = new FinancialYearCostType();

            financialYearCostType.setCostType(new GovernmentCostTypeCtrlPojo(financialYearGovernmentCostType.getGovernmentCostType()));
            financialYearCostType.setActualCosts(setBigDecimal(financialYearGovernmentCostType.getActualCost()).toString());
            financialYearCostType.setSelectedCosts(getSelectedCostFromGroup(bedragenDaoPojos, financialYearGovernmentCostType.getGovernmentCostType().getGroep()));
            financialYearCostTypes.add(financialYearCostType);
        }

        financialYear.setTotal(determineTotal(financialYear));

        return financialYear;
    }

    public void saveFinancialYear(FinancialYear financialYear){
        FinancialYearsDaoPojo financialYearsDaoPojo = financialYearsDao.findFinancialYearByYear(financialYear.getYear());
        if (financialYearsDaoPojo == null){
            financialYearsDaoPojo = new FinancialYearsDaoPojo();
            financialYearsDaoPojo.setYear(financialYear.getYear());
        } else {
            financialYearsDaoPojo.setRevenue(BedragenSrvImpl.convertComma(financialYear.getActualRevenue()));
            financialYearsDaoPojo.setCosts(BedragenSrvImpl.convertComma(financialYear.getActualCosts()));
        }
        financialYearsDao.save(financialYearsDaoPojo);

        List<FinancialYearGovernmentCostTypesDaoPojo> financialYearGovernmentCostTypesDaoPojos = financialYearGovernmentCostTypesDao.findFinancialYearGovernmentCostTypesByFinancialYear(financialYearsDaoPojo.getPkId());
        for(FinancialYearGovernmentCostTypesDaoPojo financialYearGovernmentCostTypesDaoPojo: financialYearGovernmentCostTypesDaoPojos){
            financialYearGovernmentCostTypesDao.delete(financialYearGovernmentCostTypesDaoPojo);
        }

        financialYearGovernmentCostTypesDaoPojos = new ArrayList<>();
        for(FinancialYearCostType financialYearCostType: financialYear.getFinancialYearCostTypes()){
            FinancialYearGovernmentCostTypesDaoPojo financialYearGovernmentCostTypesDaoPojo = new FinancialYearGovernmentCostTypesDaoPojo();

            financialYearGovernmentCostTypesDaoPojo.setFinancialYear(financialYearsDaoPojo);
            financialYearGovernmentCostTypesDaoPojo.setGovernmentCostType(new GovernmentCostTypeDaoPojo(financialYearCostType.getCostType()));
            financialYearGovernmentCostTypesDaoPojo.setActualCost(BedragenSrvImpl.convertComma(financialYearCostType.getActualCosts()));

            financialYearGovernmentCostTypesDao.save(financialYearGovernmentCostTypesDaoPojo);
        }
    }

    private BigDecimal totalByMonthy(List<MonthlyValue> monthlyValues, Integer codeId){
        BigDecimal total = new BigDecimal(0);
        for(MonthlyValue monthlyValue: monthlyValues){
            if (monthlyValue.getCodeId().equals(codeId)){
                total = total.add(monthlyValue.getBedrag());
            }
        }
        return total;
    }

    private BigDecimal totalByMainGroup(List<MonthlyValue> monthlyValues, String mainGroup){
        BigDecimal total = new BigDecimal(0);
        for(MonthlyValue monthlyValue: monthlyValues){
            if (monthlyValue.getMainGroup().equals(mainGroup)){
                total = total.add(monthlyValue.getBedrag());
            }
        }
        return total;
    }

    private List<String> getMainGroups(List<MonthlyValue> monthlyValues){
        List<String> mainGroups = new ArrayList<>();

        for(MonthlyValue monthlyValue: monthlyValues){
            if (!mainGroups.contains(monthlyValue.getMainGroup())){
                mainGroups.add(monthlyValue.getMainGroup());
            }
        }

        return mainGroups;
    }

    private BigDecimal getMonthlyCosts(List<MonthlyValue> monthlyValues, Integer month, Integer codeId){
        BigDecimal value = new BigDecimal(0);

        for (MonthlyValue monthlyValue: monthlyValues){
            if (monthlyValue.getMonth().equals(month)){
                if (codeId == null || monthlyValue.getCodeId().equals(codeId)){
                    value = value.add(monthlyValue.getBedrag());
                }
            }
        }
        return value;
    }

    private Map<Integer, BigDecimal> getMonthlyValues(List<MonthlyValue> monthlyValues, Integer codeId){
        Map<Integer, BigDecimal> monthlyCodeIds = new HashMap<>();
        for(int month=1; month<=12; month++){
            monthlyCodeIds.put(month, getMonthlyCosts(monthlyValues, month, codeId));
        }
        return monthlyCodeIds;
    }

    private List<MonthlyValue> extractDetails(List<MonthlyValue> monthlyValues, String mainGroup){
        List<MonthlyValue> detail = new ArrayList<>();
        for(MonthlyValue monthlyValue: monthlyValues){
            if (monthlyValue.getMainGroup().equals(mainGroup)){
                Map<String, BigDecimal> value = new HashMap<>();
                value.put(monthlyValue.getDescription(), monthlyValue.getBedrag());
                detail.add(monthlyValue);
            }
        }
        return detail;
    }

    private Map<Integer, BigDecimal> getMonthlyValues(List<MonthlyValue> monthlyValues){
        return getMonthlyValues(monthlyValues, null);
    }

    private Map calculateOmzet(List<MonthlyValue> monthlyValues){
        Map result = new HashMap();

        List<Map<String, BigDecimal>> values = new ArrayList<>();
        for(String mainGroup: getMainGroups(monthlyValues)){
            Map keyValue = new HashMap();
            keyValue.put(mainGroup, totalByMainGroup(monthlyValues, mainGroup));
            keyValue.put("detail", extractDetails(monthlyValues, mainGroup));
            values.add(keyValue);
        }
        result.put("values", values);
        result.put("report", getMonthlyValues(monthlyValues, 700000));
        result.put("sum", getSumValues(values));

        return result;
    }

    private BigDecimal getSumValues(List<Map<String, BigDecimal>> values){
        BigDecimal sum = new BigDecimal(0);
        for(Map<String, BigDecimal> value: values){
            for(Map.Entry<String, BigDecimal> val: value.entrySet()){
                if (!val.getKey().equals("detail")){
                    sum = sum.add(val.getValue());
                }
            }
        }
        return sum;
    }

    private Map calculateBedrijfslasten(List<MonthlyValue> monthlyValues){
        Map result = new HashMap();

        List<Map<String, BigDecimal>> values = new ArrayList<>();
        for(String mainGroup: getMainGroups(monthlyValues)){
            Map keyValue = new HashMap();
            keyValue.put(mainGroup, totalByMainGroup(monthlyValues, mainGroup));
            keyValue.put("detail", extractDetails(monthlyValues, mainGroup));
            values.add(keyValue);
        }
        result.put("values", values);
        result.put("sum", getSumValues(values));

        return result;
    }

    private BigDecimal sumOfAllMonths(List<Map<Integer, BigDecimal>> monthlyValues){
        BigDecimal sumOfAllMonths = new BigDecimal(0);

        for(Map<Integer, BigDecimal> monthlyValue: monthlyValues){
            for(Map.Entry<Integer, BigDecimal> value: monthlyValue.entrySet()){
                sumOfAllMonths = sumOfAllMonths.add(value.getValue());
            }
        }

        return sumOfAllMonths;
    }

    private List<Map<Integer, BigDecimal>> getMonthlyResultList(List<MonthlyValue> monthlyValuesRevenue, List<MonthlyValue> monthlyValuesCosts){

        List monthlyResultList = new ArrayList();
        Map<Integer, BigDecimal> monthlyRevenue = getMonthlyValues(monthlyValuesRevenue);
        Map<Integer, BigDecimal> monthlyCosts = getMonthlyValues(monthlyValuesCosts);

        for(int month = 1; month<=12; month++){
            Map<Integer, BigDecimal> result = new HashMap<>();
            BigDecimal monthlyResult = new BigDecimal(0);
            if (monthlyRevenue.containsKey(month)){
                monthlyResult = monthlyResult.add(monthlyRevenue.get(month));
            }
            if (monthlyCosts.containsKey(month)){
                monthlyResult = monthlyResult.subtract(monthlyCosts.get(month));
            }
            result.put(month, monthlyResult);
            monthlyResultList.add(result);
        }

        return monthlyResultList;
    }

    private Map<Integer, BigDecimal> getMonthlyResultMap(List<MonthlyValue> monthlyValuesRevenue, List<MonthlyValue> monthlyValuesCosts){
        Map<Integer, BigDecimal> monthlyResultMap = new HashMap<>();
        Map<Integer, BigDecimal> monthlyRevenue = getMonthlyValues(monthlyValuesRevenue);
        Map<Integer, BigDecimal> monthlyCosts = getMonthlyValues(monthlyValuesCosts);

        for(int month = 1; month<=12; month++){
            Map<Integer, BigDecimal> result = new HashMap<>();
            BigDecimal monthlyResult = new BigDecimal(0);
            if (monthlyRevenue.containsKey(month)){
                monthlyResult = monthlyResult.add(monthlyRevenue.get(month));
            }
            if (monthlyCosts.containsKey(month)){
                monthlyResult = monthlyResult.subtract(monthlyCosts.get(month));
            }
            monthlyResultMap.put(month, monthlyResult);
        }

        return monthlyResultMap;
    }


    private Map<Integer, BigDecimal> calculateResultaten(List<MonthlyValue> monthlyValuesRevenue, List<MonthlyValue> monthlyValuesCosts){
        Map result = new HashMap();

        result.put("Bedrijfsresultaat", sumOfAllMonths(getMonthlyResultList(monthlyValuesRevenue, monthlyValuesCosts)));
        result.put("report", getMonthlyResultMap(monthlyValuesRevenue, monthlyValuesCosts));
        result.put("sum", sumOfAllMonths(getMonthlyResultList(monthlyValuesRevenue, monthlyValuesCosts)));

        return result;

    }

    public Map returnedAccountingValues(Integer year){
        List<MonthlyValue> monthlyValueRevenue = bedragAccountingDao.getMonthlyValues(year, 0);
        List<MonthlyValue> monthlyValueCosts = bedragAccountingDao.getMonthlyValues(year, 1);

        Map accountingValues = new HashMap();

        accountingValues.put("Omzet", calculateOmzet(monthlyValueRevenue));
        accountingValues.put("Bedrijfslasten", calculateBedrijfslasten(monthlyValueCosts));
        accountingValues.put("Resultaten", calculateResultaten(monthlyValueRevenue, monthlyValueCosts));

        return accountingValues;
    }
}
