/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.JobsGroup;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public interface JobsGroupDaoImpl extends CrudRepository<JobsGroupDaoPojo, Integer>{
    
    @Query("from JobsGroupDaoPojo")
    public List<JobsGroupDaoPojo> selectAllJobGroups();

    @Query("from JobsGroupDaoPojo where coalesce(closed, false) !=  true")
    public List<JobsGroupDaoPojo> selectAllOpenJobGroups();

    @Query("select max(pk_id) + 1 as pk_id from JobsGroupDaoPojo")
    public Integer getNewPkId();
}
