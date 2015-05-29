/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicom
 */
public class CustomDateUtils {
    public static Date getFirstDayOfMonth(String month) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        return df.parse("01/" + month);
    }
    
    public static Date getLastDayOfMonth(String month) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        Date convertedDate = df.parse("01/"+ month);
        Calendar c = Calendar.getInstance();
        c.setTime(convertedDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        return c.getTime();
    }

    public static List<String> getAllMonthsBetweenMonths(String beginMonth, String endMonth) throws ParseException {
        List<String> allMonthsBetweenMonths = new ArrayList<String>();
        
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dfOut = new SimpleDateFormat("MM/yyyy");
        
        Date beginDate = df.parse("01/"+ beginMonth);
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        
        Date endDate = df.parse("01/"+ endMonth);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.add(Calendar.MONTH, 1);
        
        while(beginCal.getTime().before(endCal.getTime())){
            allMonthsBetweenMonths.add(dfOut.format(beginCal.getTime()));
            beginCal.add(Calendar.MONTH, 1);
        }
        
        return allMonthsBetweenMonths;
    }
    
    public static boolean isWeekDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        return cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
    }
    
    public static List<Date> getWeekDays(String month) throws ParseException{
        List<Date> weekDays = new ArrayList<Date>();
        
        Date firstDayOfMonth = getFirstDayOfMonth(month);
        Date lastDayOfMonth = getLastDayOfMonth(month);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDayOfMonth);

        do{
            if (isWeekDay(cal.getTime())){
                weekDays.add(cal.getTime());
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        while(cal.getTime().before(lastDayOfMonth));
        
        return weekDays;
    }
    
    public static List<Date> getWeekendDays(String month) throws ParseException{
        List<Date> weekendDays = new ArrayList<Date>();
        
        Date firstDayOfMonth = getFirstDayOfMonth(month);
        Date lastDayOfMonth = getLastDayOfMonth(month);
        
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(firstDayOfMonth);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(lastDayOfMonth);
        calEnd.add(Calendar.DATE, 1);
        
        do{
            if (!isWeekDay(calStart.getTime())){
                weekendDays.add(calStart.getTime());
            }
            calStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        while(calStart.getTime().before(calEnd.getTime()));
        
        return weekendDays;
    }
}
