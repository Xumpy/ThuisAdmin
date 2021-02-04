package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.Event;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="TA_EVENTS")
public class EventDaoPojo implements Event {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="ABSENCE_HOURS")
    private Integer absenceHours;

    @Column(name="EVENT_DATE")
    private LocalDate eventDate;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="PUBLIC_HOLIDAY")
    private Boolean publicHoliday;

    @Override
    public Integer getPkId() {
        return this.pkId;
    }

    public void setPkId(Integer pk_id) {
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

    public EventDaoPojo(){}

    public EventDaoPojo(Event event){
        this.pkId = event.getPkId();
        this.eventDate = event.getEventDate();
        this.absenceHours = event.getAbsenceHours();
        this.description = event.getDescription();
        this.publicHoliday = event.getPublicHoliday();
    }
}
