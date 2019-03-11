package com.xumpy.timesheets.controller.rest;

import com.xumpy.government.dao.VatCompensationDao;
import com.xumpy.timesheets.controller.model.JobJobVatCompensationRestPojo;
import com.xumpy.timesheets.controller.model.JobVatCompensationCtrlPojo;
import com.xumpy.timesheets.controller.model.JobVatCompensationRestPojo;
import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import com.xumpy.timesheets.domain.JobVatCompensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Base64;

@Controller
@Scope(value="session")
public class JobVatCompensationRestCtrl {
    @Autowired JobVatCompensationDaoImpl jobVatCompensation;
    @Autowired JobsDaoImpl jobsDao;
    @Autowired VatCompensationDao vatCompensationDao;

    @Autowired JobsGroupRestCtrl jobsGroupRestCtrl;

    private byte[] convertBase64ImageToByte(String base64image){
        String base64 = base64image.replace("data:image/jpeg;base64,","");
        return Base64.getDecoder().decode(base64);
    }

    @RequestMapping(value = "/json/job_vat_compensation", method = RequestMethod.POST)
    public @ResponseBody Overview  processJobJobVatCompensation(@RequestBody JobJobVatCompensationRestPojo jobJobVatCompensationRestPojo) throws ParseException {
        JobVatCompensationDaoPojo jobVatCompensationDaoPojo = new JobVatCompensationDaoPojo();
        jobVatCompensationDaoPojo.setAmount(jobJobVatCompensationRestPojo.getAmount());
        jobVatCompensationDaoPojo.setDocument(convertBase64ImageToByte(jobJobVatCompensationRestPojo.getImage()));
        jobVatCompensationDaoPojo.setDocumentMime("/image/jpeg");
        jobVatCompensationDaoPojo.setJob(jobsDao.findById(jobJobVatCompensationRestPojo.getJobId()).get());
        jobVatCompensationDaoPojo.setVatCompensation(vatCompensationDao.findById(jobJobVatCompensationRestPojo.getJobVatCompensation().getPkId()).get());

        jobVatCompensation.save(jobVatCompensationDaoPojo);

        return jobsGroupRestCtrl.fetchOverview();
    }

    @RequestMapping(value = "/json/delete_job_vat_compensation", method = RequestMethod.POST)
    public @ResponseBody Overview  deleteJobJobVatCompensation(@RequestBody JobVatCompensationCtrlPojo jobVatCompensationCtrlPojo) throws ParseException {
        jobVatCompensation.delete(new JobVatCompensationDaoPojo(jobVatCompensationCtrlPojo));

        return jobsGroupRestCtrl.fetchOverview();
    }

    @RequestMapping(value="/json/fetch_vat_compensation_document/{documentId}", method = RequestMethod.GET)
    public @ResponseBody String fetchVatCompensationDocument(@PathVariable Integer documentId, HttpServletResponse response) throws IOException, SQLException{
        OutputStream out = response.getOutputStream();
        JobVatCompensation jobVatCompensationObj = jobVatCompensation.findById(documentId).get();

        response.setContentType(jobVatCompensationObj.getDocumentMime());
        response.setHeader("Content-Disposition", "inline;filename=\"" + jobVatCompensationObj.getPkId() + ".jpg\"");

        out.write(jobVatCompensationObj.getDocument());

        out.flush();
        out.close();

        return null;
    }
}
