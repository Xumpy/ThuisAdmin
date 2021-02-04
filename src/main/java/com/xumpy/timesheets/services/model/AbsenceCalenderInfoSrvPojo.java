package com.xumpy.timesheets.services.model;

import java.time.LocalDate;

public class AbsenceCalenderInfoSrvPojo {
    private Integer id;
    private Integer pkId;
    private Integer absenceHours;
    private String description;
    private String color;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean publicHoliday;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbsenceHours() {
        return absenceHours;
    }

    public void setAbsenceHours(Integer absenceHours) {
        this.absenceHours = absenceHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublicHoliday() {
        return publicHoliday;
    }

    public void setPublicHoliday(Boolean publicHoliday) {
        this.publicHoliday = publicHoliday;
    }
}
