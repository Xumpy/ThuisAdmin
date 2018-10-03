package com.xumpy.finances.excelbuilder;

import com.xumpy.finances.model.excel.Costs;
import com.xumpy.finances.model.excel.Document;
import com.xumpy.finances.model.excel.ExcelModel;
import com.xumpy.finances.model.excel.Invoices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ExcelZipBuilder {
    @Autowired InvoicesToExcelModel invoicesToExcelModel;

    private ByteArrayOutputStream generateExcel(LocalDate startDate, LocalDate endDate) throws IOException {
        return ExcelModelToExcel.excelFile(invoicesToExcelModel.buildExcelInvoices(startDate, endDate));
    }

    private ByteArrayOutputStream createZipFromMap(Map<String, byte[]> files) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(output);

        for(Map.Entry<String, byte[]> file: files.entrySet()){
            ZipEntry entry = new ZipEntry(file.getKey());
            out.putNextEntry(entry);
            out.write(file.getValue());
        }
        out.close();

        return output;
    }

    public ByteArrayOutputStream buildZip(LocalDate startDate, LocalDate endDate) throws IOException {
        ExcelModel excelModel = invoicesToExcelModel.buildExcelInvoices(startDate, endDate);

        Map<String, byte[]> zipFiles = new HashMap<>();
        zipFiles.put("Accounting.xls", ExcelModelToExcel.excelFile(excelModel).toByteArray());

        for(Invoices invoice: excelModel.getInvoices()){
            for(Document document: invoice.getDocuments()){
                zipFiles.put(document.getDocumentLocations(), document.getDocument());
            }
        }
        for(Costs cost: excelModel.getCosts()){
            for(Document document: cost.getDocuments()){
                zipFiles.put(document.getDocumentLocations(), document.getDocument());
            }
        }

        return createZipFromMap(zipFiles);
    }
}
