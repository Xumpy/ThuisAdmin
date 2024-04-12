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

    @Query("from DocumentProviderDocumentsDaoPojo where feedback like %:feedback%")
    public List<DocumentProviderDocumentsDaoPojo> getDocumentProviderDocumentsByFeedback(@Param("feedback") String feedback);

    @Query("from DocumentProviderDocumentsDaoPojo documentProviderDocument " +
            " where documentProviderDocument.feedback like %:feedback%" +
            "  and (select count(1) from BedragAccountingDaoPojo bedragAccounting " +
            " where bedragAccounting.bedrag.pk_id = documentProviderDocument.documenten.bedrag.pk_id) = 0")
    public List<DocumentProviderDocumentsDaoPojo> getDocumentProviderDocumentsByFeedbackNoAccounting(@Param("feedback") String feedback);
}
