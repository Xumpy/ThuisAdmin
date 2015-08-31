/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.itext.services;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xumpy.timesheets.domain.Jobs;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author nico
 */
public class TimeSheet {
    
    public List<PdfPCell> header() throws BadElementException, IOException, URISyntaxException{
        List<PdfPCell> header = new ArrayList<PdfPCell>();
        
        Image img = Image.getInstance(this.getClass().getResource("/timesheet/QR.png"));
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(this.getClass().getResource("/timesheet/default.properties").toURI())));
        
        img.scaleToFit(120, 120);
        
        PdfPCell cell1 = new PdfPCell(new Paragraph("\nTimesheet Report\n\n" + properties.getProperty("name")));
        PdfPCell cell2 = new PdfPCell();
        PdfPCell cell3 = new PdfPCell(img);
        
        header.add(cell1);
        header.add(cell2);
        header.add(cell3);
        
        return header;
    }
    
    public List<PdfPCell> footer(){
        List<PdfPCell> footer = new ArrayList<PdfPCell>();
        
        PdfPCell cell1 = new PdfPCell(new Paragraph("Consultant Signature"));
        PdfPCell cell2 = new PdfPCell();
        PdfPCell cell3 = new PdfPCell(new Paragraph("Client Signature"));
        
        footer.add(cell1);
        footer.add(cell2);
        footer.add(cell3);
        
        return footer;
    }
    
    
    public PdfPTable body(List<Jobs> jobs) throws DocumentException{
        BigDecimal totalHours = new BigDecimal(0);
        
        PdfPTable timesheets = new PdfPTable(5);
        timesheets.setWidths(new int[]{25, 20, 10, 10, 48});
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        
        PdfPCell header1 = new PdfPCell(new Paragraph("Client"));
        PdfPCell header2 = new PdfPCell(new Paragraph("Date"));
        PdfPCell header3 = new PdfPCell(new Paragraph("Hours"));
        PdfPCell header4 = new PdfPCell(new Paragraph("Perc."));
        PdfPCell header5 = new PdfPCell(new Paragraph("Remarks"));
        
        timesheets.addCell(header1);
        timesheets.addCell(header2);
        timesheets.addCell(header3);
        timesheets.addCell(header4);
        timesheets.addCell(header5);
        
        for (Jobs job: jobs){
            PdfPCell cell1 = new PdfPCell(new Paragraph(job.getJobsGroup().getName()));
            PdfPCell cell2 = new PdfPCell(new Paragraph(df.format(job.getJobDate())));
            PdfPCell cell3 = new PdfPCell(new Paragraph(job.getWorkedHours().toString()));
            PdfPCell cell4 = new PdfPCell(new Paragraph(" "));
            if (job.getPercentage() != null){
                cell4 = new PdfPCell(new Paragraph(job.getPercentage().toString()));
            }
            PdfPCell cell5 = new PdfPCell(new Paragraph(job.getRemarks()));
            
            timesheets.addCell(cell1);
            timesheets.addCell(cell2);
            timesheets.addCell(cell3);
            timesheets.addCell(cell4);
            timesheets.addCell(cell5);
            
            totalHours = totalHours.add(job.getWorkedHours());
        }
        
        PdfPCell footer1 = new PdfPCell(new Paragraph(" "));
        PdfPCell footer2 = new PdfPCell(new Paragraph("Total Hours"));
        PdfPCell footer3 = new PdfPCell(new Paragraph(totalHours.toString()));
        PdfPCell footer4 = new PdfPCell(new Paragraph(" "));
        PdfPCell footer5 = new PdfPCell(new Paragraph(" "));
        
        PdfPCell footer6 = new PdfPCell(new Paragraph(" "));
        PdfPCell footer7 = new PdfPCell(new Paragraph("Total Days"));
        PdfPCell footer8 = new PdfPCell(new Paragraph(totalHours.divide(new BigDecimal(8)).toString()));
        PdfPCell footer9 = new PdfPCell(new Paragraph(" "));
        PdfPCell footer10 = new PdfPCell(new Paragraph(" "));
        
        timesheets.addCell(footer1);
        timesheets.addCell(footer2);
        timesheets.addCell(footer3);
        timesheets.addCell(footer4);
        timesheets.addCell(footer5);
        timesheets.addCell(footer6);
        timesheets.addCell(footer7);
        timesheets.addCell(footer8);
        timesheets.addCell(footer9);
        timesheets.addCell(footer10);
        
        return timesheets;
    }
    
    public OutputStream pdf(List<Jobs> jobs, OutputStream outputStream) throws DocumentException, BadElementException, IOException, URISyntaxException{
        Document document = new Document(PageSize.A4, 0, 0, 160, 220);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        
        HeaderFooter event = new HeaderFooter();
        event.setTableHeader(header());
        event.setTableFooter(footer());
        writer.setPageEvent(event);
        
        document.add(body(jobs));
        
        document.close();
        
        return outputStream;
    }
}
