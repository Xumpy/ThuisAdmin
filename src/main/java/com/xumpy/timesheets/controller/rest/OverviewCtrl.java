/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.rest;

import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.services.JobsGroupSrv;
import com.xumpy.timesheets.services.JobsSrv;
import com.xumpy.timesheets.services.model.JobsInJobsGroup;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nicom
 */
@Controller
public class OverviewCtrl implements Serializable{
    @Autowired Overview overview;
    @Autowired JobsGroupSrv jobsGroupSrv;
    @Autowired JobsSrv jobsSrv;
    
    @RequestMapping("/json/fetch_overview")
    public @ResponseBody Overview fetchOverview() throws ParseException{
        if (overview.getMonth() == null){
            SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
            String month = df.format(new Date());

            overview.setMonth(month);
            overview.setAllJobsInJobsGroup(jobsSrv.selectMonthJobsInJobGroup(month));
        }
        
        return overview;
    }
    
    @RequestMapping("/json/fetch_month")
    public @ResponseBody Overview fetchOverviewMonth(@RequestBody String month) throws ParseException{
        overview.setMonth(month);
        overview.setAllJobsInJobsGroup(jobsSrv.selectMonthJobsInJobGroup(month));

        return overview;
    }
    
    @RequestMapping("/json/save_jobs_overview")
    public @ResponseBody String saveJobsOverview(@RequestBody Overview overview){
        for(JobsInJobsGroup jobsInJobGroup: overview.getAllJobsInJobsGroup()){
            jobsSrv.saveJobs(jobsSrv.filterMonth(jobsInJobGroup.getJobs()));
        }
        
        return "1";
    }
}
