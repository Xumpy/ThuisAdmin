package com.xumpy.government.dao;


import com.xumpy.government.dao.model.VatCompensationDaoPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatCompensationDao extends CrudRepository<VatCompensationDaoPojo, Integer> {

}
