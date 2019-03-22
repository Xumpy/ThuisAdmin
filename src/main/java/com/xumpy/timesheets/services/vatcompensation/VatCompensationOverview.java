package com.xumpy.timesheets.services.vatcompensation;

import com.xumpy.timesheets.controller.model.VatCompOverviewRequestCtrlPojo;
import com.xumpy.timesheets.controller.model.VatCompOverviewResponseCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VatCompensationOverview {
    @Autowired JobVatCompensationDaoImpl jobVatCompensationDao;

    private SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

    private Date extractFirstDateMonth(String beginMonth) throws ParseException {
        return dt.parse("01/" + beginMonth);

    }

    private Date extractLastDateMonth(String beginDate) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(extractFirstDateMonth(beginDate));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    private String extractMonth(Date date){
        String dateAsString = dt.format(date);
        return dateAsString.substring(3);
    }

    private Map<String, BigDecimal> groupJobCompPerMonth(List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos){
        Map<String, BigDecimal> groupedJobs = new LinkedHashMap<>();

        for(JobVatCompensationDaoPojo jobVatCompensationDaoPojo: jobVatCompensationDaoPojos){
            String month = extractMonth(jobVatCompensationDaoPojo.getJob().getJobDate());
            if (groupedJobs.containsKey(month)){
                groupedJobs.put(month, groupedJobs.get(month).add(jobVatCompensationDaoPojo.getAmount()));
            } else {
                groupedJobs.put(month, jobVatCompensationDaoPojo.getAmount());
            }
        }

        return groupedJobs;
    }

    private List<List<Object>> convertMapToArrayList(Map<String, BigDecimal> values, VatCompOverviewRequestCtrlPojo vatCompOverviewRequestCtrlPojo){
        List<List<Object>> convertedList = new LinkedList<>();

        List<Object> headers = new LinkedList<>();
        headers.add("month");
        headers.add(vatCompOverviewRequestCtrlPojo.getVatCompensation().getName());

        convertedList.add(headers);

        for(Map.Entry<String, BigDecimal> entry: values.entrySet()){
            List<Object> entryVal = new ArrayList<>();

            entryVal.add(entry.getKey());
            entryVal.add(entry.getValue());

            convertedList.add(entryVal);
        }

        return convertedList;
    }

    public VatCompOverviewResponseCtrlPojo fetchVatCompensationsMonth(VatCompOverviewRequestCtrlPojo vatCompOverviewRequestCtrlPojo) throws ParseException {
        VatCompOverviewResponseCtrlPojo vatCompOverviewResponseCtrlPojo = new VatCompOverviewResponseCtrlPojo();

        if (vatCompOverviewRequestCtrlPojo.getVatCompensation() != null){

            List<JobVatCompensationDaoPojo> jobVatCompensationList = jobVatCompensationDao.selectJobVatCompensations(
                    extractFirstDateMonth(vatCompOverviewRequestCtrlPojo.getBeginDate()),
                    extractLastDateMonth(vatCompOverviewRequestCtrlPojo.getEndDate()));

            if (jobVatCompensationList.size() > 0){

                vatCompOverviewResponseCtrlPojo.setValues(convertMapToArrayList(groupJobCompPerMonth(jobVatCompensationList), vatCompOverviewRequestCtrlPojo));

                BigDecimal total = new BigDecimal(0);
                BigDecimal vatCompensation = new BigDecimal(0);

                for (JobVatCompensationDaoPojo jobVatCompensationDaoPojo: jobVatCompensationList){
                    total = total.add(jobVatCompensationDaoPojo.getAmount());
                    vatCompensation = vatCompensation.add(jobVatCompensationDaoPojo.getAmount().multiply(jobVatCompensationDaoPojo.getVatCompensation().getUnitPrice()));
                }

                vatCompOverviewResponseCtrlPojo.setTotal(total);
                vatCompOverviewResponseCtrlPojo.setVatCompensation(vatCompensation);
            }

        }

        return vatCompOverviewResponseCtrlPojo;
    }
}
