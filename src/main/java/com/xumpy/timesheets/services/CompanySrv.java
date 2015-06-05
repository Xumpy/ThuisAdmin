/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.domain.Company;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface CompanySrv {
    public Company select(Integer pk_id);
    public List<Company> selectAll();
    public Company save(Company company);
    public void delete(Company company);
}
