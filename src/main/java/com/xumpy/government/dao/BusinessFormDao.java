package com.xumpy.government.dao;

import com.xumpy.government.dao.model.BusinessFormDaoPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessFormDao extends CrudRepository<BusinessFormDaoPojo, Integer> {
}
