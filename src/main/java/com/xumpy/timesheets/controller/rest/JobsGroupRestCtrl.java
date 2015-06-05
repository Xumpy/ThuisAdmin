/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.rest;

import com.xumpy.timesheets.controller.model.CompanyCtrlPojo;
import com.xumpy.timesheets.controller.model.JobsGroupCtrl;
import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.controller.model.OverviewWorkHeader;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWork;
import com.xumpy.timesheets.services.CompanySrv;
import com.xumpy.timesheets.services.implementations.JobsGraphics;
import com.xumpy.timesheets.services.JobsGroupSrv;
import com.xumpy.timesheets.services.JobsSrv;
import com.xumpy.timesheets.services.model.JobsInJobsGroup;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired JobsSrv jobsSrv;
    @Autowired CompanySrv companySrv;
    
    @RequestMapping("/json/fetch_all_jobs_group")
    public @ResponseBody List<JobsGroupCtrl> fetchAllJobsGroup(){
        return JobsGroupCtrl.allJobsGroupCtrl(jobsGroupSrv.selectAllJobGroups());
    }
    
    @RequestMapping("/json/fetch_all_jobs_group_not_in_controller")
    public @ResponseBody List<JobsGroupCtrl> fetchAllJobsGroupNotInController(){
        return JobsGroupCtrl.allJobsGroupCtrl(
                jobsGroupSrv.filterJobsGroupWithJobsInJobsGroup(
                        jobsGroupSrv.selectAllJobGroups(), overview.getAllJobsInJobsGroup()));
    }
    
    @RequestMapping("/json/add_jobs_group_in_controller")
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
