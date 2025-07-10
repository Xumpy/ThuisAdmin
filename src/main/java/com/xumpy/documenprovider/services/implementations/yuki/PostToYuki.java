package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.services.implementations.yuki.model.UploadReponse;
import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClients;
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
        HttpClient httpclient = HttpClients.createDefault();

        String url = buildUrl(yukiPojo);
        System.out.println(url);

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new InputStreamEntity(yukiPojo.getFile()));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        UploadReponse uploadReponse = convertToUploadResponse(entity.getContent());

        System.out.println(uploadReponse.getMessage());

        return uploadReponse;
    }
}
