/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.global.MainSetup;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class Setup extends MainSetup{
    @InjectMocks public JobsGroupDaoImpl jobsGroupDao;
    @InjectMocks public JobsDaoImpl jobsDao;
    
    @BeforeClass
    public static void setUp(){
        config.addAnnotatedClass(JobsGroupDaoPojo.class);
        config.addAnnotatedClass(JobsDaoPojo.class);
        startSession();
    }
}
