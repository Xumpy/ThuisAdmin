/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.NieuwBedrag;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.model.view.RekeningOverzicht;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface BedragenSrv {
    void save(NieuwBedrag bedragen);
    void update(NieuwBedrag bedragen);
    void delete(NieuwBedrag bedragen);
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening, Integer offset);
    
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening,
                                         Date beginDate,
                                         Date eindDate);
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate,
                                                        Date eindDate);
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                      Integer typeGroepKostOpbrengst, 
                                                                      Date beginDate, 
                                                                      Date eindDate);
    public Bedragen findBedrag(Integer bedragId);
}
