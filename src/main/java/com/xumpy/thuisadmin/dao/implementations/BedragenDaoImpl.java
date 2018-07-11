/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nico
 */
@Repository
public interface BedragenDaoImpl extends CrudRepository<BedragenDaoPojo, Integer>{

    @Query("from BedragenDaoPojo where persoon.pk_id = :persoonId "
            + "  and (:rekeningId is null or rekening.pk_id = :rekeningId)"
            + "  and coalesce(coalesce(:professional, rekening.professional), 0) = coalesce(rekening.professional, 0)"
            + "  and (:searchText is null or lower(groep.naam) like :searchText " +
                "  or lower(rekening.naam) like :searchText " +
                "  or lower(persoon.naam) like :searchText " +
                "  or lower(persoon.voornaam) like :searchText " +
                "  or cast(bedrag as string) like :searchText " +
                "  or cast(datum as string) like :searchText " +
                "  or lower(omschrijving) like :searchText)" + 
                "  order by datum desc")
    public List<BedragenDaoPojo> reportBedragen(@Param("rekeningId") Integer rekeningId, @Param("searchText") String searchText, @Param("persoonId") Integer persoonId, @Param("professional") Boolean professional, Pageable pageable);
    
    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from BedragenDaoPojo")
    public Integer getNewPkId();
    
    @Query("select sum( case b.groep.negatief when 1 then (b.bedrag * -1) else b.bedrag end) from BedragenDaoPojo b where datum >= :datum and b.rekening.pk_id = :rekeningId")
    public BigDecimal somBedragDatum(@Param("rekeningId") Integer rekeningId, @Param("datum") Date datum);
    
    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate)"
            + " and (:rekeningId is null or rekening.pk_id = :rekeningId) and "
            + " ((:showPublicGroepen = 0 and (persoon.pk_id = :persoonId)) or (groep.publicGroep = :showPublicGroepen)) order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> BedragInPeriode(@Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate, 
                                                    @Param("rekeningId") Integer rekeningId, 
                                                    @Param("showPublicGroepen") Integer showPublicGroepen,
                                                    @Param("persoonId") Integer persoonId);

    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate)"
            + " and (persoon.pk_id = :persoonId)"
            + " and groep.pk_id not in (2,3,4)"
            + " order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> BedragInPeriode(@Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate,
                                                 @Param("persoonId") Integer persoonId);


    @Query("from BedragenDaoPojo where (:rekeningId is null or rekening.pk_id = :rekeningId) and persoon.pk_id = :persoonId and datum > :datum order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> getBedragenUntilDate(@Param("datum") Date date, @Param("rekeningId") Integer rekeningId, @Param("persoonId") Integer persoonId);
}
