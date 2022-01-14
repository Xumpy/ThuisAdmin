package com.xumpy.documenprovider.dao.implementations;

import com.xumpy.documenprovider.dao.model.DocumentProviderDaoPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentProviderImpl extends CrudRepository<DocumentProviderDaoPojo, Integer> {
}
