package com.xumpy.timesheets.domain;

import java.time.LocalDate;

public interface Event {
    public Integer getPkId();
    public Integer getAbsenceHours();
    public LocalDate getEventDate();
    public String getDescription();
    public Boolean getPublicHoliday();
}
