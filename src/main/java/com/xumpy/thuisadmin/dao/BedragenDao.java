/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface BedragenDao{
    void save(Bedragen bedragen);
    void update(Bedragen bedragen);
    void delete(Bedragen bedragen);
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening, Integer offset, String searchText);
    public Integer getNewPkId();
    public Bedragen findBedrag(Integer bedragId);
    
    public List<OverzichtBedrag> findAllBedragen();
    public List<OverzichtBedrag> findAllBedragen(Date startDate, Date endDate);
    public List<OverzichtBedrag> findBedragenRekening(Rekeningen rekening);
    public List<OverzichtBedrag> findBedragenRekening(Rekeningen rekening, Date startDate, Date endDate);
    public BigDecimal somBedragDatum(Rekeningen rekening, Date datum);
    public Bedragen getBedrag(Integer pk_id);
}
