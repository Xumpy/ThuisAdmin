/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.setup;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.model.Personen;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 * 
 * This mock is made for the mocking of default beans that are assigned and used through the application.
 * Only extend from this class if your test needs to inject those beans
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class MainMock {
    @Mock public Personen loginPersoon;
    @Spy public UserInfo userInfo;
    @Spy public OverzichtGroepBedragenTotal overzichtGroepBedragenTotal;
            
    @Before
    public void setupUserInfo(){
        when(loginPersoon.getNaam()).thenReturn("Test User");
        when(loginPersoon.getPk_id()).thenReturn(1);
        
        when(userInfo.getPersoon()).thenReturn(loginPersoon);
    }
}
