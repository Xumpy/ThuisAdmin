package com.xumpy.timesheets.services.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nico on 26/10/2017.
 */
@Service
public class TimesheetWebService {
    public RestTimesheet callWebService(String ip, String startDate, String endDate){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + ip + ":8888/timesheet?startDate=" + startDate + "&endDate=" + endDate;

        return restTemplate.getForObject(url, RestTimesheet.class);
    }
}
