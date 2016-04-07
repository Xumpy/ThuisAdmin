/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.TickedJobsDaoPojo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public interface TickedJobsDaoImpl extends CrudRepository<TickedJobsDaoPojo, Integer>{

    @Query("from TickedJobsDaoPojo")
    public List<TickedJobsDaoPojo> selectAllTickedJobs();

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from TickedJobsDaoPojo")
    public Integer getNewPkId();

    @Query("from TickedJobsDaoPojo where job.pk_id = :jobId order by ticked asc")
    public List<TickedJobsDaoPojo> selectTickedJobsByJob(@Param("jobId") Integer jobId);
}
