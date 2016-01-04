/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain;

import java.util.Date;

/**
 *
 * @author nico
 */
public interface Timesheets {
    Integer getPk_id();
    JobsGroup getJobGroup();
    Date getBeginDate();
    Date getEndDate();
    byte[] getDocument();
    String getDocumentMime();
    String getDocumentName();
}
