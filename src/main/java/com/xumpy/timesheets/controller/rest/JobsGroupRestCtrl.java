/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.rest;

import com.xumpy.government.controllers.model.VatCompensationCtrlPojo;
import com.xumpy.government.dao.VatCompensationDao;
import com.xumpy.timesheets.controller.model.*;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.domain.JobVatCompensation;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.JobsGroupPrices;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWork;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.*;
import com.xumpy.timesheets.services.implementations.JobsGraphics;
import com.xumpy.timesheets.services.implementations.JobsGroupPricesSrvImpl;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import com.xumpy.timesheets.services.model.TickedJobsSrvPojo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author nicom
 */
@Controller
@Scope(value="session")
public class JobsGroupRestCtrl {
    @Autowired Overview overview;
    @Autowired JobsGraphics jobsGraphics;
    @Autowired JobsGroupSrv jobsGroupSrv;
    @Autowired JobsGroupPricesSrv jobsGroupPricesSrv;
    @Autowired JobsSrv jobsSrv;
    @Autowired CompanySrv companySrv;
    @Autowired TickedJobsSrv tickedJobsSrv;
    @Autowired JobVatCompensationDaoImpl jobVatCompensationDao;

    @RequestMapping("/json/set_month")
    public @ResponseBody String setMonth(@RequestBody String month){
        overview.setMonth(month);
        return "201";
    }

    @RequestMapping("/json/fetch_all_jobs_group")
    public @ResponseBody List<JobsGroupCtrl> fetchAllJobsGroup(){
        return JobsGroupCtrl.allJobsGroupCtrl(jobsGroupSrv.selectAllJobGroups());
    }

    @RequestMapping("/json/fetch_all_open_jobs_group")
    public @ResponseBody List<JobsGroupCtrl> fetchAllOpenJobsGroup(){
        return JobsGroupCtrl.allJobsGroupCtrl(jobsGroupSrv.selectAllOpenJobGroups());
    }

    @RequestMapping("/json/fetch_group_price/{jobGroupPriceId}")
    public @ResponseBody JobsGroupPrices fetchJobGroupPrice(@PathVariable Integer jobGroupPriceId){
        return jobsGroupPricesSrv.select(jobGroupPriceId);
    }

    @RequestMapping("/json/fetch_group_prices/{jobGroupId}")
    public @ResponseBody List<? extends JobsGroupPrices> fetchAllJobsGroupPrices(@PathVariable Integer jobGroupId){
        JobsGroup jobsGroup = jobsGroupSrv.select(jobGroupId);
        return jobsGroupPricesSrv.selectAllJobGroupPrices(jobsGroup);
    }

    @RequestMapping("/json/fetch_all_jobs_group_not_in_controller")
    public @ResponseBody List<JobsGroupCtrl> fetchAllJobsGroupNotInController(){
        return JobsGroupCtrl.allJobsGroupCtrl(
                jobsGroupSrv.filterJobsGroupWithJobsInJobsGroup(
                        jobsGroupSrv.selectAllJobGroups(), overview.getAllJobsInJobsGroup()));
    }
    
