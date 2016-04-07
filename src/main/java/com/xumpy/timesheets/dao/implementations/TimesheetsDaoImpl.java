/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.TimesheetsDaoPojo;
import com.xumpy.timesheets.domain.Timesheets;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author nico
 */
public interface TimesheetsDaoImpl extends CrudRepository<TimesheetsDaoPojo, Integer>{

    @Query("from TimesheetsDaoPojo")
    public List<Timesheets> selectAllTimesheets();

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from TimesheetsDaoPojo")
    public Integer getNewPkId();
}
