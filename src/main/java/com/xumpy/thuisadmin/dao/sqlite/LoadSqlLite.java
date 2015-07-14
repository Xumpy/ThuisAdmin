/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.sqlite;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicom
 */
public class LoadSqlLite {
    public static List<TimeRecording> loadTimeRecordings() throws ClassNotFoundException, SQLException{
        List<TimeRecording> timeRecordings = new ArrayList<TimeRecording>();
        
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite::resource:timeRecording.db");
        Statement stmt = c.createStatement();
          
        ResultSet rs = stmt.executeQuery("select SEQNR, STAMP_DATE_STR, CHECK_ACTION from T_STAMP_3");
          
        while(rs.next()){
            TimeRecording timeRecording = new TimeRecording();
             
            timeRecording.setSqlite_id(rs.getInt("SEQNR"));
            timeRecording.setTicked(rs.getString("STAMP_DATE_STR"));
            timeRecording.setStarted(rs.getInt("CHECK_ACTION"));
              
            timeRecordings.add(timeRecording);
        }
        
        return timeRecordings;
    }
}