    @RequestMapping(value = "/json/add_jobs_group_in_controller", method = RequestMethod.POST)
    public @ResponseBody String addJobsGroupInController(@RequestBody List<JobsGroupCtrl> allJobsGroup){
        
        for(JobsGroupCtrl jobsGroupCtrl: allJobsGroup){
            try {
                if (jobsGroupCtrl.getChecked().equals(1)){
                    overview = jobsGroupSrv.addJobsGroupInOverview(overview, jobsGroupCtrl);
                }
            } catch (ParseException ex) {
                Logger.getLogger(JobsGroupRestCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return "200";
    }
    
    @RequestMapping("/json/fetch_job_group/{jobGroupId}")
    public @ResponseBody JobsGroup fetchJobGroup(@PathVariable Integer jobGroupId){
        return jobsGroupSrv.select(jobGroupId);
    }
    
    @RequestMapping("/json/save_job_group")
    public @ResponseBody String saveJobGroup(@RequestBody JobsGroupCtrl jobsGroup){
        jobsGroupSrv.save(jobsGroup);
        
        return "200";
    }
    
    @RequestMapping("/json/delete_job_group")
    public @ResponseBody String deleteJobGroup(@RequestBody JobsGroupCtrl jobsGroup){
        jobsGroupSrv.delete(jobsGroup);
        
        return "200";
    }
    
    @RequestMapping("/json/fetch_overview_work")
    public @ResponseBody OverviewWork fetchOverviewWork(@RequestBody OverviewWorkHeader overviewWorkHeader){
        try {
            List<JobsGroup> filteredJobsGroup = new ArrayList<JobsGroup>();
            for (JobsGroupCtrl jobsGroup: overviewWorkHeader.getJobsGroup()){
                if (jobsGroup.getChecked().equals(1)){
                    filteredJobsGroup.add(jobsGroup);
                }
            }
            
            return jobsGraphics.overviewWork(overviewWorkHeader.getBeginMonth(), overviewWorkHeader.getEndMonth(), filteredJobsGroup);
        } catch (ParseException ex) {
            Logger.getLogger(JobsGroupRestCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @RequestMapping("/json/fetch_overview")
    public @ResponseBody Overview fetchOverview() throws ParseException{
        return overview;
    }
    
    @RequestMapping("/json/fetch_month")
    public @ResponseBody Overview fetchOverviewMonth(@RequestBody String month) throws ParseException{
        overview.setMonth(month);
        overview.setAllJobsInJobsGroup(jobsSrv.selectMonthJobsInJobGroup(month, overview));

        for (JobsInJobsGroup jobsInJobsGroup: overview.getAllJobsInJobsGroup()){
            for(JobsCtrlPojo job: jobsInJobsGroup.getJobs()){
                if (job.getPk_id() != null){
                    List<TickedJobsCtrlPojo> tickedJobsCtrlPojo = new ArrayList<TickedJobsCtrlPojo>();
                    List<JobVatCompensationCtrlPojo> jobVatCompensationCtrlPojos = new ArrayList<>();
                    for (TickedJobs tickedJobs: tickedJobsSrv.selectTickedJobsByJob(job)){
                        tickedJobsCtrlPojo.add(new TickedJobsCtrlPojo(tickedJobs));
                    }
                    for (JobVatCompensation jobVatCompensation: jobVatCompensationDao.selectJobVatCompensations(job.getPk_id())){
                        jobVatCompensationCtrlPojos.add(new JobVatCompensationCtrlPojo(jobVatCompensation));
                    }
                    job.setTickedJobsDetail(TickedJobsDetailSrv.calculate(tickedJobsCtrlPojo, jobVatCompensationCtrlPojos, new BigDecimal(30)));
                }
            }
        }

        return overview;
    }
    
    @RequestMapping("/json/save_jobs_overview")
    public @ResponseBody Overview saveJobsOverview(@RequestBody Overview overview) throws ParseException{
        this.overview = overview;
        
        for(JobsInJobsGroup jobsInJobGroup: overview.getAllJobsInJobsGroup()){
            jobsSrv.saveJobs(jobsSrv.filterMonth(jobsInJobGroup.getJobs()));
        }

        return fetchOverviewMonth(overview.getMonth());
    }
    @RequestMapping("/json/save_job_group_price")
    public @ResponseBody String saveJobGroupPrice(@RequestBody JobsGroupPricesCtrlPojo jobsGroupPrices){
        jobsGroupPricesSrv.save(jobsGroupPrices);

        return "200";
    }

    @RequestMapping("/json/delete_job_group_price")
    public @ResponseBody String deleteJobGroupPrice(@RequestBody JobsGroupPricesCtrlPojo jobsGroupPrices){
        jobsGroupPricesSrv.delete(jobsGroupPrices);

        return "200";
    }

    @RequestMapping("/json/fetch_all_companies")
    public @ResponseBody List<CompanyCtrlPojo> fetchAllCompanies(){
        return CompanyCtrlPojo.allCompanies(companySrv.selectAll());
    }
    
    @RequestMapping("/json/fetch_company")
    public @ResponseBody CompanyCtrlPojo fetchCompany(@RequestBody Integer pk_id){
        return new CompanyCtrlPojo(companySrv.select(pk_id));
    }
    
    @RequestMapping("/json/save_company")
    public @ResponseBody CompanyCtrlPojo saveCompany(@RequestBody CompanyCtrlPojo company){
        return new CompanyCtrlPojo(companySrv.save(company));
    }
    
    @RequestMapping("/json/delete_company")
    public @ResponseBody String deleteCompany(@RequestBody CompanyCtrlPojo company){
        companySrv.delete(company);
        
        return "200";
    }
}
