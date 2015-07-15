/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface TickedJobsDao {
    TickedJobs select(Integer pk_id);
    List<TickedJobs> selectAllTickedJobs();
    List<TickedJobs> selectTickedJobsByJob(Jobs job);
    void save(TickedJobs tickedJobs);
    void delete(TickedJobs tickedJobs);
    Integer getNewPkId();
}
