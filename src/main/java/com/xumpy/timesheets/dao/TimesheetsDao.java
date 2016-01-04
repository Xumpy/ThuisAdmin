/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.timesheets.domain.Timesheets;
import java.util.List;

/**
 *
 * @author nico
 */
public interface TimesheetsDao {
    Timesheets select(Integer pk_id);
    List<Timesheets> selectAllTimesheets();
    void save(Timesheets timesheets);
    void delete(Timesheets timesheets);
    Integer getNewPkId();
}
