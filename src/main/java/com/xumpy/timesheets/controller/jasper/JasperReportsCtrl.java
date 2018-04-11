package com.xumpy.timesheets.controller.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

@Controller
public class JasperReportsCtrl {
    @Autowired DataSource dataSource;

    @RequestMapping(value = "jasper/invoice/{invoiceId}", method = RequestMethod.GET)
    @ResponseBody
    public void getInvoice(HttpServletResponse response, @PathVariable Integer invoiceId) throws JRException, IOException, SQLException {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("INVOICE_ID", invoiceId);

        generateReport(response, params, "Invoice_ " + invoiceId);
    }

    private void generateReport(HttpServletResponse response, Map<String, Object> params, String filename) throws JRException, IOException, SQLException {
        JasperReport jasperSubReport = (JasperReport) JRLoader.loadObject(ResourceUtils.getURL("classpath:jasperreports/Invoices_detail.jasper").openStream());
        params.put("SUBREPORT", jasperSubReport);
        params.put(JRParameter.REPORT_LOCALE, Locale.GERMANY);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ResourceUtils.getURL("classpath:jasperreports/Invoices.jasper").openStream());

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=" + filename + ".pdf");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
