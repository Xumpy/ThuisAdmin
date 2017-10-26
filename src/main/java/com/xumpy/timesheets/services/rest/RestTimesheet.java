package com.xumpy.timesheets.services.rest;

import java.util.List;

/**
 * Created by nico on 26/10/2017.
 */
public class RestTimesheet {
    private List<RestTimesheetDetail> batches;

    public List<RestTimesheetDetail> getBatches() {
        return batches;
    }

    public void setBatches(List<RestTimesheetDetail> batches) {
        this.batches = batches;
    }
}
