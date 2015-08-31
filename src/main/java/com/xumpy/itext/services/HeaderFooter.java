/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.itext.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nico
 */
public class HeaderFooter extends PdfPageEventHelper{
    
    private List<PdfPCell> tableHeader;
    private List<PdfPCell> tableFooter;
    
    public List<PdfPCell> getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(List<PdfPCell> tableHeader) {
        this.tableHeader = tableHeader;
    }

    public List<PdfPCell> getTableFooter() {
        return tableFooter;
    }

    public void setTableFooter(List<PdfPCell> tableFooter) {
        this.tableFooter = tableFooter;
    }
   
    
    @Override
    public void onEndPage(PdfWriter writer, Document document){
        PdfPTable header = new PdfPTable(3);
        try {
            header.setWidths(new int[]{24, 24, 24});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(20);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            for(PdfPCell cell: tableHeader){
                cell.setBorder(Rectangle.NO_BORDER);
                header.addCell(cell);
            }
            header.writeSelectedRows(0, -1, 60, 803, writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PdfPTable footer = new PdfPTable(3);
        try {
            footer.setWidths(new int[]{24, 24, 24});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(20);
            footer.getDefaultCell().setBorder(Rectangle.BOTTOM);
            for(PdfPCell cell: tableFooter){
                cell.setBorder(Rectangle.NO_BORDER);
                footer.addCell(cell);
            }
            footer.writeSelectedRows(0, -1, 60, 200, writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
