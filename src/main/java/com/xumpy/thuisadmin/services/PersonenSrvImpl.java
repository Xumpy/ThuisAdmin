/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.PersonenDaoImpl;
import com.xumpy.thuisadmin.model.db.Personen;
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
    
    @Override
    @Transactional(readOnly=false)
    public void save(Personen personen) {
        if (personen.getPk_id() == null){
            personen.setPk_id(personenDao.getNewPkId());
            personenDao.save(personen);
        } else {
            personenDao.update(personen);
        }
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Personen personen) {
        personenDao.update(personen);
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
    
}
