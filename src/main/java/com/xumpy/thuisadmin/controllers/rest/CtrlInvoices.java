package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.controllers.model.InvoiceJobsCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.InvoicesCtrlPojo;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.thuisadmin.domain.Invoices;
import com.xumpy.timesheets.controller.model.JobsCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CtrlInvoices {
    @Autowired InvoicesDaoImpl invoicesDao;
    @Autowired InvoiceJobsDaoImpl invoiceJobsDao;
    @Autowired JobsDaoImpl jobsDao;

    @RequestMapping("/json/invoices")
    public @ResponseBody List<InvoicesCtrlPojo> getAllInvoices(){
        return convertIterableInvoices(invoicesDao.findAll());
    }

    @RequestMapping("/json/invoice/{invoiceId}")
    public @ResponseBody InvoicesCtrlPojo getInvoice(@PathVariable Integer invoiceId){
        InvoicesCtrlPojo invoice = new InvoicesCtrlPojo(invoicesDao.findById(invoiceId).get());
        List<InvoiceJobsDaoPojo> invoiceJobs = invoiceJobsDao.findAllJobsByInvoice(invoice.getPkId());
        invoice.setTotalAmount(getTotalAmount(invoiceJobs));
        invoice.setTotalWorkedDays(getAmountWorkedDays(invoiceJobs));

        return invoice;
    }

    @RequestMapping("/json/invoice/saveInvoice")
    public @ResponseBody String saveInvoice(@RequestBody InvoicesCtrlPojo invoice){
        invoicesDao.save(new InvoicesDaoPojo(invoice));

        return "200";
    }

    @RequestMapping("/json/invoice/deleteInvoice")
    public @ResponseBody String deleteInvoice(@RequestBody InvoicesCtrlPojo invoice){
        invoiceJobsDao.deleteAll(invoiceJobsDao.findAllJobsByInvoice(invoice.getPkId()));
        invoicesDao.delete(new InvoicesDaoPojo(invoice));

        return "200";
    }

    @RequestMapping("/json/invoice/fetch_invoice_jobs/{invoiceId}")
    public @ResponseBody List<InvoiceJobsCtrlPojo> fetchInvoiceJobs(@PathVariable Integer invoiceId){
        return convertDaoInvoiceJobs(invoiceJobsDao.findAllJobsByInvoice(invoiceId));
    }

    @RequestMapping("/json/fetch_all_jobs_not_invoiced/{currentJobId}")
    public @ResponseBody List<JobsCtrlPojo> fetchAllJobsNotInvoiced(@PathVariable Integer currentJobId){
        return convertDaoJobs(jobsDao.selectAllJobsNotInInvoice(currentJobId));
    }

    @RequestMapping("/json/save_job_invoice")
    public @ResponseBody String saveJobInvoice(@RequestBody InvoiceJobsCtrlPojo invoiceJobsCtrlPojo){
        invoiceJobsDao.save(new InvoiceJobsDaoPojo(invoiceJobsCtrlPojo));

        return "200";
    }

    @RequestMapping("/json/select_job_invoice/{jobInvoiceId}")
    public @ResponseBody InvoiceJobsCtrlPojo selectJobInvoice(@PathVariable Integer jobInvoiceId){
        return new InvoiceJobsCtrlPojo(invoiceJobsDao.findById(jobInvoiceId).get());
    }

    @RequestMapping("/json/delete_job_invoice")
    public @ResponseBody String deleteJobInvoice(@RequestBody InvoiceJobsCtrlPojo invoiceJobsCtrlPojo){
        invoiceJobsDao.delete(new InvoiceJobsDaoPojo(invoiceJobsCtrlPojo));

        return "200";
    }

    private List<JobsCtrlPojo> convertDaoJobs(List<JobsDaoPojo> jobsDaoPojos){
        List<JobsCtrlPojo> jobsCtrlPojos = new ArrayList<JobsCtrlPojo>();

        for (JobsDaoPojo jobsDaoPojo: jobsDaoPojos){
            jobsCtrlPojos.add(new JobsCtrlPojo(jobsDaoPojo));
        }

        return jobsCtrlPojos;
    }

    private List<InvoiceJobsCtrlPojo> convertDaoInvoiceJobs(List<InvoiceJobsDaoPojo> invoiceJobsDaoPojos){
        List<InvoiceJobsCtrlPojo> invoiceJobsCtrlPojos = new ArrayList<InvoiceJobsCtrlPojo>();
        for(InvoiceJobsDaoPojo invoiceJobsDaoPojo : invoiceJobsDaoPojos){
            invoiceJobsCtrlPojos.add(new InvoiceJobsCtrlPojo(invoiceJobsDaoPojo));
        }
        return invoiceJobsCtrlPojos;
    }

    private BigDecimal getAmountWorkedDays(List<InvoiceJobsDaoPojo> invoiceJobs){
        BigDecimal totalWorkedDays = new BigDecimal(0);

        for (InvoiceJobsDaoPojo invoiceJob: invoiceJobs) {
            totalWorkedDays = totalWorkedDays.add(invoiceJob.getJob().getWorkedHours().divide(invoiceJob.getJob().getJobsGroup().getCompany().getDailyPayedHours()));
        }

        return totalWorkedDays;
    }

    private BigDecimal getTotalAmount(List<InvoiceJobsDaoPojo> invoiceJobs){
        BigDecimal totalAmount= new BigDecimal(0);

        for (InvoiceJobsDaoPojo invoiceJob: invoiceJobs){
            if (invoiceJob.getAmount() != null) {
                totalAmount = totalAmount.add(invoiceJob.getAmount().multiply(invoiceJob.getJob().getWorkedHours()));
            }
        }
        return totalAmount;
    }

    private List<InvoicesCtrlPojo> convertIterableInvoices(Iterable<InvoicesDaoPojo> iTinvoices){
        List<InvoicesCtrlPojo> lstInvoices = new ArrayList<InvoicesCtrlPojo>();

        for(InvoicesDaoPojo invoiceDaoPojo: iTinvoices){
            InvoicesCtrlPojo invoice = new InvoicesCtrlPojo(invoiceDaoPojo);
            List<InvoiceJobsDaoPojo> invoiceJobs = invoiceJobsDao.findAllJobsByInvoice(invoice.getPkId());

            invoice.setTotalWorkedDays(getAmountWorkedDays(invoiceJobs));
            invoice.setTotalAmount(getTotalAmount(invoiceJobs));

            lstInvoices.add(invoice);
        }

        return lstInvoices;
    }
}
