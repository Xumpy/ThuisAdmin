package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.controllers.model.InvoiceJobsCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.InvoicesCtrlPojo;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.timesheets.controller.model.JobsCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return new InvoicesCtrlPojo(invoicesDao.findById(invoiceId).get(), invoiceJobsDao.sumAmount(invoiceId));
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

    private List<InvoicesCtrlPojo> convertIterableInvoices(Iterable<InvoicesDaoPojo> iTinvoices){
        List<InvoicesCtrlPojo> lstInvoices = new ArrayList<InvoicesCtrlPojo>();

        for(InvoicesDaoPojo invoiceDaoPojo: iTinvoices){
            lstInvoices.add(new InvoicesCtrlPojo(invoiceDaoPojo, invoiceJobsDao.sumAmount(invoiceDaoPojo.getPkId())));
        }

        return lstInvoices;
    }
}
