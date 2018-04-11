/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.domain.Rekeningen;
import java.math.BigDecimal;
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
public interface RekeningenDaoImpl extends CrudRepository<RekeningenDaoPojo, Integer>{

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from RekeningenDaoPojo")
    public Integer getNewPkId();

    @Query("from RekeningenDaoPojo where coalesce(closed, 0) !=  1 and persoon.pk_id = :persoonId")
    public List<Rekeningen> findAllOpenRekeningen(@Param("persoonId") Integer persoonId);

    @Query("from RekeningenDaoPojo where persoon.pk_id = :persoonId")
    public List<Rekeningen> findAllRekeningen(@Param("persoonId") Integer persoonId);
    
    @Query("select sum(waarde) from RekeningenDaoPojo where persoon.pk_id = :persoonId")
    public BigDecimal totalAllRekeningen(@Param("persoonId") Integer persoonId);
}
