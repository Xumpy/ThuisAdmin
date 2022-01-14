package com.xumpy.documenprovider.dao.implementations;

import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentProviderDocumentsImpl extends CrudRepository<DocumentProviderDocumentsDaoPojo, Integer> {

    @Query("from DocumentProviderDocumentsDaoPojo where documenten.pk_id = :documentId")
    public List<DocumentProviderDocumentsDaoPojo> getDocumentProviderDocumentsByDocumentId(@Param("documentId") Integer documentId);
}
