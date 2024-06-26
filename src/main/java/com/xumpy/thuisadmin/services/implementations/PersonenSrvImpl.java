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
import java.io.Serializable;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
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
        PersonenDaoPojo personenDaoPojo = new PersonenDaoPojo(personen);
        
        if (personenDaoPojo.getMd5_password().equals("")){
            personenDaoPojo.setMd5_password(personenDao.findById(personenDaoPojo.getPk_id()).get().getMd5_password());
        } else {
            personenDaoPojo.setMd5_password(getMD5Password(personenDaoPojo.getMd5_password()));
        }
        
        if (personenDaoPojo.getPk_id() == null){
            personenDaoPojo.setPk_id(personenDao.getNewPkId());
        }
        personenDao.save(personenDaoPojo);
        userInfo.updateBean(personenDaoPojo);
        
        return personenDaoPojo;
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
        return personenDao.findById(persoonId).get();
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

    @Override
    public String getMD5Password(String password) {
        return DigestUtils.md5Hex(password).toLowerCase();
    }
}
