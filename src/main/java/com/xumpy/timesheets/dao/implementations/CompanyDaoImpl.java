/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicom
 */
@Repository
public interface CompanyDaoImpl  extends CrudRepository<CompanyDaoPojo, Integer>{

    @Query("from CompanyDaoPojo")
    public List<CompanyDaoPojo> selectAll();
    
    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from CompanyDaoPojo")
    public Integer getNextPkId();
}
