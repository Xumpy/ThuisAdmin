/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.domain.Personen;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public interface PersonenDaoImpl extends CrudRepository<PersonenDaoPojo, Integer>{
    
    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from PersonenDaoPojo")
    public Integer getNewPkId();

    @Query("from PersonenDaoPojo")
    public List<PersonenDaoPojo> findAllPersonen();
    
    @Query("from PersonenDaoPojo where username = :username")
    public Personen findPersoonByUsername(@Param("username") String username);
}
