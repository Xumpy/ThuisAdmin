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
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BedragenDaoImpl extends CrudRepository<BedragenDaoPojo, Integer>{

    @Query("select bedrag from BedragenDaoPojo where courant = 1 and groep.negatief = 1")
    public List<BigDecimal> allCourantNagitveAmounts();

    @Query("select bedrag from BedragenDaoPojo where courant = 1 and groep.negatief = 0")
    public List<BigDecimal> allCourantPositiveAmounts();

    @Query("from BedragenDaoPojo bedragen where persoon.pk_id = :persoonId "
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
    public Slice<BedragenDaoPojo> reportBedragen(@Param("rekeningId") Integer rekeningId,
                                                 @Param("searchText") String searchText,
                                                 @Param("persoonId") Integer persoonId,
                                                 @Param("professional") Boolean professional,
                                                 Pageable pageable);

    @Query("from BedragenDaoPojo bedragen where persoon.pk_id = :persoonId "
            + "  and (:rekeningId is null or rekening.pk_id = :rekeningId)"
            + "  and coalesce(coalesce(:professional, rekening.professional), 0) = coalesce(rekening.professional, 0)"
            + "  and groep.pk_id not in (select groep.pk_id from GroepCodesDaoPojo where codeId = 'INTER_REKENING') "
            + "  and (select count(1) from BedragAccountingDaoPojo bedragAccountingDaoPojo"
            + " where bedragAccountingDaoPojo.bedrag.pk_id = bedragen.pk_id "
            + "   and bedragAccountingDaoPojo.accountCode in (select codeId from GroepCodesDaoPojo where groep.pk_id = bedragen.groep.pk_id)) = 0"
            + "  and (:searchText is null or lower(groep.naam) like :searchText " +
            "  or lower(rekening.naam) like :searchText " +
            "  or lower(persoon.naam) like :searchText " +
            "  or lower(persoon.voornaam) like :searchText " +
            "  or cast(bedrag as string) like :searchText " +
            "  or cast(datum as string) like :searchText " +
            "  or lower(omschrijving) like :searchText)" +
            "  order by datum desc")
    public Slice<BedragenDaoPojo> reportBedragenNoAccounting(@Param("rekeningId") Integer rekeningId,
                                                             @Param("searchText") String searchText,
                                                             @Param("persoonId") Integer persoonId,
                                                             @Param("professional") Boolean professional,
                                                             Pageable pageable);

    @Query("from BedragenDaoPojo bedragen where persoon.pk_id = :persoonId "
            + "  and (:rekeningId is null or rekening.pk_id = :rekeningId)"
            + "  and coalesce(coalesce(:professional, rekening.professional), 0) = coalesce(rekening.professional, 0)"
            + "  and groep.pk_id not in (select groep.pk_id from GroepCodesDaoPojo where codeId = 'INTER_REKENING') "
            + "  and coalesce(managedByAccountant, 0) = 0 "
            + "  and coalesce(courant, 0) = 0 "
            + "  and (select count(1) from DocumentProviderValidDaoPojo documentProviderValid"
                + " where (documentProviderValid.dateFrom <= bedragen.datum and coalesce(documentProviderValid.dateUntil, '9999-12-31') >= bedragen.datum)) "
            + "   > 0 "
            + "  and (select count(1) from DocumentProviderDocumentsDaoPojo documentProviderDocuments "
                + " where documentProviderDocuments.documenten.bedrag.pk_id = bedragen.pk_id "
                + "   and (select count(1) from DocumentProviderValidDaoPojo documentProviderValid "
                        + "where documentProviderValid.documentProvider.pkId = documentProviderDocuments.documentProvider.pkId"
                        + "  and (documentProviderValid.dateFrom <= bedragen.datum and coalesce(documentProviderValid.dateUntil, '9999-12-31') >= bedragen.datum)) > 0)"
                + " <= 0"
            + "  and (:searchText is null or lower(groep.naam) like :searchText " +
            "  or lower(rekening.naam) like :searchText " +
            "  or lower(persoon.naam) like :searchText " +
            "  or lower(persoon.voornaam) like :searchText " +
            "  or cast(bedrag as string) like :searchText " +
            "  or cast(datum as string) like :searchText " +
            "  or lower(omschrijving) like :searchText)" +
            "  order by datum desc")
    public Slice<BedragenDaoPojo> reportInValidAccountantBedragen(@Param("rekeningId") Integer rekeningId,
                                                 @Param("searchText") String searchText,
                                                 @Param("persoonId") Integer persoonId,
                                                 @Param("professional") Boolean professional,
                                                 Pageable pageable);

    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate) and groep.pk_id in (select groep.pk_id from GovernmentCostTypeDaoPojo where type = :levelType)")
    public List<BedragenDaoPojo> allBedragenInCostType(@Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate,
                                                       @Param("levelType") String type);

    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate) and rekening.professional = true"+
            " and groep.pk_id not in (select groep.pk_id from GroepCodesDaoPojo where codeId = 'INTER_REKENING') ")
    public List<BedragenDaoPojo> allProfesionalBedragenInDate(@Param("startDate") Date startDate,
                                                               @Param("endDate") Date endDate);


    @Query("from BedragenDaoPojo where invoice.pkId = :invoiceId")
    public List<BedragenDaoPojo> getBedragenFromInvoice(@Param("invoiceId") Integer invoiceId);

    @Query("select coalesce(max(pk_id),0) + 1 as pk_id from BedragenDaoPojo")
    public Integer getNewPkId();
    
    @Query("select sum( case b.groep.negatief when 1 then (b.bedrag * -1) else b.bedrag end) from BedragenDaoPojo b where datum >= :datum and b.rekening.pk_id = :rekeningId and coalesce(processed, 0) = 1")
    public BigDecimal somBedragDatum(@Param("rekeningId") Integer rekeningId, @Param("datum") Date datum);
    
    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate)"
            + " and (:rekeningId is null or rekening.pk_id = :rekeningId) and "
            + " ((:showPublicGroepen = 0 and (persoon.pk_id = :persoonId)) or (groep.publicGroep = :showPublicGroepen)) and coalesce(processed, 0) = 1 order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> BedragInPeriode(@Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate, 
                                                    @Param("rekeningId") Integer rekeningId, 
                                                    @Param("showPublicGroepen") Integer showPublicGroepen,
                                                    @Param("persoonId") Integer persoonId);

    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate)"
            + " and coalesce(processed, 0) = 1"
            + " and (persoon.pk_id = :persoonId)"
            + " and groep.pk_id not in (2,3,4)"
            + " order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> BedragInPeriode(@Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate,
                                                 @Param("persoonId") Integer persoonId);

    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate)"
            + " and (rekening.pk_id = :rekeningId)"
            + " and groep.pk_id not in (2,3,4)"
            + " and coalesce(processed, 0) = 1"
            + " order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> BedragInPeriodeOpRekening(@Param("startDate") Date startDate,
                                                           @Param("endDate") Date endDate,
                                                           @Param("rekeningId") Integer rekeningId);

    @Query("from BedragenDaoPojo where (:rekeningId is null or rekening.pk_id = :rekeningId) and persoon.pk_id = :persoonId and datum > :datum order by datum asc, bedrag asc")
    public List<BedragenDaoPojo> getBedragenUntilDate(@Param("datum") Date date, @Param("rekeningId") Integer rekeningId, @Param("persoonId") Integer persoonId);

    @Query("from BedragenDaoPojo where (invoice.pkId = :invoiceId)")
    public List<BedragenDaoPojo> getBedragOfInvoice(@Param("invoiceId") Integer invoiceId);

    @Query("from BedragenDaoPojo where (datum >= :startDate and datum <= :endDate) and bedrag = :bedrag and " +
            "coalesce(coalesce(:professional, rekening.professional), 0) = coalesce(rekening.professional, 0)")
    public List<BedragenDaoPojo> getBedragByBedragInPeriod(@Param("bedrag") BigDecimal bedrag,
                                                           @Param("startDate") Date startDate,
                                                           @Param("endDate") Date endDate,
                                                           @Param("professional") Boolean professional);
}
