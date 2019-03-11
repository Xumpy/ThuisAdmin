package com.xumpy.finances.excelbuilder;

import com.xumpy.finances.model.excel.Costs;
import com.xumpy.finances.model.excel.Document;
import com.xumpy.finances.model.excel.ExcelModel;
import com.xumpy.finances.model.excel.Invoices;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelModelToExcelTest {
    private List<Document> createLocationList(String ... locations){
        List<Document> lstDocuments = new ArrayList<>();
        for(String location: locations){
            Document document = new Document();
            document.setDocumentLocations(location);
            lstDocuments.add(document);
        }
        return lstDocuments;
    }
    private Date createDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }

    private List<Invoices> invoiceMock() throws ParseException {
        List<Invoices> invoices = new ArrayList<>();
        invoices.add(new Invoices(createDate("31/01/2018"), "00001", 10000.89, createLocationList("document1.pdf")));
        invoices.add(new Invoices(createDate("28/02/2018"), "00002", 78945, createLocationList("document2.pdf")));
        invoices.add(new Invoices(createDate("31/03/2018"), "00003", 12345678, createLocationList("document3.pdf")));

        return invoices;
    }

    private List<Costs> costMock() throws ParseException {
        List<Costs> costs = new ArrayList<>();
        costs.add(new Costs(1, createDate("25/01/2018"), "Mangeren", 100, 1000, createLocationList("document1.pdf", "document_shizzle.pdf"), "Nico Martens"));
        costs.add(new Costs(2, createDate("26/01/2018"), "Materioal", 20, 1000.45, createLocationList("document2.pdf"), "Nico Martens"));
        costs.add(new Costs(3, createDate("25/02/2018"), "Mangeren", 12.5, 1000, createLocationList("document3.pdf"), "Nico Martens"));
        costs.add(new Costs(4, createDate("26/02/2018"), "Materioal", 100, 1000, createLocationList("document4.pdf"), "Nico Martens"));
        costs.add(new Costs(5, createDate("26/03/2018"), "Mangeren", 100, 1000, createLocationList("document5.pdf"), "Nico Martens"));
        return costs;
    }

    private void writeOutputStreamToResourceFolder(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream( new File("src/test/resources/excelExample.xls"));
        byteArrayOutputStream.writeTo(fileOutputStream);
    }

    @Test
    public void testGenerateExcel() throws IOException, ParseException {
        ExcelModel excelModel = new ExcelModel();
        excelModel.setCosts(costMock());
        excelModel.setInvoices(invoiceMock());

        ExcelModelToExcel excelModelToExcel = new ExcelModelToExcel();

        ByteArrayOutputStream outputStream = excelModelToExcel.excelFile(excelModel);
        //writeOutputStreamToResourceFolder(outputStream);
    }
}
