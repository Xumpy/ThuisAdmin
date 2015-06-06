/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.timesheets.oracle;

import com.xumpy.migration.timesheets.model.DataBase;
import com.xumpy.migration.timesheets.model.JobsGroupPojo;
import com.xumpy.migration.timesheets.model.JobsPojo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class ReadDatabase {
    private String jdbc;
    private String username;
    private String password;
    
    public ReadDatabase(String jdbc, String username, String password){
        this.jdbc = jdbc;
        this.username = username;
        this.password = password;
    }
    
    public DataBase start() throws ClassNotFoundException, SQLException{
        DataBase database = new DataBase();
        
        List<JobsGroupPojo> allJobsGroup = new ArrayList<JobsGroupPojo>();
        List<JobsPojo> allJobs = new ArrayList<JobsPojo>();
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        Connection connection = DriverManager.getConnection(jdbc, username, password);
        Statement stmt = connection.createStatement();
        
        String sql = "select * from ta_job_sub_groep";
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            JobsGroupPojo jobsGroup = new JobsGroupPojo();
            
            jobsGroup.setPk_id(rs.getInt("PK_ID"));
            jobsGroup.setName(rs.getString(("NAAM")));
            jobsGroup.setDescription(rs.getString("OMSCHRIJVING"));
            if (rs.wasNull()){
                jobsGroup.setDescription(null);
            }
            
            allJobsGroup.add(jobsGroup);
        }
        
        sql = "select * from ta_jobs";
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            JobsPojo jobs = new JobsPojo();
            
            jobs.setPk_id(rs.getInt("PK_ID"));
            jobs.setJobDate(rs.getDate(("DATUM")));
            jobs.setFk_jobs_group_id(rs.getInt(("FK_JOB_GROEP_ID")));
            jobs.setWorkedHours(rs.getBigDecimal("GEWERKTE_UREN"));
            
            allJobs.add(jobs);
        }
        
        database.setJobsGroup(allJobsGroup);
        database.setJobs(allJobs);
        
        return database;
    }
}
