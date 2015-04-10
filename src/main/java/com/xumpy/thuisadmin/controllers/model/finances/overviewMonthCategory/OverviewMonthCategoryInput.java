/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model.finances.overviewMonthCategory;

import java.util.List;

/**
 *
 * @author nicom
 */
public class OverviewMonthCategoryInput {
    String beginDate;
    String endDate;
    List<MainGroupValue> mainGroupValues;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<MainGroupValue> getMainGroupValues() {
        return mainGroupValues;
    }

    public void setMainGroupValues(List<MainGroupValue> mainGroupValues) {
        this.mainGroupValues = mainGroupValues;
    }
}
