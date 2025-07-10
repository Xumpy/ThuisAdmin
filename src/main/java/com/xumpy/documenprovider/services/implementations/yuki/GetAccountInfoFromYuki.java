package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.services.implementations.yuki.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GetAccountInfoFromYuki {

    @Autowired YukiBuilder yukiBuilder;

    private final String BASE_URL = "https://api.yukiworks.nl/ws/AccountingInfo.asmx";

    private String createURISessionId(){
        return BASE_URL + "/Authenticate?accessKey=" + yukiBuilder.build().getWebServiceAccessKey();
    }

    private String createURIArrayOfTransactions(String sessionId, Date startDate, Date endDate){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        YukiPojo yukiPojo = yukiBuilder.build();

        String paramSesssionId = "sessionID=" + sessionId;
        String paramAdministrationGUID = "administrationID=" + yukiPojo.getAdministrationGUID();
        String paramGLAccountCode = "GLAccountCode=";
        String paramStartDate = "StartDate=" + dateFormat.format(startDate);
        String paramEndDate = "EndDate=" + dateFormat.format(endDate);
        String paramFinancialMode = "financialMode=1";

        return BASE_URL + "/GetTransactionDetails?" + paramSesssionId
                + "&" + paramAdministrationGUID
                + "&" + paramGLAccountCode
                + "&" + paramStartDate
                + "&" + paramEndDate
                + "&" + paramFinancialMode;
    }

    private String createURITransactionDocument(String sessionId, String transactionId){
        YukiPojo yukiPojo = yukiBuilder.build();

        String paramSesssionId = "sessionID=" + sessionId;
        String paramAdministrationGUID = "administrationID=" + yukiPojo.getAdministrationGUID();
        String paramTransactionID = "transactionID=" + transactionId;

        return BASE_URL + "/GetTransactionDocument?" + paramSesssionId
                + "&" + paramAdministrationGUID
                + "&" + paramTransactionID;
    }


    private Object executeWebCall(Class object, String url) throws JAXBException, IOException {
        HttpClient httpclient = HttpClients.createDefault();

        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(object);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        return jaxbUnmarshaller.unmarshal(entity.getContent());
    }

    public String getYukiSession() throws JAXBException, IOException {
        SessionID sessionID = (SessionID) executeWebCall(SessionID.class, createURISessionId());

        return sessionID.getValue();
    }

    public ArrayOfTransactionInfo getTransactionInfo(String sessionId, Date startDate, Date endDate) throws JAXBException, IOException {
        ArrayOfTransactionInfo arrayOfTransactionInfo = (ArrayOfTransactionInfo) executeWebCall(ArrayOfTransactionInfo.class,
                createURIArrayOfTransactions(sessionId, startDate, endDate));

        return arrayOfTransactionInfo;
    }

    public TransactionDocument getTransactionDocument(String sessionId, String transactionId) throws JAXBException, IOException {
        TransactionDocument transactionDocument = (TransactionDocument) executeWebCall(TransactionDocument.class,
                createURITransactionDocument(sessionId, transactionId));

        return transactionDocument;
    }
}
