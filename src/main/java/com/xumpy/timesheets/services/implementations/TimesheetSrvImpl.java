/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.itextpdf.text.DocumentException;
import com.xumpy.itext.services.TimeSheet;
import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.TickedJobsDetailSrv;
import com.xumpy.timesheets.services.TimesheetSrv;
import com.xumpy.timesheets.services.model.JobsGroupSrvPojo;
import com.xumpy.timesheets.services.rest.RestTimesheet;
import com.xumpy.timesheets.services.rest.RestTimesheetDetail;
import com.xumpy.timesheets.services.rest.RestTimesheetHour;
import com.xumpy.timesheets.services.rest.TimesheetWebService;
import com.xumpy.utilities.CustomDateUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;

/**
 *
 * @author nico
 */
@Service
public class TimesheetSrvImpl implements TimesheetSrv{

    private Logger log = Logger.getLogger(TimesheetSrvImpl.class.getName());
    private DateFormat format = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
    private static final int MONTHS_TO_GET_FROM_WEBSERVICE = 2;

    @Autowired DataSource dataSource;
    @Autowired JobsDaoImpl jobsDao;
    @Autowired JobsGroupDaoImpl jobsGroupDao;
    @Autowired TimesheetWebService timesheetWebService;
    @Autowired TickedJobsDetailSrv tickedJobsDetailSrv;

    private Map<String, Object> addCompanyParams(Map<String, Object> params, Integer jobsGroupId){
        JobsGroupDaoPojo jobsGroupDaoPojo = jobsGroupDao.findById(jobsGroupId).get();

        params.put("COMPANY_NAME", jobsGroupDaoPojo.getCompany().getName());
        params.put("COMPANY_STREET_NAME_NUMBER", jobsGroupDaoPojo.getCompany().getStreet() + " " + jobsGroupDaoPojo.getCompany().getNumber());
        params.put("COMPANY_POSTAL_CITY_CODE", jobsGroupDaoPojo.getCompany().getPostalCode() + " " + jobsGroupDaoPojo.getCompany().getCity());
        params.put("COMPANY_VAT_NUMBER", jobsGroupDaoPojo.getCompany().getVatNumber());
        params.put("JOB_DESCRIPTION", jobsGroupDaoPojo.getName() + ": " + jobsGroupDaoPojo.getDescription());

        return params;
    }

    private String convertWorkHours(String workHours){
        return new BigDecimal(workHours)
                .setScale(0,BigDecimal.ROUND_DOWN)
                .divide(new BigDecimal(60), RoundingMode.DOWN)
                .divide(new BigDecimal(60), RoundingMode.DOWN)
                .toString();
    }

    private Map<String, Object> addActualTimes(Map<String, Object> params, Integer jobsGroupId, String month) throws ParseException {
        for (Map.Entry<JobsGroupSrvPojo, Map<String, String>> entry: tickedJobsDetailSrv.tickedOverviewMonth(month).entrySet()){
            if (entry.getKey().getPk_id().equals(jobsGroupId)){
                params.put("ACTUAL_WORK_HOURS", convertWorkHours(entry.getValue().get("actualWorked")));
                params.put("EXPECTED_WORK_HOURS", convertWorkHours(entry.getValue().get("timesheetWorked")));
            }
        }

        return params;
    }

    @Override
    @Transactional
    public void generateReport(Integer jobsGroupId, String month, HttpServletResponse response) throws IOException, JRException, SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd"); // This String format has to match string formatter mysql +  jasperreports

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("FIRST_DAY_MONTH", formatter.format(CustomDateUtils.getFirstDayOfMonth(month)));
        params.put("LAST_DAY_MONTH", formatter.format(CustomDateUtils.getLastDayOfMonth(month)));
        params.put("JOBS_GROUP_ID", jobsGroupId);
        params.put("LOGO", "classpath:jasperreports/NMConsultancyLogo.png");

        params = addCompanyParams(params, jobsGroupId);
        params = addActualTimes(params, jobsGroupId, month);

        params.put(JRParameter.REPORT_LOCALE, Locale.GERMANY);
        DefaultJasperReportsContext.getInstance().setProperty(JRPdfExporter.PDF_PRODUCER_FACTORY_PROPERTY, "com.jaspersoft.jasperreports.export.pdf.modern.ModernPdfProducerFactory");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ResourceUtils.getURL("classpath:jasperreports/Timesheet.jasper").openStream());

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=timesheet-" + month + ".pdf");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    private String getStartDate(){
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -MONTHS_TO_GET_FROM_WEBSERVICE);
        return format.format(c.getTime());
    }

    private String getEndDate(){
        return format.format(new Date());
    }

    private TimeRecording createTimeRecording(Integer seqId, String hoursBatch, Integer ticked){
        TimeRecording timeRecording = new TimeRecording();

        timeRecording.setSqlite_id(seqId);
        timeRecording.setStarted(ticked);
        timeRecording.setTicked(hoursBatch);

        return timeRecording;
    }

    private String createFullDate(String dateIn, String hoursIn){
        return dateIn + " " + hoursIn;
    }

    private List<TimeRecording> transformRestTimesheetToTimeRecording(RestTimesheet restTimesheet){
        List<TimeRecording> timeRecordings = new LinkedList<TimeRecording>();

        for (RestTimesheetDetail restTimesheetDetail: restTimesheet.getBatches()){
            if (restTimesheetDetail.getHoursBatchIn() != null){
                for(RestTimesheetHour hoursBatchIn: restTimesheetDetail.getHoursBatchIn()){
                    timeRecordings.add(createTimeRecording(hoursBatchIn.getSeqNr(), createFullDate(restTimesheetDetail.getDate(), hoursBatchIn.getHours()), 10));
                }
            }
            if (restTimesheetDetail.getHoursBatchOut() != null){
                for(RestTimesheetHour hoursBatchOut: restTimesheetDetail.getHoursBatchOut()){
                    timeRecordings.add(createTimeRecording(hoursBatchOut.getSeqNr(), createFullDate(restTimesheetDetail.getDate(), hoursBatchOut.getHours()), 20));
                }
            }
        }

        return timeRecordings;
    }

    public List<TimeRecording> getTimeRecordingFromWeb(String ip){
        return transformRestTimesheetToTimeRecording(timesheetWebService.callWebService(ip, getStartDate(), getEndDate()));
    }
}
