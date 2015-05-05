/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.timesheets.domain.JobsGroup;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsGroupDao {
    JobsGroup select(Integer pk_id);
    List<JobsGroup> selectAllJobGroups();
    void save(JobsGroup jobsGroup);
    void delete(JobsGroup jobsGroup);
    Integer getNewPkId();
}
