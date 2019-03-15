package com.xumpy.timesheets.services.vatcompensation;

import com.itextpdf.text.DocumentException;
import com.xumpy.finances.services.ImageShrinker;
import com.xumpy.government.dao.model.VatCompensationDaoPojo;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class VatCompensationPdfBuilderTest {
    @Mock JobVatCompensationDaoImpl jobVatCompensationDao;
    @Mock ImageShrinker imageShrinker;
    @InjectMocks VatCompensationPdfBuilder vatCompensationPdfBuilder;

    private static Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    private VatCompensationDaoPojo generateVatComMock(){
        VatCompensationDaoPojo vatCompensation = new VatCompensationDaoPojo();

        vatCompensation.setUnitPrice(new BigDecimal("0.23"));
        vatCompensation.setName("Fietske");

        return vatCompensation;

    }

    private JobsDaoPojo generateJobMock(String date) throws ParseException {
        JobsDaoPojo jobsDaoPojo = new JobsDaoPojo();

        jobsDaoPojo.setJobDate(parseDate(date));

        return jobsDaoPojo;
    }

    private JobVatCompensationDaoPojo generateJobVatComMock(String date) throws ParseException, IOException {
        JobVatCompensationDaoPojo jobVatCompensationDaoPojo = new JobVatCompensationDaoPojo();
        jobVatCompensationDaoPojo.setVatCompensation(generateVatComMock());
        jobVatCompensationDaoPojo.setJob(generateJobMock(date));
        jobVatCompensationDaoPojo.setDocument(IOUtils.toByteArray(getClass().getResourceAsStream("/images/image.png")));
        jobVatCompensationDaoPojo.setDocumentMime("image/png");
        jobVatCompensationDaoPojo.setAmount(new BigDecimal("30"));

        return jobVatCompensationDaoPojo;
    }

    private List<JobVatCompensationDaoPojo> generateJobVatComListMock() throws ParseException, IOException {
        List<JobVatCompensationDaoPojo> jobVatCompensationDaoPojos = new ArrayList<>();
        for (int i=1; i<10; i++){
            jobVatCompensationDaoPojos.add(generateJobVatComMock("2019-01-0" + Integer.toString(i)));
        }
        return jobVatCompensationDaoPojos;
    }

    @Test
    public void testBuildPdf() throws ParseException, DocumentException, IOException {
        Date startDate = parseDate("2019-01-01");
        Date endDate = parseDate("2019-01-10");

        Mockito.when(jobVatCompensationDao.selectJobVatCompensations(startDate, endDate)).thenReturn(generateJobVatComListMock());

        byte[] pdf = vatCompensationPdfBuilder.buildPdf(startDate, endDate);
        byte[] pdfExpected = IOUtils.toByteArray(getClass().getResourceAsStream("/images/pdfke.pdf"));

    }
}
