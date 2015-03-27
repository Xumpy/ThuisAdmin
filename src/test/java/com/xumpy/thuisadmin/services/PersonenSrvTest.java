/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.view.RegisterUserPage;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonenSrvTest {
    @Mock UserInfo userInfo;
    @Spy Personen persoon = new Personen();
    @Spy RegisterUserPage registerUser = new RegisterUserPage();
    @InjectMocks PersonenSrvImpl personenSrv = new PersonenSrvImpl();
    
    @Test
    public void testCreateRegisterUser(){
        registerUser.setNaam("test123");
        registerUser.setVoornaam("test123");
        registerUser.setUsername("test123");
        registerUser.setPassword("test123");
        
        System.out.println("Test");
        
        Personen persoon = personenSrv.createRegisterUser(registerUser);
        persoon.set_password(persoon.getMd5_password());
        
        assertEquals(persoon.getUsername(), "test123");
        assertEquals(persoon.getMd5_password(), "cc03e747a6afbbcbf8be7668acfebee5");
    }
    
    @Test
    public void testWhoAmI(){
        when(userInfo.getPersoon()).thenReturn(persoon);
        when(persoon.getNaam()).thenReturn("Nico");
        
        Personen persoonFetch = personenSrv.getWhoAmI();
        assertEquals(persoonFetch.getNaam(), "Nico");
    }
}
