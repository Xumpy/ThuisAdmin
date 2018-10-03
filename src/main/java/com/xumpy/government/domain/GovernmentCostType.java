package com.xumpy.government.domain;

import com.xumpy.thuisadmin.domain.Groepen;

/**
 * Created by nico on 06/09/2018.
 */
public interface GovernmentCostType {
    public Integer getPkId();
    public String getType();
    public Integer getLevel();
    public Groepen getGroep();
}
