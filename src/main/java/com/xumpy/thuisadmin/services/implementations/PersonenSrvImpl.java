/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.RegisterUserPage;
import com.xumpy.thuisadmin.domain.Personen;
import com.xumpy.thuisadmin.services.PersonenSrv;
import com.xumpy.thuisadmin.services.model.PersonenSrvPojo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
public class PersonenSrvImpl implements PersonenSrv, Serializable{

    @Autowired
    private PersonenDaoImpl personenDao;
    
    @Autowired
    private UserInfo userInfo;
    
    @Override
    @Transactional
    public Personen save(Personen personen) {
        PersonenSrvPojo personenSrvPojo = new PersonenSrvPojo(personen);
        
        if (!personenSrvPojo.getMd5_password().isEmpty()){
            personenSrvPojo.set_password(personen.getMd5_password());
        }
        
        if (personenSrvPojo.getPk_id() == null){
            personenSrvPojo.setPk_id(personenDao.getNewPkId());
        }
        personenDao.save(new PersonenDaoPojo(personenSrvPojo));
        userInfo.updateBean(personenSrvPojo);
        
        return personenSrvPojo;
    }

    @Override
    @Transactional
    public Personen delete(Personen personen) {
        personenDao.delete(new PersonenDaoPojo(personen));
        return personen;
    }

    @Override
    @Transactional
    public List<? extends Personen> findAllPersonen() {
        return personenDao.findAllPersonen();
    }

    @Override
    @Transactional
    public Personen findPersoon(Integer persoonId) {
        return personenDao.findOne(persoonId);
    }
    
    @Override
    @Transactional
    public Personen findPersoonByUsername(String username) {
        return personenDao.findPersoonByUsername(username);
    }
    
    @Override
    public Personen createRegisterUser(RegisterUserPage registerUserPage){
        PersonenDaoPojo persoon = new PersonenDaoPojo();
        
        persoon.setNaam(registerUserPage.getNaam());
        persoon.setVoornaam(registerUserPage.getVoornaam());
        persoon.setUsername(registerUserPage.getUsername());
        persoon.setMd5_password(registerUserPage.getPassword());

        return persoon;
    }
    
    @Override
    public Personen getWhoAmI(){
        return userInfo.getPersoon();
    }
}
