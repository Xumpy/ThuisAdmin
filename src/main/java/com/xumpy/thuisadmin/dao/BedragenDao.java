/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Rekeningen;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface BedragenDao{
    void save(Bedragen bedragen);
    void delete(Bedragen bedragen);
    public List<Bedragen> reportBedragen(Rekeningen rekening, Integer offset, String searchText);
    public Integer getNewPkId();
    public Bedragen findBedrag(Integer bedragId);

    public BigDecimal somBedragDatum(Rekeningen rekening, Date datum);
    public List<Bedragen> BedragInPeriode(Date startDate, Date endDate, Rekeningen rekening, boolean showPublicGroepen);
    public BigDecimal getBedragAtDate(Date date, Rekeningen rekening);
}
