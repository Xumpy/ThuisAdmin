/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
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
public interface GroepenDaoImpl extends CrudRepository<GroepenDaoPojo, Integer>{

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from GroepenDaoPojo")
    public Integer getNewPkId();

    @Query("from GroepenDaoPojo where (persoon.pk_id = :personenId or publicGroep = 1)")
    public List<GroepenDaoPojo> findAllGroepen(@Param("personenId") Integer personenId);

    @Query("from GroepenDaoPojo where hoofdGroep is null and (persoon.pk_id = :personenId or publicGroep = 1)")
    public List<GroepenDaoPojo> findAllHoofdGroepen(@Param("personenId") Integer personenId);

    @Query("from GroepenDaoPojo where hoofdGroep.pk_id = :hoofdGroepId and (persoon.pk_id = :personenId or publicGroep = 1) order by negatief")
    public List<GroepenDaoPojo> findAllGroepen(@Param("hoofdGroepId") Integer hoofdGroepId, @Param("personenId") Integer personenId);

    @Query("from GroepenDaoPojo where coalesce(closed, false) !=  true and (persoon.pk_id = :personenId or publicGroep = 1)")
    public List<GroepenDaoPojo> findAllOpenGroepen(@Param("personenId") Integer personenId);

    @Query("from GroepenDaoPojo where coalesce(closed, false) !=  true and hoofdGroep is null and (persoon.pk_id = :personenId or publicGroep = 1)")
    public List<GroepenDaoPojo> findAllOpenHoofdGroepen(@Param("personenId") Integer personenId);

    @Query("from GroepenDaoPojo where coalesce(closed, false) !=  true and hoofdGroep.pk_id = :hoofdGroepId and (persoon.pk_id = :personenId or publicGroep = 1) order by negatief")
    public List<GroepenDaoPojo> findAllOpenGroepen(@Param("hoofdGroepId") Integer hoofdGroepId, @Param("personenId") Integer personenId);
}
