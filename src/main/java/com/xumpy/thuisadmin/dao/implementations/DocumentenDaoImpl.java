/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
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
public interface DocumentenDaoImpl extends CrudRepository<DocumentenDaoPojo, Integer>{

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from DocumentenDaoPojo")
    public Integer getNewPkId();
    
    @Query("from DocumentenDaoPojo where bedrag.persoon.pk_id = :persoonId")
    public List<DocumentenDaoPojo> fetchDocumentenReport(@Param("persoonId") Integer persoonId);

    @Query("from DocumentenDaoPojo where bedrag.pk_id = :bedragId")
    public List<DocumentenDaoPojo> fetchDocumentByBedrag(@Param("bedragId") Integer bedragId);
}
