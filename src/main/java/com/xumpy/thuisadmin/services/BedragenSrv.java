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
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
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
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate, Date eindDate, boolean showBedragPublicGroep);
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                     Date beginDate, 
                                                                     Date eindDate,
                                                                     boolean showBedragPublicGroep);
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                     Integer typeGroepKostOpbrengst, 
                                                                     Date beginDate, 
                                                                     Date eindDate,
                                                                     boolean showBedragPublicGroep);
    public Bedragen findBedrag(Integer bedragId);
    
    public OverzichtGroepBedragenTotal filterOverzichtGroepBedragenTotalGroep(OverzichtGroepBedragenTotal overzichtGroepBedragenTotal, Groepen groep);
    public OverzichtGroepBedragenTotal filterOverzichtGroepBedragenTotalFilter(OverzichtGroepBedragenTotal overzichtGroepBedragenTotal, String filter);
    public List<Bedragen> orderByGroup(List<Bedragen> bedragen);
    public List<String> findAllMonthsBedragen(List<Bedragen> bedragen);
    public Map<Integer, BigDecimal> findMainBedragen(List<Bedragen> bedragen, String Month);
    
    public List<Bedragen> selectBedragenInPeriode(Date beginDate, Date endDate);
    public List<Bedragen> filterBedragenWithMainGroup(List<Bedragen> bedragen, List<Integer> MainGroupId);
}
