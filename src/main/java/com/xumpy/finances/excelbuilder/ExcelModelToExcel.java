package com.xumpy.finances.excelbuilder;

import com.xumpy.finances.model.excel.Costs;
import com.xumpy.finances.model.excel.Document;
import com.xumpy.finances.model.excel.ExcelModel;
import com.xumpy.finances.model.excel.Invoices;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ExcelModelToExcel {
    private static Integer rowCount;
    private static Workbook workbook;
    private static CreationHelper createHelper;

    private static CellStyle stringCellStyle;
    private static CellStyle dateCellStyle;
    private static CellStyle doubleCellStyle;

    private static void autoSizeAllColumns(Sheet sheet){
        for (int i=0; i<6; i++) sheet.autoSizeColumn(i);
    }

    private static Font getFont(Boolean bold, int size){
        Font font = workbook.createFont();
        font.setBold(bold);
        font.setFontHeightInPoints((short) size);
        font.setColor(IndexedColors.BLACK.getIndex());

        return font;
    }

    private static Font headerFont(){
        return getFont(true, 14);
    }

    private static Font titleFont(){
        return getFont(true, 20);
    }

    private static void createTitle(Sheet sheet, String title){
        Row rowTitle = sheet.createRow(rowCount);
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setFont(titleFont());
        Cell cell = rowTitle.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(titleCellStyle);

        rowCount++;
    }

    private static void createHeaderRow(Sheet sheet, String title, String ... values){
        createTitle(sheet, title);
        Row rowHeader = sheet.createRow(rowCount);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont());

        for(int i = 0; i < values.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(values[i]);
            cell.setCellStyle(headerCellStyle);
        }
        rowCount++;
    }

    private static void createCell(Cell cell, Date value){
        cell.setCellValue(value);
        cell.setCellStyle(dateCellStyle);
    }

    private static void createCell(Cell cell, String value){
        cell.setCellValue(value);
        cell.setCellStyle(stringCellStyle);
    }

    private static void createCell(Cell cell, double value){
        cell.setCellValue(value);
        cell.setCellStyle(doubleCellStyle);
    }

    private static void createHyperlinkSingleCell(Cell cell, Document document){
        Hyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
        link.setAddress(document.getDocumentLocations());
        cell.setHyperlink(link);
        cell.setCellValue(document.getDocumentLocations());
    }

    private static void createHyperlinkCell(Cell cell, List<Document> documents){
        for (int i=0; i<documents.size(); i++){
            createHyperlinkSingleCell(cell, documents.get(i));
            rowCount ++;
            cell = cell.getRow().getSheet().createRow(rowCount).createCell(cell.getColumnIndex());
        }
        cell.getRow().getSheet().removeRow(cell.getRow().getSheet().getRow(rowCount));
        rowCount--;
    }

    private static void createInvoiceRows(Sheet sheet, List<Invoices> invoices){
        for(Invoices invoice: invoices){
            Row row = sheet.createRow(rowCount);
            createCell(row.createCell(0), invoice.getDate());
            createCell(row.createCell(1), invoice.getInvoiceId());
            createCell(row.createCell(2), invoice.getAmount());
            createHyperlinkCell(row.createCell(3), invoice.getDocuments());
            rowCount++;
        }
    }

    private static void createCostRows(Sheet sheet, List<Costs> costs){
        for(Costs cost: costs){
            Row row = sheet.createRow(rowCount);
            createCell(row.createCell(0), cost.getDate());
            createCell(row.createCell(1), cost.getDescription());
            createCell(row.createCell(2), cost.getAccountName());
            createCell(row.createCell(3), cost.getAmount());
            createHyperlinkCell(row.createCell(4), cost.getDocuments());
            rowCount++;
        }
    }

    private static void init(){
        rowCount = 0;
        workbook = new XSSFWorkbook();
        createHelper = workbook.getCreationHelper();
        stringCellStyle = workbook.createCellStyle();
        dateCellStyle = workbook.createCellStyle();
        doubleCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
    }

    public static ByteArrayOutputStream excelFile(ExcelModel excelModel) throws IOException {
        init();
        Sheet sheet = workbook.createSheet("Accountancy");
        createHeaderRow(sheet, "Invoices", "Date", "Invoice Id", "Amount", "Document Location");
        createInvoiceRows(sheet, excelModel.getInvoices());
        rowCount++;
        createHeaderRow(sheet, "Costs", "Date", "Description", "Account Name", "Amount", "Document Location");
        createCostRows(sheet, excelModel.getCosts());

        autoSizeAllColumns(sheet);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        return outputStream;
    }
}
