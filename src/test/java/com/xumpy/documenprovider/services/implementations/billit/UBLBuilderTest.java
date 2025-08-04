package com.xumpy.documenprovider.services.implementations.billit;


import com.helger.ubl21.UBL21Marshaller;
import com.xumpy.thuisadmin.dao.implementations.InvoiceJobsDaoImpl;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UBLBuilderTest {
    @Mock InvoiceJobsDaoImpl invoiceJobsDao;
    @InjectMocks UBLBuilder ublBuilder;

    @Test
    public void testUblBuilder() throws TransformerException, IOException, ParseException {
        Mockito.when(invoiceJobsDao.findAllJobsByInvoice(1)).thenReturn(InvoiceTestData.createInvoiceJobsDaoList());

        InvoiceType invoiceType = ublBuilder.createUBLInvoice(InvoiceTestData.createDuchtInvoic());
        Document document = UBL21Marshaller.invoice().getAsDocument(invoiceType);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Writer out = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(out));

        String expected = IOUtils.toString(UBLBuilderTest.class.getResourceAsStream("/ublFile.xml"), StandardCharsets.UTF_8.name());

        Assert.assertEquals(expected, out.toString());
    }
}