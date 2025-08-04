package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.services.implementations.yuki.model.UploadReponse;
import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PostToYuki {
    private String buildUrl(YukiPojo  yukiPojo) {
        String mainUrl = "https://api.yukiworks.be/docs/Upload.aspx";

        return mainUrl + "?WebServiceAccessKey=" + yukiPojo.getWebServiceAccessKey() +
                "&Domain=" + yukiPojo.getDomain() +
                "&Administration=" + yukiPojo.getAdministrationGUID() +
                "&FileName=" + yukiPojo.getFileName() +
                "&Folder="+ yukiPojo.getFolder().getOrdner() +
                "&ReponseType=XML";

    }

    public UploadReponse convertToUploadResponse(InputStream entityStream) throws JAXBException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(UploadReponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (UploadReponse) jaxbUnmarshaller.unmarshal(entityStream);
    }

    public UploadReponse post(YukiPojo yukiPojo) throws IOException, JAXBException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String url = buildUrl(yukiPojo);
        System.out.println(url);

        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new InputStreamEntity(yukiPojo.getFile(), ContentType.DEFAULT_BINARY));

        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        UploadReponse uploadReponse = convertToUploadResponse(entity.getContent());

        System.out.println(uploadReponse.getMessage());

        return uploadReponse;
    }
}
