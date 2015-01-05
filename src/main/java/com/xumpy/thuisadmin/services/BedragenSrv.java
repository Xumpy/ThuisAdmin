/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
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
    public List<Bedragen> graphiekBedrag(Integer rekening_id,
                                         Date beginDate,
                                         Date eindDate);
}
