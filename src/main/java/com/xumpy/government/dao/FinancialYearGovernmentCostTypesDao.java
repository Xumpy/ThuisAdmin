package com.xumpy.government.dao;

import com.xumpy.government.dao.model.FinancialYearGovernmentCostTypesDaoPojo;
import com.xumpy.government.dao.model.FinancialYearsDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialYearGovernmentCostTypesDao extends CrudRepository<FinancialYearGovernmentCostTypesDaoPojo, Integer> {
    @Query("from FinancialYearGovernmentCostTypesDaoPojo where financialYear.pkId = :financialYearPkId")
    public List<FinancialYearGovernmentCostTypesDaoPojo> findFinancialYearGovernmentCostTypesByFinancialYear(@Param("financialYearPkId") Integer financialYearPkId);
}
