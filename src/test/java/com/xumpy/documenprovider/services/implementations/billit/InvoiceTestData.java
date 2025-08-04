package com.xumpy.documenprovider.services.implementations.billit;

import com.helger.commons.base64.Base64;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoiceJobsDaoPojo;
import com.xumpy.thuisadmin.dao.model.InvoicesDaoPojo;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceTestData {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static JobsGroupDaoPojo createJobsGroupDaoPojo(){
        CompanyDaoPojo companyDaoPojo = new CompanyDaoPojo();
        companyDaoPojo.setName("Test Bedrijf");
        companyDaoPojo.setStreet("Postbus");
        companyDaoPojo.setNumber("1");
        companyDaoPojo.setCity("Pelt");
        companyDaoPojo.setPostalCode("3900");
        companyDaoPojo.setCountry("The Netherlands");
        companyDaoPojo.setVatNumber("NL000000000B45");
        companyDaoPojo.setDailyPayedHours(new BigDecimal(8));
        companyDaoPojo.setEmail("nico.martens1985@gmail.com");

        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo();
        jobsGroupDaoPojo.setName("123456");
        jobsGroupDaoPojo.setDescription("DevOps Engineer 3");
        jobsGroupDaoPojo.setCompany(companyDaoPojo);

        return jobsGroupDaoPojo;
    }

    private static InvoicesDaoPojo createInvoicesDaoPojo() {
        try {
            InvoicesDaoPojo invoicesDaoPojo = new InvoicesDaoPojo();
            invoicesDaoPojo.setPkId(1);
            invoicesDaoPojo.setInvoiceId("00001");
            invoicesDaoPojo.setInvoiceDate(simpleDateFormat.parse("2025-07-31"));
            invoicesDaoPojo.setInvoiceRef("INK00000001");
            invoicesDaoPojo.setInvoiceDueDate(simpleDateFormat.parse("2025-09-10"));
            invoicesDaoPojo.setVatNumber("NL32871039217");
            invoicesDaoPojo.setVatAmount(new BigDecimal(0));
            invoicesDaoPojo.setClosed(false);

            return invoicesDaoPojo;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static InvoiceJobsDaoPojo createInvoiceJobsDaoPojo(Date jobDate) {
        JobsGroupDaoPojo jobsGroupDaoPojo = createJobsGroupDaoPojo();

        JobsDaoPojo jobsDaoPojo = new JobsDaoPojo();
        jobsDaoPojo.setJobDate(jobDate);
        jobsDaoPojo.setJobsGroup(jobsGroupDaoPojo);
        jobsDaoPojo.setWorkedHours(new BigDecimal(8));

        InvoiceJobsDaoPojo invoiceJobsDaoPojo = new InvoiceJobsDaoPojo();
        invoiceJobsDaoPojo.setDescription(jobsGroupDaoPojo.getName() + ": " + jobsGroupDaoPojo.getDescription());
        invoiceJobsDaoPojo.setInvoice(InvoiceTestData.createDuchtInvoic());
        invoiceJobsDaoPojo.setAmount(new BigDecimal(20));
        invoiceJobsDaoPojo.setJob(jobsDaoPojo);
        invoiceJobsDaoPojo.setTimeUnitDays(false);

        return invoiceJobsDaoPojo;
    }

    public static List<InvoiceJobsDaoPojo> createInvoiceJobsDaoList() throws ParseException {
        List<InvoiceJobsDaoPojo> jobsByInvoice = new ArrayList<>();
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-08")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-09")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-10")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-11")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-14")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-15")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-16")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-17")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-18")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-21")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-22")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-23")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-24")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-25")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-28")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-29")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-30")));
        jobsByInvoice.add(createInvoiceJobsDaoPojo(simpleDateFormat.parse("2025-07-31")));

        return jobsByInvoice;
    }

    public static InvoicesDaoPojo createDuchtInvoic(){
        return createInvoicesDaoPojo();
    }

    public static DocumentenDaoPojo createDocument(InvoicesDaoPojo invoicesDaoPojo) throws IOException {
        BedragenDaoPojo bedragenDaoPojo = new BedragenDaoPojo();
        bedragenDaoPojo.setInvoice(invoicesDaoPojo);

        DocumentenDaoPojo documentenDaoPojo = new DocumentenDaoPojo();
        documentenDaoPojo.setDocument_mime("application/pdf");
        documentenDaoPojo.setDocument_naam("dummy_1.pdf");
        documentenDaoPojo.setBedrag(bedragenDaoPojo);
        documentenDaoPojo.setDocument(IOUtils.toByteArray(InvoiceTestData.class.getResourceAsStream("/dummy_1.pdf")));

        return documentenDaoPojo;
    }
}
