/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.JobsGroupPricesDaoPojo;

import java.math.BigDecimal;
import java.util.Date;
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
    public List<JobsGroupPricesDaoPojo> selectAllJobGroupPrices(@Param("jobsGroupId") Integer jobsGroupId);

    @Query("select pricePerHour from JobsGroupPricesDaoPojo where jobsGroup.pk_id = :jobsGroupId and :currentDate between startDate and endDate")
    public BigDecimal selectPriceBetweenDate(@Param("jobsGroupId") Integer jobsGroupId, @Param("currentDate") Date currentDate);

    @Query("from JobsGroupPricesDaoPojo where startDate >= :startDate and endDate <= :endDate")
    List<JobsGroupPricesDaoPojo> selectPeriode(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
