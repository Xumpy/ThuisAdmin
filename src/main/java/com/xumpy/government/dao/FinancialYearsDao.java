package com.xumpy.government.dao;

import com.xumpy.government.dao.model.FinancialYearsDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialYearsDao extends CrudRepository<FinancialYearsDaoPojo, Integer> {
    @Query("from FinancialYearsDaoPojo where year = :year")
    public FinancialYearsDaoPojo findFinancialYearByYear(@Param("year") Integer year);
}
