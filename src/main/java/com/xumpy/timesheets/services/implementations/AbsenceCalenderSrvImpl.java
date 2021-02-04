package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.implementations.EventDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupPricesDaoImpl;
import com.xumpy.timesheets.dao.model.EventDaoPojo;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupPricesDaoPojo;
import com.xumpy.timesheets.domain.Event;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.services.model.AbsenceCalenderInfoSrvPojo;
import com.xumpy.timesheets.services.model.AbsenceCalenderSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AbsenceCalenderSrvImpl {
    @Autowired private EventDaoImpl eventDao;
    @Autowired private JobsDaoImpl jobsDao;
    @Autowired private JobsGroupPricesDaoImpl jobsGroupPricesDao;

    private static Integer ID;
    private static BigDecimal ABSENCEDAYS;

    private Integer getID(){
        return ID++;
    }

    private Boolean isWeekend(LocalDate date){
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private Integer getTotalWorkingHours(List<JobsGroupPricesDaoPojo> jobsGroupPrices, LocalDate date){
        if (isWeekend(date)) return 0;

        List<Integer> foundWorkingHours = new ArrayList<>();

        for(JobsGroupPricesDaoPojo jobsGroupPrice: jobsGroupPrices){
            Date oldDateNotation = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (!(oldDateNotation.before(jobsGroupPrice.getStartDate()) || oldDateNotation.after(jobsGroupPrice.getEndDate()))){
                foundWorkingHours.add(jobsGroupPrice.getJobsGroup().getCompany().getDailyPayedHours().intValue());
            }
        }

        return foundWorkingHours.isEmpty() ? 0 : Collections.max(foundWorkingHours);
    }

    private Event getEvent(List<EventDaoPojo> events, LocalDate date){
        for (EventDaoPojo event: events){
            if (event.getEventDate().isEqual(date)){
                return event;
            }
        }
        return null;
    }

    private List<Jobs> getJobs(List<JobsDaoPojo> jobs, LocalDate date){
        Date oldDateNotation = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Jobs> dateJobs = new ArrayList<>();

        for (JobsDaoPojo job: jobs){
            if (job.getJobDate().compareTo(oldDateNotation) == 0){
                dateJobs.add(job);
            }
        }

        return dateJobs;
    }

    private Integer totalWorkedHours(List<Jobs> currentJobs){
        Integer totalWorkedHours = 0;
        for (Jobs jobs: currentJobs){
            totalWorkedHours = totalWorkedHours + jobs.getWorkedHours().intValue();
        }

        return totalWorkedHours;
    }

    private AbsenceCalenderInfoSrvPojo createFaultyAbsenceCalenderInfo(LocalDate localDate, Integer absenceHours, Integer pkId){
        AbsenceCalenderInfoSrvPojo absenceCalenderInfo = new AbsenceCalenderInfoSrvPojo();

        absenceCalenderInfo.setId(getID());
        absenceCalenderInfo.setAbsenceHours(absenceHours);
        absenceCalenderInfo.setDescription("Faulty hours logged on working day");
        absenceCalenderInfo.setColor("red");
        absenceCalenderInfo.setStartDate(localDate);
        absenceCalenderInfo.setEndDate(localDate);
        absenceCalenderInfo.setPublicHoliday(false);
        absenceCalenderInfo.setPkId(pkId);

        return absenceCalenderInfo;
    }

    private AbsenceCalenderInfoSrvPojo createEventAbsenceCalenderInfo(LocalDate localDate, Event currentEvent, String color){
        AbsenceCalenderInfoSrvPojo absenceCalenderInfo = new AbsenceCalenderInfoSrvPojo();

        absenceCalenderInfo.setId(getID());
        absenceCalenderInfo.setAbsenceHours(currentEvent.getAbsenceHours());
        absenceCalenderInfo.setDescription(currentEvent.getDescription());
        absenceCalenderInfo.setColor(color);
        absenceCalenderInfo.setStartDate(localDate);
        absenceCalenderInfo.setEndDate(localDate);
        absenceCalenderInfo.setPublicHoliday(currentEvent.getPublicHoliday());
        absenceCalenderInfo.setPkId(currentEvent.getPkId());

        return absenceCalenderInfo;
    }

    private AbsenceCalenderInfoSrvPojo createAbscenceCalenderInfo(LocalDate currentDate,
                                                                  Integer workingHours,
                                                                  Event currentEvent,
                                                                  List<Jobs> currentJobs){
        if (currentEvent == null){
           if (isWeekend(currentDate) || totalWorkedHours(currentJobs) >= workingHours || currentDate.isAfter(LocalDate.now().minusDays(1))){
               return null;
           } else {
               return createFaultyAbsenceCalenderInfo(currentDate, 0, null);
           }
        } else {
            Integer eventAbsenceHours = currentEvent.getAbsenceHours() == null ? 0 : currentEvent.getAbsenceHours();

            if (!currentEvent.getPublicHoliday() && !isWeekend(currentDate)) ABSENCEDAYS = ABSENCEDAYS.add(new BigDecimal(eventAbsenceHours).divide(new BigDecimal(workingHours)));

            if (currentEvent.getPublicHoliday()){
                return createEventAbsenceCalenderInfo(currentDate, currentEvent, "grey");
            } else if (isWeekend(currentDate) || totalWorkedHours(currentJobs) >= workingHours){
                return createEventAbsenceCalenderInfo(currentDate, currentEvent, "blue");
            } else if ((totalWorkedHours(currentJobs) + eventAbsenceHours) < workingHours){
                return createFaultyAbsenceCalenderInfo(currentDate, (totalWorkedHours(currentJobs) + eventAbsenceHours), currentEvent.getPkId());
            } else {
                if (eventAbsenceHours >= workingHours){
                    return createEventAbsenceCalenderInfo(currentDate, currentEvent, "green");
                } else {
                    return createEventAbsenceCalenderInfo(currentDate, currentEvent, "yellow");
                }
            }
        }
    }

    public AbsenceCalenderSrvPojo generateAbsenceCalender(Integer year){
        ID = new Integer(0);
        ABSENCEDAYS = new BigDecimal(0);

        AbsenceCalenderSrvPojo absenceCalender = new AbsenceCalenderSrvPojo();

        List<AbsenceCalenderInfoSrvPojo> absenceCalenderInfos = new ArrayList<>();
        absenceCalender.setCalenderInfo(absenceCalenderInfos);

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        Date oldStartDateNotation = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date oldEndDateNotation = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<EventDaoPojo> events = eventDao.selectPeriode(startDate, endDate);
        List<JobsDaoPojo> jobs = jobsDao.selectPeriode(oldStartDateNotation, oldEndDateNotation);
        List<JobsGroupPricesDaoPojo> jobsGroupPrices = jobsGroupPricesDao.selectPeriode(oldStartDateNotation, oldEndDateNotation);

        BigDecimal totalPossibleWorkDays = new BigDecimal(0);

        for (LocalDate currentDate = startDate; currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
            Integer workingHours = getTotalWorkingHours(jobsGroupPrices, currentDate);

            Event currentEvent = getEvent(events, currentDate);
            List<Jobs> currentJobs = getJobs(jobs, currentDate);

            if (!isWeekend(currentDate) && (currentEvent == null || !currentEvent.getPublicHoliday())) totalPossibleWorkDays = totalPossibleWorkDays.add(new BigDecimal(1));

            AbsenceCalenderInfoSrvPojo absenceCalenderInfo = createAbscenceCalenderInfo(currentDate, workingHours, currentEvent, currentJobs);
            if (absenceCalenderInfo != null) absenceCalenderInfos.add(absenceCalenderInfo);
        }

        absenceCalender.setTotalPossibleWorkDays(totalPossibleWorkDays);
        absenceCalender.setTotalAbsenceDays(ABSENCEDAYS);

        return absenceCalender;
    }

    public AbsenceCalenderSrvPojo saveEvent(Event event){
        EventDaoPojo eventDaoPojo = new EventDaoPojo(event);
        eventDao.save(eventDaoPojo);

        return generateAbsenceCalender(event.getEventDate().getYear());
    }

    public AbsenceCalenderSrvPojo deleteEvent(Event event){
        EventDaoPojo eventDaoPojo = new EventDaoPojo(event);
        eventDao.delete(eventDaoPojo);

        return generateAbsenceCalender(event.getEventDate().getYear());
    }
}
