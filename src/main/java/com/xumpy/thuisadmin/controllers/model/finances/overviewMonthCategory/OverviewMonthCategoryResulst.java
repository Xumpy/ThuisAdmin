/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model.finances.overviewMonthCategory;

import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.GroepenSrv;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nicom
 */
public class OverviewMonthCategoryResulst {
    List<List<Object>> overviewMonthCategory;

    public List<List<Object>> getOverviewMonthCategory() {
        return overviewMonthCategory;
    }

    public void setOverviewMonthCategory(List<List<Object>> overviewMonthCategory) {
        this.overviewMonthCategory = overviewMonthCategory;
    }
    
    public OverviewMonthCategoryResulst(List<? extends Bedragen> bedragen, GroepenSrv groepenSrv, BedragenSrv bedragenSrv){
        overviewMonthCategory = new ArrayList<List<Object>>();
        
        List<? extends Groepen> mainGroups = groepenSrv.findAllHoofdGroepen(bedragen);
        List<String> months = bedragenSrv.findAllMonthsBedragen(bedragen);

        List<Object> firstRow = new ArrayList<Object>();
        firstRow.add("Month");
        firstRow.add("Average");
        for (Groepen groep: mainGroups){
            firstRow.add(groep.getNaam());
        }
        overviewMonthCategory.add(firstRow);
        
        for (String month: months){
            List<Object> row = new ArrayList<Object>();
            row.add(month);
            Map<Integer, BigDecimal> mainBedragen = bedragenSrv.findMainBedragen(bedragen, month);
            row.add(mainBedragen.get(0));
            for (Groepen mainGroup: mainGroups){
                row.add(mainBedragen.get(mainGroup.getPk_id()));
            }
            overviewMonthCategory.add(row);
        }
    }
}
