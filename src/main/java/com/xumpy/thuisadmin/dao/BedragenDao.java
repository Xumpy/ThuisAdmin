/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import java.io.Serializable;
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
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening);
    
    public List<Bedragen> graphiekBedrag(Rekeningen rekening,
                                         Date beginDate,
                                         Date eindDate);
    public List<OverzichtGroep> graphiekOverzichtGroep(Date beginDate,
                                                       Date eindDate);
    public List<OverzichtGroepBedragen> rapportOverzichtGroepBedragen(Integer typeGroepId,
                                                                      Integer negatief,
                                                                      Date beginDate,
                                                                      Date eindDate);
}
