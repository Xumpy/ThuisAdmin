package com.xumpy.government.dao;

import com.xumpy.government.dao.model.GovernmentCostDaoPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentCostDao extends CrudRepository<GovernmentCostDaoPojo, Integer> {
}
