package com.xumpy.timesheets.services.rest;

/**
 * Created by nico on 26/10/2017.
 */
public class RestTimesheetHour {
    private Integer seqNr;
    private String hours;

    public Integer getSeqNr() {
        return seqNr;
    }

    public void setSeqNr(Integer seqNr) {
        this.seqNr = seqNr;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
