package com.xumpy.timesheets.controller.jasper;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.IOException;
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
        params.put("SUBREPORT", "classpath:jasperreports/Invoices_detail.jasper");
        params.put("LOGO", "classpath:jasperreports/NMConsultancyLogo.png");
        params.put(JRParameter.REPORT_LOCALE, Locale.GERMANY);
        DefaultJasperReportsContext.getInstance().setProperty(JRPdfExporter.PDF_PRODUCER_FACTORY_PROPERTY, "com.jaspersoft.jasperreports.export.pdf.modern.ModernPdfProducerFactory");

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ResourceUtils.getURL("classpath:jasperreports/Invoices.jasper").openStream());

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=" + filename + ".pdf");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
