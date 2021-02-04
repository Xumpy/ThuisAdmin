package com.xumpy.timesheets.controller.rest;

import com.xumpy.timesheets.controller.model.EventCtrlPojo;
import com.xumpy.timesheets.services.implementations.AbsenceCalenderSrvImpl;
import com.xumpy.timesheets.services.model.AbsenceCalenderSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope(value="session")
public class AbsenceCalenderRestCtrl {
    @Autowired AbsenceCalenderSrvImpl absenceCalenderSrv;

    @RequestMapping("/json/absence_calender/{year}")
    public @ResponseBody AbsenceCalenderSrvPojo fetchAbsenceCalender(@PathVariable Integer year){
        return absenceCalenderSrv.generateAbsenceCalender(year);
    }

    @RequestMapping("/json/absence_calender/save_event")
    public @ResponseBody AbsenceCalenderSrvPojo saveEvent(@RequestBody EventCtrlPojo event){
        return absenceCalenderSrv.saveEvent(event);
    }

    @RequestMapping("/json/absence_calender/delete_event")
    public @ResponseBody AbsenceCalenderSrvPojo deleteEvent(@RequestBody EventCtrlPojo event){
        return absenceCalenderSrv.deleteEvent(event);
    }
}
