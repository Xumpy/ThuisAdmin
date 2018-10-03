package com.xumpy.finances.services;

import com.xumpy.finances.model.AccountingModel;
import com.xumpy.finances.model.AccountingModelTotal;
import com.xumpy.finances.model.BusinessCost;
import com.xumpy.government.dao.GovernmentCostDao;
import com.xumpy.government.domain.GovernmentCost;
import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.domain.Invoices;
import com.xumpy.thuisadmin.domain.Rekeningen;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccountingCalculationsSrv {
    @Autowired private UserInfo userInfo;
    @Autowired RekeningenDaoImpl rekeningenDao;
    @Autowired BedragenDaoImpl bedragenDao;
    @Autowired InvoicesDaoImpl invoicesDao;
    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;
    @Autowired GovernmentCostDao governmentCostDao;

    private List<Bedragen> getAllProfessionalBedragenInPeriode(Date startDate, Date endDate){
        List<Bedragen> bedragen = new ArrayList<>();
        for(Rekeningen rekening: rekeningenDao.findAllOpenRekeningen(userInfo.getPersoon().getPk_id())){
            if (rekening.getProfessional() != null && rekening.getProfessional()){
                bedragen.addAll( bedragenDao.BedragInPeriodeOpRekening(startDate, endDate, rekening.getPk_id()));
            }
        }
        return bedragen;
    }

    private GroepenSrvPojo generateCostGroup(){
        GroepenSrvPojo groepenSrvPojo = new GroepenSrvPojo();

        groepenSrvPojo.setNegatief(1);

        return groepenSrvPojo;
    }

    private Bedragen transformSimulatedBusinessCostToBedrag(BusinessCost simulatedBusinessCost){
        BedragenSrvPojo bedragen = new BedragenSrvPojo();

        bedragen.setBedrag(simulatedBusinessCost.getAmount());
        bedragen.setTaxPercentagePaid(simulatedBusinessCost.getTax());
        bedragen.setWeightAccountancy(simulatedBusinessCost.getWeight());
        bedragen.setGroep(generateCostGroup());

        return bedragen;
    }

    private void subtractPaidGovernmentCosts(AccountingModel accountingModel, LocalDate startDate, LocalDate endDate){
        Map<String, BigDecimal> governmentCosts = accountingModel.getGovernmentCosts();

        for(Map.Entry<String, BigDecimal> governmentCost: governmentCosts.entrySet()){
            List<BedragenDaoPojo> bedragen = bedragenDao.allBedragenInCostType(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate), governmentCost.getKey());
            BigDecimal currentAmount = governmentCost.getValue();
            for (BedragenDaoPojo bedrag: bedragen){
                if (bedrag.getGroep().getNegatief() == 1){
                    currentAmount = currentAmount.subtract(bedrag.getBedrag());
                } else {
                    currentAmount = currentAmount.add(bedrag.getBedrag());
                }
            }
            governmentCosts.put(governmentCost.getKey(), currentAmount);
        }
    }

    public AccountingModel generateAccountingModel(LocalDate startDate, LocalDate endDate, BusinessCost simulatedBusinessCost){
        AccountingModel accountingModel = new AccountingModel();

        List<InvoicesDaoPojo> invoices = invoicesDao.findAllInvoicesBetween(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        for(Invoices invoice: invoices){
            if (invoice.getClosed() != null && invoice.getClosed()){
                accountingModel.getRevenue().add(new BedragWeightPojo(invoice, invoiceJobsDao.sumAmount(invoice.getPkId())));
            }
        }

        List<Bedragen> bedragen = getAllProfessionalBedragenInPeriode(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));

        if (simulatedBusinessCost != null){
            bedragen.add(transformSimulatedBusinessCostToBedrag(simulatedBusinessCost));
        }

        for(Bedragen bedrag: bedragen){
            if (bedrag.getInvoice() == null){
                if (bedrag.getGroep().getNegatief() == 0){
                    accountingModel.getRevenue().add(new BedragWeightPojo(bedrag));
                } else{
                    accountingModel.getCosts().add(new BedragWeightPojo(bedrag));
                }
            }
        }
        accountingModel.setGovernmentCosts(AmountTaxAndWeightCalculator.calculateGovernmentCosts(accountingModel, governmentCostDao.findAll()));

        subtractPaidGovernmentCosts(accountingModel, startDate, endDate);

        return accountingModel;
    }

    public AccountingModel generateAccountingModel(LocalDate startDate, LocalDate endDate){
        return generateAccountingModel(startDate, endDate, null);
    }

    private BedragenDaoPojo getBedragInvoice(Invoices invoice){
        List<BedragenDaoPojo> bedrag = bedragenDao.getBedragOfInvoice(invoice.getPkId());
        if (bedrag.size() > 0){
            return bedrag.get(0);
        }
        return null;
    }

    private BigDecimal totalGovernmentCosts(AccountingModel accountingModel){
        BigDecimal totalGovernmentCosts = new BigDecimal(0);
        for (Map.Entry<String, BigDecimal> governmentCostEntry: accountingModel.getGovernmentCosts().entrySet()){
            totalGovernmentCosts = totalGovernmentCosts.add(governmentCostEntry.getValue());
        }
        return totalGovernmentCosts;
    }

    private BigDecimal totalAmountUnpaidInvoices(LocalDate startDate, LocalDate endDate){
        BigDecimal totalUnpaidInvoices = new BigDecimal(0);
        List<InvoicesDaoPojo> invoices = invoicesDao.findAllInvoicesBetween(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        for(Invoices invoice: invoices) {
            if (invoice.getClosed() != null && invoice.getClosed()) {
                Bedragen bedrag = getBedragInvoice(invoice);
                if (bedrag != null && !bedrag.getProcessed()){
                    totalUnpaidInvoices = totalUnpaidInvoices.add(bedrag.getBedrag());
                }
            }
        }
        return totalUnpaidInvoices;
    }

    private BigDecimal totalAmountNowWithSimulatedBusinessCost(BusinessCost simulatedBusinessCost){
        BigDecimal totalAmountNow = rekeningenDao.totalAllRekeningen(userInfo.getPersoon().getPk_id(), true);
        if (simulatedBusinessCost.getAmount() != null){
            return totalAmountNow.subtract(simulatedBusinessCost.getAmount());
        }
        return totalAmountNow;
    }

    public AccountingModelTotal getAccountingModelTotal(LocalDate startDate, LocalDate endDate, BusinessCost simulatedBusinessCost){
        AccountingModelTotal accountingModelTotal = new AccountingModelTotal();

        accountingModelTotal.setTotalAmountNow(totalAmountNowWithSimulatedBusinessCost(simulatedBusinessCost));
        accountingModelTotal.setTotalGovernmentCosts(totalGovernmentCosts(generateAccountingModel(startDate, endDate, simulatedBusinessCost)));
        accountingModelTotal.setTotalUnpaidInvoices(totalAmountUnpaidInvoices(startDate, endDate));

        return accountingModelTotal;
    }
}
