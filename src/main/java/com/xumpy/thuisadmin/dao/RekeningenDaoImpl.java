/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.util.List;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
public class RekeningenDaoImpl extends HibernateDaoSupport implements RekeningenDao {

    @Override
    @Transactional(readOnly=false)
    public void save(Rekeningen rekeningen) {
        getHibernateTemplate().save(rekeningen);
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Rekeningen rekeningen) {
        getHibernateTemplate().update(rekeningen);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Rekeningen rekeningen) {
        getHibernateTemplate().delete(rekeningen);
    }
    
    public List<Rekeningen> findAllRekeningen() {
        List list = getHibernateTemplate().find(
                "from Rekeningen"
        );
        return list;
    }
}
