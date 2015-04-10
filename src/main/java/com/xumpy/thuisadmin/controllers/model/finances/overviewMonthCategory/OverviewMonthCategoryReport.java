/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model.finances.overviewMonthCategory;

/**
 *
 * @author nicom
 */
public class OverviewMonthCategoryReport {
    private String date;
    private Integer mainGroup;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(Integer mainGroup) {
        this.mainGroup = mainGroup;
    }
}
