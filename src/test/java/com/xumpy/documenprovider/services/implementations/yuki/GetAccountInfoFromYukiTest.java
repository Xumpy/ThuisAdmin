package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.services.implementations.yuki.model.ArrayOfTransactionInfo;
import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-junit.properties")
@Ignore
public class GetAccountInfoFromYukiTest {
    @Mock YukiBuilder yukiBuilder;
    @InjectMocks GetAccountInfoFromYuki getAccountInfoFromYuki;

    @Value("${yuki.domain}")
    public String domain;

    @Value("${yuki.webserviceaccesskey}")
    public String webserviceaccesskey;

    @Value("${yuki.administrationguid}")
    public String administrationguid;

    private YukiPojo yukiPojo = new YukiPojo();

    @Before
    public void setup(){
        yukiPojo.setDomain(domain);
        yukiPojo.setWebServiceAccessKey(webserviceaccesskey);
        yukiPojo.setAdministrationGUID(administrationguid);

        Mockito.when(yukiBuilder.build()).thenReturn(yukiPojo);
    }

    @Test
    public void getYukiSessionTest() throws JAXBException, IOException {
        getAccountInfoFromYuki.getYukiSession();
    }

    @Test
    public void getTransactionInfoTest() throws JAXBException, IOException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String sessionID = getAccountInfoFromYuki.getYukiSession();
        Date startDate = dateFormat.parse("01-01-2020");
        Date endDate = dateFormat.parse("31-12-2020");

        String arrayOfTransactionInfoAsString = getAccountInfoFromYuki.getTransactionInfo(sessionID, startDate, endDate).toString();
        ArrayOfTransactionInfo arrayOfTransactionInfo = new ArrayOfTransactionInfo(arrayOfTransactionInfoAsString);
    }
}