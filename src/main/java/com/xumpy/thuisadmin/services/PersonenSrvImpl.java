/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.PersonenDaoImpl;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.view.RegisterUserPage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
@Scope("session")
public class PersonenSrvImpl implements PersonenSrv{

    @Autowired
    private PersonenDaoImpl personenDao;
    
    @Autowired
    private Personen persoon;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Personen personen) {
        if (!personen.getMd5_password().isEmpty()){
            personen.set_password(personen.getMd5_password());
        }
        
        if (personen.getPk_id() == null){
            personen.setPk_id(personenDao.getNewPkId());
            personenDao.save(personen);
        } else {
            personenDao.update(personen);
        }
        persoon = personen;
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Personen personen) {
        personenDao.update(personen);
        persoon = personen;
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Personen personen) {
        personenDao.delete(personen);
    }

    @Override
    @Transactional
    public List<Personen> findAllPersonen() {
        return personenDao.findAllPersonen();
    }

    @Override
    @Transactional
    public Personen findPersoon(Integer persoonId) {
        return personenDao.findPersoon(persoonId);
    }
    
    @Override
    public Personen createRegisterUser(RegisterUserPage registerUserPage){
        Personen persoon = new Personen();

        persoon.setNaam(registerUserPage.getNaam());
        persoon.setVoornaam(registerUserPage.getVoornaam());
        persoon.setUsername(registerUserPage.getUsername());
        persoon.setMd5_password(registerUserPage.getPassword());

        return persoon;
    }
    
    @Override
    public Personen getWhoAmI(){
        // Transfer object to new Personen object because json can't handle the session scope
        // This is necessary because Personen is not only a session object but also proxied because it is set
        // in the Application Context
        
        Personen returnPersoon = new Personen();
        returnPersoon.setNaam(persoon.getNaam());
        returnPersoon.setVoornaam(persoon.getVoornaam());
        returnPersoon.setUsername(persoon.getUsername());
        returnPersoon.setMd5_password(persoon.getMd5_password());
        returnPersoon.setPk_id(persoon.getPk_id());
        
        return returnPersoon;
    }
}
