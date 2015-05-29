/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nicom
 */
public class CustomDateUtilsTest {
    
    @Test
    public void testGetFirstDayOfMonth() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String month = "05/2015";
        
        assertEquals(df.parse("01/05/2015"), CustomDateUtils.getFirstDayOfMonth(month));
    }
    
    @Test
    public void testGetLastDayOfMonth() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String month = "02/2015";
        
        assertEquals(df.parse("28/02/2015"), CustomDateUtils.getLastDayOfMonth(month));
    }
    
    @Test
    public void testGetAllMonthsBetweenMonths() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String beginMonth = "02/2015";
        String endMonth = "04/2015";
        
        assertEquals("02/2015", CustomDateUtils.getAllMonthsBetweenMonths(beginMonth, endMonth).get(0));
        assertEquals("03/2015", CustomDateUtils.getAllMonthsBetweenMonths(beginMonth, endMonth).get(1));
        assertEquals("04/2015", CustomDateUtils.getAllMonthsBetweenMonths(beginMonth, endMonth).get(2));
    }
    
    @Test
    public void testIsWeekDay() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(true, CustomDateUtils.isWeekDay(df.parse("29/05/2015")));
        assertEquals(false, CustomDateUtils.isWeekDay(df.parse("30/05/2015")));
    }
    
    @Test
    public void testGetWeekDays() throws ParseException{
        List<Date> weekDaysInMonth = CustomDateUtils.getWeekDays("05/2015");
        assertEquals(21, weekDaysInMonth.size());
    }
    
    @Test
    public void testGetWeekendDays() throws ParseException{
        List<Date> weekendDaysInMonth = CustomDateUtils.getWeekendDays("05/2015");
        assertEquals(10, weekendDaysInMonth.size());
    }
}
