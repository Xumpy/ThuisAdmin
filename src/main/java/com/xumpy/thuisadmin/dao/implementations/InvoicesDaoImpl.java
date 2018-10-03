package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InvoicesDaoImpl extends CrudRepository<InvoicesDaoPojo, Integer> {
    @Query("from InvoicesDaoPojo where invoiceId = :invoiceId")
    public List<InvoicesDaoPojo> findAllInvoicesByInvoice(@Param("invoiceId") String invoiceId);

    @Query("from InvoicesDaoPojo where (invoiceDate >= :startDate and invoiceDate <= :endDate)")
    public List<InvoicesDaoPojo> findAllInvoicesBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
