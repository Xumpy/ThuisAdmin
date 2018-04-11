package com.xumpy.thuisadmin.dao.implementations;

import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceJobsDaoImpl extends CrudRepository<InvoiceJobsDaoPojo, Integer> {
    @Query("from InvoiceJobsDaoPojo where invoice.pkId = :invoiceId")
    public List<InvoiceJobsDaoPojo> findAllJobsByInvoice(@Param("invoiceId") Integer invoiceId);

    @Query("select sum(amount * job.workedHours) from InvoiceJobsDaoPojo where invoice.pkId = :invoiceId")
    public BigDecimal sumAmount(@Param("invoiceId") Integer invoiceId);
}
