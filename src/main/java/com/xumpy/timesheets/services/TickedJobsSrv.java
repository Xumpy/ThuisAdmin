/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsSrvPojo;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface TickedJobsSrv {
    TickedJobs select(Integer pk_id);
    List<TickedJobs> selectAllTickedJobs();
    TickedJobs save(TickedJobs tickedJobs);
    void delete(TickedJobs tickedJobs);
    public List<TickedJobsSrvPojo> allNotProcessedTickedJobs();
}
