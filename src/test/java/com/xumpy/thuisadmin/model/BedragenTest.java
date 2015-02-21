/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model;

import com.xumpy.thuisadmin.model.db.Bedragen;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Nico
 */
@RunWith(MockitoJUnitRunner.class)
public class BedragenTest {
    @Test
    public void testSortBedragen() throws ParseException{
        List<Bedragen> lstBedragen = new ArrayList<Bedragen>();
        List<Date> tstDates = SortDatums.getDates();
        
        for(Date date: tstDates){
            Bedragen bedrag = new Bedragen();
            bedrag.setDatum(date);
            lstBedragen.add(bedrag);
        }
        
        Collections.sort(lstBedragen);
        
    }
}
