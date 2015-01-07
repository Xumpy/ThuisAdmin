/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface BedragenSrv {
    void save(Bedragen bedragen);
    void update(Bedragen bedragen);
    void delete(Bedragen bedragen);
    public List<Bedragen> graphiekBedrag(Rekeningen rekening,
                                         Date beginDate,
                                         Date eindDate);
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate,
                                                        Date eindDate);
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                      Integer typeGroepKostOpbrengst, 
                                                                      Date beginDate, 
                                                                      Date eindDate);
}
