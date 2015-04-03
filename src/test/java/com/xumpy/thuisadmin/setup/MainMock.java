/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.setup;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.model.Personen;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class MainMock {
    @Mock Personen loginPersoon;
    @Mock UserInfo userInfo;
    
    @Before
    public void setupUserInfo(){
        when(loginPersoon.getNaam()).thenReturn("Test User");
        when(loginPersoon.getPk_id()).thenReturn(1);
        
        when(userInfo.getPersoon()).thenReturn(loginPersoon);
    }
}
