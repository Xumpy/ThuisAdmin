/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.timesheets.domain.Jobs;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsDao {
    Jobs select(Integer pk_id);
    List<Jobs> selectDate(Date date);
    List<Jobs> selectPeriode(Date startDate, Date endDate);
    void save(Jobs jobs);
    void delete(Jobs jobs);
    Integer getNewPkId();
}
