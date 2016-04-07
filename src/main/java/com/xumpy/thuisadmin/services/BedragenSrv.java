/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.controllers.model.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.controllers.model.NieuwBedrag;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.controllers.model.RekeningOverzicht;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.domain.Rekeningen;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nico
 */
public interface BedragenSrv {
    public Bedragen save(NieuwBedrag bedragen);
    public Bedragen delete(NieuwBedrag bedragen);
    public BeheerBedragenReportLst reportBedragen(BeheerBedragenReportLst beheerBedragenReportLst, Integer offset, Rekeningen rekening, String searchText);
    
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening,
                                         Date beginDate,
                                         Date eindDate);
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate, Date eindDate, Integer showBedragPublicGroep);
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                     Date beginDate, 
                                                                     Date eindDate,
                                                                     Integer showBedragPublicGroep,
                                                                     OverzichtGroepBedragenTotal overzichtGroepBedragenTotal);
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                     Integer typeGroepKostOpbrengst, 
                                                                     Date beginDate, 
                                                                     Date eindDate,
                                                                     Integer showBedragPublicGroep,
                                                                     OverzichtGroepBedragenTotal overzichtGroepBedragenTotal);
    public Bedragen findBedrag(Integer bedragId);
    
    public OverzichtGroepBedragenTotal filterOverzichtGroepBedragenTotalGroep(OverzichtGroepBedragenTotal overzichtGroepBedragenTotal, Groepen groep);
    public OverzichtGroepBedragenTotal filterOverzichtGroepBedragenTotalFilter(OverzichtGroepBedragenTotal overzichtGroepBedragenTotal, String filter);
    public List<? extends Bedragen> orderByGroup(List<? extends Bedragen> bedragen);
    public List<String> findAllMonthsBedragen(List<? extends Bedragen> bedragen);
    public Map<Integer, BigDecimal> findMainBedragen(List<? extends Bedragen> bedragen, String Month);
    
    public List<? extends Bedragen> selectBedragenInPeriode(Date beginDate, Date endDate);
    public List<? extends Bedragen> filterBedragenWithMainGroup(List<? extends Bedragen> bedragen, List<Integer> MainGroupId);
    public BigDecimal getBedragAtDate(Date date, Rekeningen rekening);
}
