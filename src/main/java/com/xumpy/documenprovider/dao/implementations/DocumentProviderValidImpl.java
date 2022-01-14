package com.xumpy.documenprovider.dao.implementations;

import com.xumpy.documenprovider.dao.model.DocumentProviderValidDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DocumentProviderValidImpl extends CrudRepository<DocumentProviderValidDaoPojo, Integer> {
    @Query("from DocumentProviderValidDaoPojo where (dateFrom <= :datum and coalesce(dateUntil, '9999-12-31') >= :datum)")
    public List<DocumentProviderValidDaoPojo> findAllValidDocumentProviders(@Param("datum") Date datum);
}
