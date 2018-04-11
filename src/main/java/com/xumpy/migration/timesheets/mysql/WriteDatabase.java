/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.timesheets.mysql;

import com.xumpy.migration.timesheets.model.DataBase;
import com.xumpy.migration.timesheets.model.JobsGroupPojo;
import com.xumpy.migration.timesheets.model.JobsPojo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author nico
 */
public class WriteDatabase {
    private String jdbc;
    private String username;
    private String password;
    
    public WriteDatabase(String jdbc, String username, String password){
        this.jdbc = jdbc;
        this.username = username;
        this.password = password;
    }
    
    public void start(DataBase database) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connection = DriverManager.getConnection(jdbc, username, password);

        String insertJobGroups = "INSERT INTO TA_JOB_GROUPS(PK_ID, JOB_NAME, DESCRIPTION, CLOSED)" +
                                " VALUES(?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(insertJobGroups);
        
        for(JobsGroupPojo jobsGroup: database.getJobsGroup()){
            stmt.setInt(1, jobsGroup.getPk_id());
            stmt.setString(2, jobsGroup.getName());
            stmt.setString(3, jobsGroup.getDescription());
            stmt.setBoolean(4, jobsGroup.getClosed());

            stmt.addBatch();
        }
        stmt.executeBatch();
        
        String insertJobs = "INSERT INTO TA_JOBS(PK_ID, FK_JOB_GROUP_ID, JOB_DATE, WORKED_HOURS, REMARKS)" + 
                                " VALUES(?,?,?,?,?)";
        stmt = connection.prepareStatement(insertJobs);
        
        for(JobsPojo jobs: database.getJobs()){
            stmt.setInt(1, jobs.getPk_id());
            stmt.setInt(2, jobs.getFk_jobs_group_id());
            stmt.setDate(3, (Date) jobs.getJobDate());
            stmt.setBigDecimal(4, jobs.getWorkedHours());
            stmt.setString(5, jobs.getRemarks());
            
            stmt.addBatch();
        }
        stmt.executeBatch();
    }
}
