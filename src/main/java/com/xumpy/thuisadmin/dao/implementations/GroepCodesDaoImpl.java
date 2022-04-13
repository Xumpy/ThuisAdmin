package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.GroepCodesDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroepCodesDaoImpl extends CrudRepository<GroepCodesDaoPojo, Integer> {
    @Query("from GroepCodesDaoPojo where groep.pk_id = :groepId")
    public List<GroepCodesDaoPojo> findAllByGroep(@Param("groepId") Integer groepId);
}
