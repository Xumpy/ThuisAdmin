package com.xumpy.timesheets.services.rest;

import java.util.List;

/**
 * Created by nico on 26/10/2017.
 */
public class RestTimesheetDetail {
    private String date;
    private List<RestTimesheetHour> hoursBatchIn;
    private List<RestTimesheetHour> hoursBatchOut;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RestTimesheetHour> getHoursBatchIn() {
        return hoursBatchIn;
    }

    public void setHoursBatchIn(List<RestTimesheetHour> hoursBatchIn) {
        this.hoursBatchIn = hoursBatchIn;
    }

    public List<RestTimesheetHour> getHoursBatchOut() {
        return hoursBatchOut;
    }

    public void setHoursBatchOut(List<RestTimesheetHour> hoursBatchOut) {
        this.hoursBatchOut = hoursBatchOut;
    }
}
