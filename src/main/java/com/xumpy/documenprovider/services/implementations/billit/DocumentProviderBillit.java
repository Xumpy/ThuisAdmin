package com.xumpy.documenprovider.services.implementations.billit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.thuisadmin.domain.Documenten;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DocumentProviderBillit implements DocumentProviderSrv {

    @Autowired BillitBuilder billitBuilder;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocuments;

    @Value( "${billit.url}" ) private String url;
    @Value( "${billit.token}" ) private String token;

    @Override
    public String getDocumentProviderId() {
        return "BILLIT";
    }

    private CloseableHttpResponse sendDocumentToBillit(Map<String, Object> payload) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);

        System.out.println("json: " + json);

        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        post.setHeader("apiKey", token);

        return client.execute(post);
    }

    @Override
    public String process(Documenten document) {
        if (document.getBedrag().getInvoice() == null){
            throw new RuntimeException("No invoice assigned to bedrag in document");
        }

        Map<String, Object> payload = billitBuilder.createInvoice(document);

        try {
            CloseableHttpResponse response = sendDocumentToBillit(payload);
            String billitInvoiceId = IOUtils.toString(response.getEntity().getContent());

            System.out.println(billitInvoiceId);

            return "Billit ID: " + billitInvoiceId;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean shouldBeProcessed(Documenten document) {
        Boolean isDocumentAlreadyProcessed = documentProviderDocuments.getDocumentProviderDocumentsByDocumentIdAndDocumentProviderId(document.getPk_id(), getDocumentProviderId()).isEmpty();
        Boolean isInvoice = (document.getBedrag().getInvoice() != null);

        return isDocumentAlreadyProcessed && isInvoice;
    }
}
