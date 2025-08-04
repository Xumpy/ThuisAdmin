package com.xumpy.documenprovider.services.implementations.billit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BillitBuilderTest {
    @Mock InvoiceJobsDaoImpl invoiceJobsDao;
    @InjectMocks BillitBuilder billitBuilder;

    @Test
    public void testBillitBuilder() throws TransformerException, IOException, ParseException {
        Mockito.when(invoiceJobsDao.findAllJobsByInvoice(1)).thenReturn(InvoiceTestData.createInvoiceJobsDaoList());

        Map<String, Object> billitMap = billitBuilder.createInvoice(InvoiceTestData.createDocument(InvoiceTestData.createDuchtInvoic()));

        System.out.println(new ObjectMapper().writeValueAsString(billitMap));
    }
}