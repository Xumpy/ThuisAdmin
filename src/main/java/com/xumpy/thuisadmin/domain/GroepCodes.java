package com.xumpy.thuisadmin.domain;

public interface GroepCodes {
    Integer getPkId();
    Groepen getGroep();
    HoofdCodes getHoofdCode();
    String getCodeId();
    String getDescription();
    Integer getYear();
}
