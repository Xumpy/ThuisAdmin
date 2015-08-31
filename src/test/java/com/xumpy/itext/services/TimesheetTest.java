/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.itext.services;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author nico
 */
public class TimesheetTest {
    private TimeSheet timeSheet = new TimeSheet();
    
    @Test
    public void testPdf() throws DocumentException, FileNotFoundException, IOException, BadElementException, URISyntaxException{
        List<Jobs> jobs = new ArrayList<Jobs>();
        
        JobsGroup jobsGroup = Mockito.mock(JobsGroup.class);
        Jobs job = Mockito.mock(Jobs.class);
        
        when(jobsGroup.getName()).thenReturn("Ravago");
        when(job.getJobsGroup()).thenReturn(jobsGroup);
        when(job.getJobDate()).thenReturn(new Date());
        when(job.getPercentage()).thenReturn(null);
        when(job.getRemarks()).thenReturn("test remark");
        when(job.getWorkedHours()).thenReturn(new BigDecimal(8));
        
        for(int i=0; i<20; i++){
            jobs.add(job);
        }
        
        ByteArrayOutputStream pdf = (ByteArrayOutputStream) timeSheet.pdf(jobs, new ByteArrayOutputStream());
    }
}
