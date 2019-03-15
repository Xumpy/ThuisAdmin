package com.xumpy.timesheets.services.vatcompensation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.lowagie.text.pdf.PdfCell;
import com.xumpy.finances.services.ImageShrinker;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import com.xumpy.timesheets.domain.JobVatCompensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VatCompensationPdfBuilder {

    @Autowired JobVatCompensationDaoImpl jobVatCompensationDao;

    private static String formatDate(Date date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    private void attachImages(List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos, Document document) throws IOException, DocumentException {
        for(JobVatCompensationDaoPojo jobVatCompensationDaoPojo: jobVatCompensationDaoPojos){

            Image img = Image.getInstance(jobVatCompensationDaoPojo.getDocument());
            img.scalePercent(25);
            document.add(img);
        }
    }

    private Phrase createTableHeader(String text){
        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Phrase tableHeader = new Phrase(text, bold);
        return tableHeader;
    }

    private Map<String, BigDecimal> generateTotalsJobVatComp(List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos){
        Map<String, BigDecimal> totals = new HashMap<>();
        for (JobVatCompensation jobVatCompensation: jobVatCompensationDaoPojos){
            if (totals.containsKey(jobVatCompensation.getVatCompensation().getName())){
                totals.put(jobVatCompensation.getVatCompensation().getName(), totals.get(jobVatCompensation.getVatCompensation().getName()).add(jobVatCompensation.getAmount()));
            } else {
                totals.put(jobVatCompensation.getVatCompensation().getName(), jobVatCompensation.getAmount());
            }
        }
        return totals;
    }

    private PdfPTable createVatCompensationTotalTable(List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos) throws ParseException {
        PdfPTable table = new PdfPTable(2);
        for(Map.Entry<String, BigDecimal> total: generateTotalsJobVatComp(jobVatCompensationDaoPojos).entrySet()){
            PdfPCell description = new PdfPCell(new Paragraph(total.getKey()));
            PdfPCell amount = new PdfPCell(new Paragraph(total.getValue().setScale(0, RoundingMode.HALF_UP).toString()));
            table.addCell(description);
            table.addCell(amount);
        }
        return  table;
    }


    private PdfPTable createVatCompensationTable(List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos) throws ParseException {
        PdfPTable table = new PdfPTable(3);

        PdfPCell dateColumn = new PdfPCell(createTableHeader("Date"));
        PdfPCell descriptionColumn = new PdfPCell(createTableHeader("Description"));
        PdfPCell amountColumn = new PdfPCell(createTableHeader("Amount"));

        table.addCell(dateColumn);
        table.addCell(descriptionColumn);
        table.addCell(amountColumn);

        for (JobVatCompensation jobVatCompensation : jobVatCompensationDaoPojos) {
            PdfPCell date = new PdfPCell(new Paragraph(formatDate(jobVatCompensation.getJob().getJobDate())));
            PdfPCell description = new PdfPCell(new Paragraph(jobVatCompensation.getVatCompensation().getName()));
            PdfPCell amount = new PdfPCell(new Paragraph(jobVatCompensation.getAmount().setScale(0, RoundingMode.HALF_UP).toString()));

            table.addCell(date);
            table.addCell(description);
            table.addCell(amount);
        }
        return table;
    }

    private void createPDF(Document document, List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos) throws DocumentException, IOException, ParseException {
        document.add(createTableHeader("Overview of VAT Compensations"));
        document.add(createVatCompensationTable(jobVatCompensationDaoPojos));
        document.add(createTableHeader("Totals"));
        document.add(createVatCompensationTotalTable(jobVatCompensationDaoPojos));
        document.newPage();

        attachImages(jobVatCompensationDaoPojos, document);
    }

    public byte[] buildPdf(Date startDate, Date endDate) throws DocumentException, IOException, ParseException {
        List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos = jobVatCompensationDao.selectJobVatCompensations(startDate, endDate);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, byteStream);
        document.open();
        createPDF(document, jobVatCompensationDaoPojos);
        document.close();

        return byteStream.toByteArray();
    }
}
