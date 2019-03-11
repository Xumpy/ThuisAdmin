package com.xumpy.timesheets.dao.implementations;

import com.xumpy.government.dao.model.VatCompensationDaoPojo;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobVatCompensationDaoImpl extends CrudRepository<JobVatCompensationDaoPojo, Integer> {
    @Query("from JobVatCompensationDaoPojo where job.id = :jobId")
    public List<JobVatCompensationDaoPojo> selectJobVatCompensations(@Param("jobId") Integer jobId);
}
