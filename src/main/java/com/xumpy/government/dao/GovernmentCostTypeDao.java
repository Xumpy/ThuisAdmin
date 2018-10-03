package com.xumpy.government.dao;

import com.xumpy.government.dao.model.GovernmentCostTypeDaoPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentCostTypeDao extends CrudRepository<GovernmentCostTypeDaoPojo, Integer> {
}
