package com.xumpy.finances.controller;

import com.xumpy.finances.excelbuilder.ExcelZipBuilder;
import com.xumpy.finances.services.SendDocumentToYuki;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AccountController {
    @Autowired ExcelZipBuilder excelZipBuilder;
    @Autowired SendDocumentToYuki sendDocumentToYuki;

    @RequestMapping(value = "/accounting/accountingModel")
    public String viewAccountingModel(){
        return "finances/accountingModel";
    }

    private LocalDate lastDayOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(localDate.lengthOfMonth());
    }

    @RequestMapping(value="/json/generateAccountingZip", method = RequestMethod.GET)
    public @ResponseBody String generateAccounting(@RequestParam("startDate") String startDate,
                                                   @RequestParam("endDate") String endDate,
                                                   HttpServletResponse response) throws IOException{
        OutputStream out = response.getOutputStream();

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "inline;filename=\"accounting.zip\"");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate localStartDate = LocalDate.parse("01/" + startDate, formatter);
        LocalDate localEndDate  = lastDayOfMonth(LocalDate.parse("01/" + endDate, formatter));

        out.write(excelZipBuilder.buildZip(localStartDate, localEndDate).toByteArray());

        out.flush();
        out.close();

        return null;
    }

    @RequestMapping(value="/json/sendToYuki", method = RequestMethod.GET)
    public String sendToYuki(@RequestParam("documentId") Integer documentId) throws IOException, JAXBException {
        Documenten document = sendDocumentToYuki.send(documentId);

        return "redirect:/finances/nieuwBedrag/" + document.getBedrag().getPk_id();
    }
}
