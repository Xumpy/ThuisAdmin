package com.xumpy.timesheets.controller.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xumpy.timesheets.domain.Event;
import java.time.LocalDate;

public class EventCtrlPojo implements Event {
    private Integer pkId;
    private Integer absenceHours;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") private LocalDate eventDate;
    private String description;
    private Boolean publicHoliday;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public Integer getAbsenceHours() {
        return absenceHours;
    }

    public void setAbsenceHours(Integer absenceHours) {
        this.absenceHours = absenceHours;
    }

    @Override
    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean getPublicHoliday() {
        return publicHoliday;
    }

    public void setPublicHoliday(Boolean publicHoliday) {
        this.publicHoliday = publicHoliday;
    }
}
