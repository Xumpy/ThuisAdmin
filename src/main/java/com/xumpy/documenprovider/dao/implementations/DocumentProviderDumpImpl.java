package com.xumpy.documenprovider.dao.implementations;

import com.xumpy.documenprovider.dao.model.DocumentProviderDumpDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentProviderDumpImpl  extends CrudRepository<DocumentProviderDumpDaoPojo, Integer> {
    @Query("from DocumentProviderDumpDaoPojo where documentProvider.pkId = :documentProviderId")
    public List<DocumentProviderDumpDaoPojo> getDocumentProviderDumpsByProviderId(@Param("documentProviderId") Integer documentProviderId);
}
