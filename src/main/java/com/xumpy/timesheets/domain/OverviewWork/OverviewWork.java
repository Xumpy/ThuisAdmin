/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain.OverviewWork;

import java.util.List;

public interface OverviewWork {
    public OverviewWorkDetails getWorkDetails();
    public List<? extends MonthlyOverviewWorkDetails> getMonthlyWorkDetails();
}
