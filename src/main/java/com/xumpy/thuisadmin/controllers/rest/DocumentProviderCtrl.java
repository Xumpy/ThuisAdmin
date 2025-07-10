package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDumpImpl;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDaoPojo;
import com.xumpy.documenprovider.dao.model.DocumentProviderDumpDaoPojo;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.thuisadmin.controllers.model.DocumentProviderCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.DocumentProviderDumpCtrlPojo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DocumentProviderCtrl {
    @Autowired
    DocumentProviderImpl documentProvider;
    @Autowired
    DocumentProviderDumpImpl documentProviderDump;

    @Autowired List<DocumentProviderSrv> documentProviderSrvs;

    @RequestMapping("/json/documentProviders")
    public @ResponseBody
    List<DocumentProviderCtrlPojo> getAllDocumentProviders() {
        List<DocumentProviderCtrlPojo> documentProviderCtrlPojos = new ArrayList<>();
        for (DocumentProviderDaoPojo documentProviderDaoPojo : documentProvider.findAll()) {
            documentProviderCtrlPojos.add(new DocumentProviderCtrlPojo(documentProviderDaoPojo));
        }

        return documentProviderCtrlPojos;
    }

    @RequestMapping("/json/documentProvider/{pkId}")
    public @ResponseBody DocumentProviderCtrlPojo getDocumentProvider(@PathVariable Integer pkId) {
        return new DocumentProviderCtrlPojo(documentProvider.findById(pkId).get());
    }

    @RequestMapping("/json/dataDumps/{documentProviderId}")
    public @ResponseBody
    List<DocumentProviderDumpCtrlPojo> getAllDocumentProviderDumps(@PathVariable Integer documentProviderId) {
        List<DocumentProviderDumpCtrlPojo> documentProviderDumpCtrlPojos = new ArrayList<>();

        for (DocumentProviderDumpDaoPojo documentProviderDumpDaoPojo : documentProviderDump.getDocumentProviderDumpsByProviderId(documentProviderId)) {
            documentProviderDumpCtrlPojos.add(new DocumentProviderDumpCtrlPojo(documentProviderDumpDaoPojo));
        }

        return documentProviderDumpCtrlPojos;
    }

    @RequestMapping("/json/dataDump/{pkId}")
    public @ResponseBody DocumentProviderDumpCtrlPojo getAllDocumentProviderDump(@PathVariable Integer pkId) {
        return new DocumentProviderDumpCtrlPojo(documentProviderDump.findById(pkId).get());
    }

    @RequestMapping("/json/uploadDumpFromWebservice")
    public @ResponseBody String uploadDumpFromWebservice(@RequestBody  DocumentProviderDumpCtrlPojo documentProviderDumpCtrlPojo){

        DocumentProviderDumpDaoPojo documentProviderDumpDaoPojo = new DocumentProviderDumpDaoPojo(documentProviderDumpCtrlPojo);

        for (DocumentProviderSrv documentProviderSrv: documentProviderSrvs){
            if (documentProviderSrv.getDocumentProviderId().equals(documentProviderDumpCtrlPojo.getDocumentProvider().getPkId())){
                documentProviderDumpDaoPojo.setDump(
                        documentProviderSrv.getDumpFromDocumentProvider(documentProviderDumpCtrlPojo.getStartDate(), documentProviderDumpCtrlPojo.getEndDate()));
            }
        }

        documentProviderDump.save(documentProviderDumpDaoPojo);

        return "200";
    }

    @RequestMapping("/json/deleteDataDump/{pkId}")
    public @ResponseBody String deleteDataDumps(@PathVariable Integer pkId){
        documentProviderDump.deleteById(pkId);

        return "200";
    }

    @RequestMapping("/json/downloadDataDump/{pkId}")
    public @ResponseBody String downloadDataDump(@PathVariable Integer pkId, HttpServletResponse response) throws IOException, SQLException {
        OutputStream out = response.getOutputStream();

        String datadumpAsString = documentProviderDump.findById(pkId).get().getDump();

        response.setContentType("application/text");
        response.setHeader("Content-Disposition", "inline;filename=\"datadump.txt\"");

        out.write(datadumpAsString.getBytes(StandardCharsets.UTF_8));

        out.flush();
        out.close();

        return null;
    }

    @RequestMapping("/json/processDataDump/{pkId}")
    public @ResponseBody String processDataDump(@PathVariable Integer pkId, HttpServletResponse response) throws IOException, SQLException {
        OutputStream out = response.getOutputStream();

        DocumentProviderDumpDaoPojo documentProviderDumpDaoPojo = documentProviderDump.findById(pkId).get();

        for (DocumentProviderSrv documentProviderSrv: documentProviderSrvs){
            if (documentProviderSrv.getDocumentProviderId().equals(documentProviderDumpDaoPojo.getDocumentProvider().getPkId())){
                String report = documentProviderSrv.processDumpToBedragAccounting(documentProviderDumpDaoPojo.getDump());

                response.setContentType("application/text");
                response.setHeader("Content-Disposition", "inline;filename=\"report.txt\"");

                out.write(report.getBytes(StandardCharsets.UTF_8));

                out.flush();
                out.close();
            }
        }

        return null;
    }

}
