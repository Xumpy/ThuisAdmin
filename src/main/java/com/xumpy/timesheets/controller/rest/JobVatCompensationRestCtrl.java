package com.xumpy.timesheets.controller.rest;

import com.itextpdf.text.DocumentException;
import com.xumpy.government.controllers.model.VatCompensationCtrlPojo;
import com.xumpy.government.dao.VatCompensationDao;
import com.xumpy.government.domain.VatCompensation;
import com.xumpy.timesheets.controller.model.*;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import com.xumpy.timesheets.domain.JobVatCompensation;
import com.xumpy.timesheets.services.vatcompensation.VatCompensationOverview;
import com.xumpy.timesheets.services.vatcompensation.VatCompensationPdfBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@Scope(value="session")
public class JobVatCompensationRestCtrl {
    @Autowired JobVatCompensationDaoImpl jobVatCompensation;
    @Autowired JobsDaoImpl jobsDao;
    @Autowired VatCompensationDao vatCompensationDao;
    @Autowired VatCompensationPdfBuilder vatCompensationPdfBuilder;
    @Autowired JobsGroupRestCtrl jobsGroupRestCtrl;
    @Autowired VatCompensationOverview vatCompensationOverview;

    private LocalDate lastDayOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(localDate.lengthOfMonth());
    }

    private byte[] convertBase64ImageToByte(String base64image){
        String base64;
        base64 = base64image.replace("data:image/jpeg;base64,","");
        base64 = base64.replace("data:image/png;base64,","");
        return Base64.getMimeDecoder().decode(base64);
    }


    @RequestMapping(value = "/json/fetchVatCompensations", method = RequestMethod.GET)
    public @ResponseBody List<VatCompensationCtrlPojo> fetchVatCompensations(){
        List<VatCompensationCtrlPojo> vatCompensationCtrlPojos = new ArrayList<>();
        for(VatCompensation vatCompensation: vatCompensationDao.findAll()){
            vatCompensationCtrlPojos.add(new VatCompensationCtrlPojo(vatCompensation));
        }

        return vatCompensationCtrlPojos;
    }

    @RequestMapping(value = "/json/fetchVatCompensationRequestHeader", method = RequestMethod.GET)
    public @ResponseBody VatCompOverviewRequestCtrlPojo fetchVatCompensationRequestHeader() {
        return new VatCompOverviewRequestCtrlPojo();
    }

    @RequestMapping(value = "/json/fetchVatCompensationsMonth", method = RequestMethod.POST)
    public @ResponseBody VatCompOverviewResponseCtrlPojo fetchVatCompensationsMonth(@RequestBody VatCompOverviewRequestCtrlPojo vatCompOverviewRequestCtrlPojo) throws ParseException {
        return vatCompensationOverview.fetchVatCompensationsMonth(vatCompOverviewRequestCtrlPojo);
    }

    @RequestMapping(value = "/json/job_vat_compensation", method = RequestMethod.POST)
    public @ResponseBody Overview  processJobJobVatCompensation(@RequestBody JobJobVatCompensationRestPojo jobJobVatCompensationRestPojo) throws ParseException {
        JobVatCompensationDaoPojo jobVatCompensationDaoPojo = new JobVatCompensationDaoPojo();
        jobVatCompensationDaoPojo.setAmount(jobJobVatCompensationRestPojo.getAmount());
        jobVatCompensationDaoPojo.setDocument(convertBase64ImageToByte(jobJobVatCompensationRestPojo.getImage()));
        if(jobJobVatCompensationRestPojo.getImage().contains("data:image/jpeg")){
            jobVatCompensationDaoPojo.setDocumentMime("image/jpeg");
        }
        if(jobJobVatCompensationRestPojo.getImage().contains("data:image/png")){
            jobVatCompensationDaoPojo.setDocumentMime("image/png");
        }
        jobVatCompensationDaoPojo.setJob(jobsDao.findById(jobJobVatCompensationRestPojo.getJobId()).get());
        jobVatCompensationDaoPojo.setVatCompensation(vatCompensationDao.findById(jobJobVatCompensationRestPojo.getJobVatCompensation().getPkId()).get());

        jobVatCompensation.save(jobVatCompensationDaoPojo);

        return jobsGroupRestCtrl.fetchOverviewMonth(jobsGroupRestCtrl.overview.getMonth());
    }

    @RequestMapping(value = "/json/delete_job_vat_compensation", method = RequestMethod.POST)
    public @ResponseBody Overview  deleteJobJobVatCompensation(@RequestBody JobVatCompensationCtrlPojo jobVatCompensationCtrlPojo) throws ParseException {
        jobVatCompensation.delete(new JobVatCompensationDaoPojo(jobVatCompensationCtrlPojo));

        return jobsGroupRestCtrl.fetchOverviewMonth(jobsGroupRestCtrl.overview.getMonth());
    }

    @RequestMapping(value="/json/fetch_vat_compensation_document/{documentId}", method = RequestMethod.GET)
    public @ResponseBody String fetchVatCompensationDocument(@PathVariable Integer documentId, HttpServletResponse response) throws IOException, SQLException{
        OutputStream out = response.getOutputStream();
        JobVatCompensation jobVatCompensationObj = jobVatCompensation.findById(documentId).get();

        response.setContentType(jobVatCompensationObj.getDocumentMime());
        response.setHeader("Content-Type", jobVatCompensationObj.getDocumentMime());
        response.setHeader("Content-Disposition", "inline;filename=\"" + jobVatCompensationObj.getPkId() + ".jpg\"");

        out.write(jobVatCompensationObj.getDocument());

        out.flush();
        out.close();

        return null;
    }

    @RequestMapping(value="/json/fetch_vat_compensation_pdf", method = RequestMethod.GET)
    public @ResponseBody String fetchVatCompensationPdf(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, HttpServletResponse response) throws IOException, SQLException, ParseException, DocumentException {
        OutputStream out = response.getOutputStream();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        Date localStartDate = Date.from(LocalDate.parse("01/" + startDate, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date localEndDate = Date.from(lastDayOfMonth(LocalDate.parse("01/" + endDate, formatter)).atStartOfDay(ZoneId.systemDefault()).toInstant());

        response.setHeader("Content-Type", "document/pdf");
        response.setHeader("Content-Disposition", "inline;filename=\"vatCompensationOverview.pdf\"");

        out.write(vatCompensationPdfBuilder.buildPdf(localStartDate, localEndDate));

        out.flush();
        out.close();

        return null;
    }
}
