package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.GroepCodesDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroepCodesDaoImpl extends CrudRepository<GroepCodesDaoPojo, Integer> {
    @Query("from GroepCodesDaoPojo where groep.pk_id = :groepId")
    public List<GroepCodesDaoPojo> findAllByGroep(@Param("groepId") Integer groepId);

    @Query("from GroepCodesDaoPojo where codeId = :codeId")
    public List<GroepCodesDaoPojo> findAllByCode(@Param("codeId") String codeId);

    @Query("from GroepCodesDaoPojo where year = :year")
    public List<GroepCodesDaoPojo> findAllByYear(@Param("year") Integer year);

    @Query("from GroepCodesDaoPojo where hoofdCode.pkId = :hoofdGroepId and year = :year")
    public List<GroepCodesDaoPojo> findAllByYearAndHoofdCodeId(@Param("year") Integer year, @Param("hoofdGroepId") Integer hoofdGroepId);

    @Query("from GroepCodesDaoPojo where hoofdCode.pkId = :hoofdGroepId and year = :year")
    public List<GroepCodesDaoPojo> findAllByGroepIdAndYear(@Param("hoofdGroepId") Integer hoofdGroepId, @Param("year") Integer year);

}
