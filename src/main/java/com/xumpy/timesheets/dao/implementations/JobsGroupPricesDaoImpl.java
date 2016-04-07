/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.JobsGroupPricesDaoPojo;
import com.xumpy.timesheets.domain.JobsGroup;
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
public interface JobsGroupPricesDaoImpl extends CrudRepository<JobsGroupPricesDaoPojo, Integer>{

    @Query("From JobsGroupPricesDaoPojo")
    public List<JobsGroupPricesDaoPojo> selectAll();

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from JobsGroupPricesDaoPojo")
    public Integer getNextPkId();

    @Query("from JobsGroupPricesDaoPojo where jobsGroup.pk_id = :jobsGroupId")
    public List<JobsGroupPricesDaoPojo> selectAllJobGroupPrices(@Param("jobsGroup") Integer jobsGroupId);
}
