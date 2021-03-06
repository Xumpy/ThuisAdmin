package com.xumpy.finances.controller;

import com.xumpy.finances.excelbuilder.ExcelZipBuilder;
import com.xumpy.finances.excelbuilder.InvoicesToExcelModel;
import com.xumpy.finances.model.AccountingModel;
import com.xumpy.finances.model.AccountingModelTotal;
import com.xumpy.finances.model.BusinessCost;
import com.xumpy.finances.services.AccountingCalculationsSrv;
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

@Controller
public class AccountController {
    @Autowired AccountingCalculationsSrv accountingCalculationsSrv;
    @Autowired ExcelZipBuilder excelZipBuilder;
    @Autowired SendDocumentToYuki sendDocumentToYuki;

    @RequestMapping("/json/generateAccountingModel")
    public @ResponseBody AccountingModel generateAccountingModel(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                 @RequestBody BusinessCost simulatedBusinessCost){
        return accountingCalculationsSrv.generateAccountingModel(startDate, endDate, simulatedBusinessCost);
    }

    @RequestMapping(value = "/accounting/accountingModel")
    public String viewAccountingModel(){
        return "finances/accountingModel";
    }

    @RequestMapping(value = "/json/generateAccountingModelTotal")
    public @ResponseBody AccountingModelTotal getAccountingModelTotal(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                        @RequestBody BusinessCost simulatedBusinessCost){
        return accountingCalculationsSrv.getAccountingModelTotal(startDate, endDate, simulatedBusinessCost);
    }

    @RequestMapping(value="/json/generateAccountingZip", method = RequestMethod.GET)
    public @ResponseBody String generateAccounting(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                   HttpServletResponse response) throws IOException{
        OutputStream out = response.getOutputStream();

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "inline;filename=\"accounting.zip\"");

        out.write(excelZipBuilder.buildZip(startDate, endDate).toByteArray());

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
