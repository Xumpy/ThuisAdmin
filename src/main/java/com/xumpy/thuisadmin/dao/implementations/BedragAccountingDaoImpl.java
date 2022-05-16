package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.BedragAccountingDaoPojo;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
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
}
