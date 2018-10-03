package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.thuisadmin.dao.implementations.InvoicesDaoImpl;
import com.xumpy.thuisadmin.domain.Invoices;
import com.xumpy.thuisadmin.services.model.InvoicesSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoicesSrvImpl {
    @Autowired InvoicesDaoImpl invoicesDao;

    public List<InvoicesSrvPojo> findAllInvoicesByInvoice(String invoiceId){
        List<InvoicesSrvPojo> lstInvoicesSrvPojos = new ArrayList<>();

        for(Invoices invoices: invoicesDao.findAllInvoicesByInvoice(invoiceId)){
            lstInvoicesSrvPojos.add(new InvoicesSrvPojo(invoices));
        }

        return lstInvoicesSrvPojos;
    }

    public List<InvoicesSrvPojo> findAllInvoicesBetween(Date startDate, Date endDate){
        List<InvoicesSrvPojo> lstInvoicesSrvPojos = new ArrayList<>();

        for(Invoices invoices: invoicesDao.findAllInvoicesBetween(startDate, endDate)){
            lstInvoicesSrvPojos.add(new InvoicesSrvPojo(invoices));
        }

        return lstInvoicesSrvPojos;
    }
}
