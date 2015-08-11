/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.rest;

import com.xumpy.timesheets.controller.model.TickedJobsLstCtrlPojo;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.TickedJobsDetailSrv;
import com.xumpy.timesheets.services.TickedJobsSrv;
import java.text.ParseException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nicom
 */
@Controller
@Scope(value="session")
public class TickedJobsRestCtrl {
    @Autowired TickedJobsSrv tickedJobsSrv;
    @Autowired TickedJobsDetailSrv tickedJobsDetailSrv;
    
    @RequestMapping("/json/fetch_all_not_processed_ticked_jobs")
    public @ResponseBody TickedJobsLstCtrlPojo fetchAllNotProcessedTickedJobs(){
        return tickedJobsSrv.allNotProcessedTickedJobs();
    }
    
    @RequestMapping("/json/process_ticked_jobs")
    public @ResponseBody TickedJobsLstCtrlPojo processTickedJobs(@RequestBody TickedJobsLstCtrlPojo tickedJobs){
        tickedJobsSrv.processTickedJobs(tickedJobs);
        
        return tickedJobsSrv.allNotProcessedTickedJobs();
    }
    
    @RequestMapping("/json/ticket_overview_month")
    public @ResponseBody Map<String, Map<String, String>> tickedOverviewMonth(@RequestBody String month) throws ParseException{
        
        return tickedJobsDetailSrv.tickedOverviewMonth(month);
    }
}
