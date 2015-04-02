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
import com.xumpy.thuisadmin.services.PersonenSrv;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
public class PersonenSrvImpl implements PersonenSrv{

    @Autowired
    private PersonenDaoImpl personenDao;
    
    @Autowired
    private UserInfo userInfo;
    
    @Override
    @Transactional(readOnly=false)
    public void save(PersonenDaoPojo personen) {
        if (!personen.getMd5_password().isEmpty()){
            personen.set_password(personen.getMd5_password());
        }
        
        if (personen.getPk_id() == null){
            personen.setPk_id(personenDao.getNewPkId());
            personenDao.save(personen);
        } else {
            personenDao.update(personen);
        }
        userInfo.updateBean(personen);
    }

    @Override
    @Transactional(readOnly=false)
    public void update(PersonenDaoPojo personen) {
        personenDao.update(personen);
        userInfo.updateBean(personen);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(PersonenDaoPojo personen) {
        personenDao.delete(personen);
    }

    @Override
    @Transactional
    public List<PersonenDaoPojo> findAllPersonen() {
        return personenDao.findAllPersonen();
    }

    @Override
    @Transactional
    public PersonenDaoPojo findPersoon(Integer persoonId) {
        return personenDao.findPersoon(persoonId);
    }
    
    @Override
    public PersonenDaoPojo createRegisterUser(RegisterUserPage registerUserPage){
        PersonenDaoPojo persoon = new PersonenDaoPojo();
        
        persoon.setNaam(registerUserPage.getNaam());
        persoon.setVoornaam(registerUserPage.getVoornaam());
        persoon.setUsername(registerUserPage.getUsername());
        persoon.setMd5_password(registerUserPage.getPassword());

        return persoon;
    }
    
    @Override
    public PersonenDaoPojo getWhoAmI(){
        return userInfo.getPersoon();
    }
}
