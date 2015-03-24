/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.PersonenDaoImpl;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.view.RegisterUserPage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
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
    private Personen persoon;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Personen personen) {
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
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            Personen persoon = new Personen();
            
            persoon.setNaam(registerUserPage.getNaam());
            persoon.setVoornaam(registerUserPage.getVoornaam());
            persoon.setUsername(registerUserPage.getUsername());
            persoon.setMd5_password((new HexBinaryAdapter()).marshal(md.digest(registerUserPage.getPassword().getBytes())).toLowerCase());
            
            return persoon;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PersonenSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @Override
    public Personen getWhoAmI(){
        return persoon;
    }
}
