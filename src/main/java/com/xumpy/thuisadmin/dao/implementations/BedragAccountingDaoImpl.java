package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.BedragAccountingDaoPojo;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.MonthlyValue;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedragAccountingDaoImpl extends CrudRepository<BedragAccountingDaoPojo, Integer> {
    @Query("from BedragAccountingDaoPojo where bedrag.pk_id = :bedragId")
    public List<BedragAccountingDaoPojo> getAccountantBedragenByBedrag(@Param("bedragId") Integer bedragId);

    @Modifying
    @Query("delete from BedragAccountingDaoPojo where bedrag.pk_id = :bedragId")
    public void deleteAccountantBedragenByBedrag(@Param("bedragId") Integer bedragId);

    @Query(value = "select month(ta_bedrag_accounting.datum) as month," +
            "   code_id as codeId," +
            "   description as description," +
            "   ta_bedrag_accounting.datum as datum," +
            "   sum(if(ta_code_type_groep.negatief = 1, ta_bedrag_accounting.bedrag, ta_bedrag_accounting.bedrag * -1)) as bedrag," +
            "   ta_code_hoofd_groep.name as mainGroup," +
            "   ta_bedrag_accounting.fk_bedrag_id as bedragId" +
            " from (select distinct code_id, description, fk_code_hoofd_groep_id, year, negatief from ta_code_type_groep) ta_code_type_groep " +
            "join ta_code_hoofd_groep" +
            "  on (ta_code_type_groep.fk_code_hoofd_groep_id = ta_code_hoofd_groep.pk_id)" +
            "join ta_bedrag_accounting " +
            "  on (ta_bedrag_accounting.account_code = ta_code_type_groep.code_id " +
            "and year(ta_bedrag_accounting.datum) = ta_code_type_groep.year)" +
            "where ta_code_type_groep.year = :year " +
            "  and ta_code_type_groep.negatief = :negatief " +
            "group by ta_code_type_groep.code_id, " +
            " ta_code_type_groep.description, " +
            " ta_bedrag_accounting.datum, " +
            " ta_code_hoofd_groep.name," +
            " month(ta_bedrag_accounting.datum)," +
            " ta_bedrag_accounting.fk_bedrag_id " +
            "order by code_id," +
            " month(ta_bedrag_accounting.datum)",
            nativeQuery = true)
    public List<MonthlyValue> getMonthlyValues(@Param("year") Integer year, @Param("negatief") Integer negatief);
}
