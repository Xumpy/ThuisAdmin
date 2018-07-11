/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author nicom
 */
public interface JobsDaoImpl extends CrudRepository<JobsDaoPojo, Integer>{
    @Query("from JobsDaoPojo where jobDate = :date order by pk_id")
    List<JobsDaoPojo> selectDate(@Param("date") Date date);

    @Query("from JobsDaoPojo where jobDate >= :startDate and jobDate <= :endDate order by pk_id")
    List<JobsDaoPojo> selectPeriode(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("from JobsDaoPojo where jobDate >= :startDate and jobDate <= :endDate and jobsGroup.pk_id = :groupId order by pk_id")
    List<JobsDaoPojo> selectPeriodeGroup(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("groupId") Integer groupId);

    @Query("select max(pk_id) + 1 as pk_id from JobsDaoPojo")
    Integer getNewPkId();

    @Query("from JobsDaoPojo where pk_id not in (select job.pk_id from InvoiceJobsDaoPojo) or pk_id = :currentJobId order by jobDate desc")
    List<JobsDaoPojo> selectAllJobsNotInInvoice(@Param("currentJobId") Integer currentJobId);
}
