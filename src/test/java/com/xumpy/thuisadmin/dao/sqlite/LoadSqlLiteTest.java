/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.sqlite;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadSqlLiteTest {
    @Test
    public void testLoadTimeRecordings() throws SQLException, ClassNotFoundException{
        List<TimeRecording> timeRecordings = new ArrayList<TimeRecording>();
        
        timeRecordings = LoadSqlLite.loadTimeRecordings(":resource:timeRecording.db");
        
        assertEquals(380, timeRecordings.size());
        assertEquals(new Integer(2), timeRecordings.get(0).getSqlite_id());
    }
    
    @Test
    public void testDateConversion() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Date date = format.parse("2015-07-14 07:44:00");
        
        assertEquals("2015-07-14 07:44:00", format.format(date));
    }
}
