package com.xumpy.finances.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OverviewPDFBuilder {
    @Autowired DocumentenDaoImpl documentenDao;

    private Map<Integer, List<DocumentenDaoPojo>> mappedDocuments = new HashMap<>();

    private Phrase createTableHeader(String text){
        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Phrase tableHeader = new Phrase(text, bold);
        return tableHeader;
    }

    private BigDecimal buildTotal(List<OverzichtGroepBedragen> overzichtGroepBedragen){
        BigDecimal sum = new BigDecimal(0);
        for (OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragen){
            sum = sum.add(overzichtGroepBedrag.getBedrag());
        }
        return sum;
    }

    private PdfPTable createOverviewExportedAmountsTable(List<OverzichtGroepBedragen> overzichtGroepBedragen) throws ParseException {
        PdfPTable table = new PdfPTable(4);

        PdfPCell dateColumn = new PdfPCell(createTableHeader("Date"));
        PdfPCell amountColumn = new PdfPCell(createTableHeader("Amount"));
        PdfPCell descriptionColumn = new PdfPCell(createTableHeader("Description"));
        PdfPCell availableColumn = new PdfPCell(createTableHeader("Document Available"));

        table.addCell(dateColumn);
        table.addCell(amountColumn);
        table.addCell(descriptionColumn);
        table.addCell(availableColumn);

        for(OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragen){
            PdfPCell datum = new PdfPCell(new Paragraph(overzichtGroepBedrag.getDatum()));
            PdfPCell amount = new PdfPCell(new Paragraph(overzichtGroepBedrag.getBedrag().setScale(0, RoundingMode.HALF_UP).toString()));
            PdfPCell description = new PdfPCell(new Paragraph(overzichtGroepBedrag.getOmschrijving()));
            PdfPCell documentAvailable = new PdfPCell(new Paragraph(mappedDocuments.get(overzichtGroepBedrag.getPk_id()).isEmpty() ? "X": "/"));

            table.addCell(datum);
            table.addCell(amount);
            table.addCell(description);
            table.addCell(documentAvailable);
        }
        return  table;
    }

    private void attachPdfs(List<OverzichtGroepBedragen> overzichtGroepBedragen, PdfCopy copy) throws IOException, DocumentException {
        for(OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragen){
            for(DocumentenDaoPojo documenten: mappedDocuments.get(overzichtGroepBedrag.getPk_id())){
                PdfReader reader = new PdfReader(documenten.getDocument());
                copy.addDocument(reader);
                reader.close();
            }
        }
    }

    private void createPDF(PdfCopy copy, List<OverzichtGroepBedragen> overzichtGroepBedragen) throws DocumentException, IOException, ParseException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        document.add(createTableHeader("Overview"));
        document.add(createOverviewExportedAmountsTable(overzichtGroepBedragen));
        document.add(createTableHeader("Total: " + buildTotal(overzichtGroepBedragen)));
        document.close();

        copy.addDocument(new PdfReader(byteArrayOutputStream.toByteArray()));
        attachPdfs(overzichtGroepBedragen, copy);
    }

    private void buildMappedDocuments(List<OverzichtGroepBedragen> overzichtGroepBedragen){
        for(OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragen){
            List<DocumentenDaoPojo> documenten = documentenDao.fetchDocumentByBedrag(overzichtGroepBedrag.getPk_id());
            mappedDocuments.put(overzichtGroepBedrag.getPk_id(), documenten);
        }
    }

    public String buildPDFOveriew(List<OverzichtGroepBedragen> overzichtGroepBedragen) throws DocumentException, IOException, ParseException {
        buildMappedDocuments(overzichtGroepBedragen);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        PdfCopy copy = new PdfSmartCopy(document, byteStream);

        document.open();
        createPDF(copy, overzichtGroepBedragen);
        document.close();

        byte[] encoded = Base64.getEncoder().encode(byteStream.toByteArray());
        return new String(encoded);
    }
}
