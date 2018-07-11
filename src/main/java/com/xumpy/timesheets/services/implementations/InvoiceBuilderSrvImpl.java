package com.xumpy.timesheets.services.implementations;

import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.timesheets.controller.model.InvoiceBuilderCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupPricesDaoImpl;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceBuilderSrvImpl {
    @Autowired JobsSrvImpl jobsSrv;
    @Autowired JobsGroupDaoImpl jobsGroupDao;
    @Autowired InvoicesDaoImpl invoicesDao;
    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;
    @Autowired JobsGroupPricesDaoImpl jobsGroupPricesDao;

    private Date datePlusOnMoth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);

        return cal.getTime();
    }

    private Boolean doesJobExistsInJobs(Jobs job, List<InvoiceJobsDaoPojo> jobs){
        if (jobs != null){
            for(InvoiceJobsDaoPojo currentJob: jobs){
                if (job.getJobDate().compareTo(currentJob.getJob().getJobDate()) == 0 &&
                        job.getJobsGroup().getName().equals(currentJob.getJob().getJobsGroup().getName())){
                    return true;
                }
            }
        }

        return false;
    }

    private void saveInvoiceJobs(List<? extends Jobs> jobs, InvoicesDaoPojo invoicesDaoPojo){
        List<InvoiceJobsDaoPojo> invoiceJobsDaoPojos = new ArrayList<InvoiceJobsDaoPojo>();
        List<InvoiceJobsDaoPojo> currentJobsInInvoice = null;

        if (invoicesDaoPojo.getPkId() != null) currentJobsInInvoice = invoiceJobsDao.findAllJobsByInvoice(invoicesDaoPojo.getPkId());

        for (Jobs job: jobs){
            InvoiceJobsDaoPojo invoiceJobsDaoPojo = new InvoiceJobsDaoPojo();
            invoiceJobsDaoPojo.setAmount(jobsGroupPricesDao.selectPriceBetweenDate(job.getJobsGroup().getPk_id(), job.getJobDate()));
            invoiceJobsDaoPojo.setInvoice(invoicesDaoPojo);
            invoiceJobsDaoPojo.setJob(new JobsDaoPojo(job));
            invoiceJobsDaoPojo.setDescription(job.getJobsGroup().getName() + ": " + job.getJobsGroup().getDescription());
            invoiceJobsDaoPojo.setTimeUnitDays(job.getJobsGroup().getCompany().isTimeUnitDays());

            if (!doesJobExistsInJobs(job, currentJobsInInvoice)){
                invoiceJobsDaoPojos.add(invoiceJobsDaoPojo);
            }
        }

        invoiceJobsDao.saveAll(invoiceJobsDaoPojos);
    }

    private InvoicesDaoPojo buildNewInvoice(String invoiceId, String vatNumber){
        InvoicesDaoPojo invoicesDaoPojo = new InvoicesDaoPojo();
        invoicesDaoPojo.setInvoiceDate(new Date());
        invoicesDaoPojo.setInvoiceDueDate(datePlusOnMoth());
        invoicesDaoPojo.setInvoiceId(invoiceId);
        invoicesDaoPojo.setVatNumber(vatNumber);
        invoicesDaoPojo.setVatAmount(new BigDecimal(21));

        return invoicesDaoPojo;
    }

    @Transactional
    public void build(InvoiceBuilderCtrlPojo invoiceBuilder) throws ParseException {
        JobsGroup jobsGroup = jobsGroupDao.findById(invoiceBuilder.getGroupId()).get();
        InvoicesDaoPojo invoicesDaoPojo;

        List<InvoicesDaoPojo> invoices = invoicesDao.findAllInvoicesByInvoice(invoiceBuilder.getInvoiceId());
        if (invoices.size() == 1){
            invoicesDaoPojo = invoices.get(0);
        } else {
            invoicesDaoPojo = buildNewInvoice(invoiceBuilder.getInvoiceId(), jobsGroup.getCompany().getVatNumber());
        }


        saveInvoiceJobs(jobsSrv.selectMonth(invoiceBuilder.getMonth(), invoiceBuilder.getGroupId()), invoicesDao.save(invoicesDaoPojo));
    }
}
