/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain;

/**
 *
 * @author nicom
 */
public interface JobsGroup {

    String getDescription();

    String getName();

    Integer getPk_id();
    
    Company getCompany();

    Boolean getClosed();

    Integer getExtraTime();
}
