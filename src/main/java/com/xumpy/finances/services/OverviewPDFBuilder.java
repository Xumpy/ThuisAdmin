package com.xumpy.finances.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;
import java.util.List;

@Service
public class OverviewPDFBuilder {
    @Autowired DocumentenDaoImpl documentenDao;

    private Map<Integer, List<DocumentenDaoPojo>> mappedDocuments = new HashMap<>();

    private Phrase createTableHeader(String text){
        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Phrase tableHeader = new Phrase(text, bold);
        return tableHeader;
    }

    private BigDecimal buildTotal(Map<OverzichtGroepBedragen, Integer> overzichtGroepBedragen){
        BigDecimal sum = new BigDecimal(0);
        for (Map.Entry<OverzichtGroepBedragen, Integer> overzichtGroepBedrag: overzichtGroepBedragen.entrySet()){
            sum = sum.add(overzichtGroepBedrag.getKey().getBedrag());
        }
        return sum;
    }

    private String pageNumber(Integer sizeHeader, Integer currentPage) {
        if (sizeHeader == null) {
            return "Yes";
        } else {
            return Integer.toString(sizeHeader + currentPage);
        }
    }

    private PdfPTable createOverviewExportedAmountsTable(Map<OverzichtGroepBedragen, Integer> overzichtGroepBedragen, Integer sizeHeader) throws ParseException {
        PdfPTable table = new PdfPTable(new float[] { 20, 15, 50, 15 });

        PdfPCell dateColumn = new PdfPCell(createTableHeader("Date"));
        PdfPCell amountColumn = new PdfPCell(createTableHeader("Amount"));
        PdfPCell descriptionColumn = new PdfPCell(createTableHeader("Description"));
        PdfPCell availableColumn = new PdfPCell(createTableHeader("Page"));

        table.addCell(dateColumn);
        table.addCell(amountColumn);
        table.addCell(descriptionColumn);
        table.addCell(availableColumn);

        for(Map.Entry<OverzichtGroepBedragen, Integer> overzichtGroepBedrag: overzichtGroepBedragen.entrySet()){
            PdfPCell datum = new PdfPCell(new Paragraph(overzichtGroepBedrag.getKey().getDatum()));
            PdfPCell amount = new PdfPCell(new Paragraph(overzichtGroepBedrag.getKey().getBedrag().setScale(2, RoundingMode.HALF_UP).toString()));
            PdfPCell description = new PdfPCell(new Paragraph(overzichtGroepBedrag.getKey().getOmschrijving()));
            PdfPCell documentAvailable = new PdfPCell(new Paragraph(mappedDocuments.get(overzichtGroepBedrag.getKey().getPk_id()).isEmpty() ? "": pageNumber(sizeHeader, overzichtGroepBedrag.getValue())));

            table.addCell(datum);
            table.addCell(amount);
            table.addCell(description);
            table.addCell(documentAvailable);
        }
        return  table;
    }

    private Map.Entry<byte[], Map<OverzichtGroepBedragen, Integer>> generatePdfs(List<OverzichtGroepBedragen> overzichtGroepBedragen) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        PdfCopy copy = new PdfSmartCopy(document, byteArrayOutputStream);

        document.open();
        copy.open();

        Map<OverzichtGroepBedragen, Integer> documentPerPage = new LinkedHashMap<>();

        for(OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragen){
            documentPerPage.put(overzichtGroepBedrag, copy.getPageNumber());

            for(DocumentenDaoPojo documenten: mappedDocuments.get(overzichtGroepBedrag.getPk_id())){
                try {
                    PdfReader reader = new PdfReader(documenten.getDocument());
                    copy.addDocument(reader);
                    reader.close();
                } catch (Exception exception){
                    System.out.println("Exception with document: " + documenten.getOmschrijving() + " - " + documenten.getPk_id() + " : Bedrag: " + documenten.getBedrag().getOmschrijving());
                }
            }
        }
        copy.close();

        return new Map.Entry<>() {
            @Override
            public byte[] getKey() {
                return byteArrayOutputStream.toByteArray() ;
            }

            @Override
            public Map<OverzichtGroepBedragen, Integer> getValue() {
                return documentPerPage;
            }

            @Override
            public Map<OverzichtGroepBedragen, Integer> setValue(Map<OverzichtGroepBedragen, Integer> value) {
                throw new RuntimeException("Not implemented");
            }
        };
    }

    private Integer getHeaderPageSize(Map<OverzichtGroepBedragen, Integer> overzichtGroepBedragen) throws DocumentException, ParseException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();

        document.add(createTableHeader("Overview"));
        document.add(createOverviewExportedAmountsTable(overzichtGroepBedragen, null));
        document.add(createTableHeader("Total: " + buildTotal(overzichtGroepBedragen)));

        document.close();

        PdfReader pdfReader = new PdfReader(byteArrayOutputStream.toByteArray());

        return pdfReader.getNumberOfPages();
    }

    private byte[] createHeader(Map<OverzichtGroepBedragen, Integer> overzichtGroepBedragen) throws IOException, DocumentException, ParseException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();

        document.add(createTableHeader("Overview"));
        document.add(createOverviewExportedAmountsTable(overzichtGroepBedragen, getHeaderPageSize(overzichtGroepBedragen)));
        document.add(createTableHeader("Total: " + buildTotal(overzichtGroepBedragen)));

        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private ByteArrayOutputStream createPDF(List<OverzichtGroepBedragen> overzichtGroepBedragen) throws DocumentException, IOException, ParseException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfCopy copy = new PdfSmartCopy(document, byteArrayOutputStream);
        document.open();

        Map.Entry<byte[], Map<OverzichtGroepBedragen, Integer>> overzichtBedragen = generatePdfs(overzichtGroepBedragen);

        PdfReader header = new PdfReader(createHeader(overzichtBedragen.getValue()));
        copy.addDocument(header);
        header.close();

        PdfReader pdfs = new PdfReader(overzichtBedragen.getKey());
        copy.addDocument(pdfs);
        pdfs.close();

        copy.close();

        return byteArrayOutputStream;
    }

    private void buildMappedDocuments(List<OverzichtGroepBedragen> overzichtGroepBedragen){
        for(OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragen){
            List<DocumentenDaoPojo> documenten = documentenDao.fetchDocumentByBedrag(overzichtGroepBedrag.getPk_id());
            mappedDocuments.put(overzichtGroepBedrag.getPk_id(), documenten);
        }
    }

    public String buildPDFOveriew(List<OverzichtGroepBedragen> overzichtGroepBedragen) throws DocumentException, IOException, ParseException {
        buildMappedDocuments(overzichtGroepBedragen);

        byte[] encoded = Base64.getEncoder().encode(createPDF(overzichtGroepBedragen).toByteArray());
        return new String(encoded);
    }
}
