package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.implementations.yuki.model.UploadReponse;
import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-junit.properties")
public class TestYukiUpload {
    @Mock PostToYuki postToYuki;
    @Mock UploadReponse uploadReponse;
    @InjectMocks YukiBuilder yukiBuilder;

    @Value("${yuki.domain}")
    public String domain;

    @Test
    public void testDomain(){
        Assert.assertEquals("test", domain);
    }

    @Before
    public void before() throws JAXBException, IOException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(postToYuki.convertToUploadResponse(Mockito.any())).thenCallRealMethod();
        Mockito.when(postToYuki.post(Mockito.any())).thenReturn(uploadReponse);

        Mockito.when(uploadReponse.getDocumentID()).thenReturn("b14a8850-165e-4afa-8a5a-9ed42f894537");
    }

    @Test
    public void testUpload() throws IOException, JAXBException {
        YukiPojo yukiPojo = yukiBuilder.build();

        yukiPojo.setFolder(Folder.ZelfTeOrdenen);
        yukiPojo.setFileName("tanken.pdf");
        yukiPojo.setFile(getClass().getResourceAsStream("/images/Tanken.pdf"));

        UploadReponse yukiReponse = postToYuki.post(yukiPojo);

        Assert.assertEquals("b14a8850-165e-4afa-8a5a-9ed42f894537", yukiReponse.getDocumentID());

    }

    @Test
    public void testMarshall() throws JAXBException {
        String marshall = "<UploadReponse><UploadSuccess>True</UploadSuccess><Message /><DocumentID>b14a8850-165e-4afa-8a5a-9ed42f894537</DocumentID></UploadReponse>";
        InputStream inputStream = new ByteArrayInputStream(marshall.getBytes());

        UploadReponse uploadReponseYuki = postToYuki.convertToUploadResponse(inputStream);

        Assert.assertEquals("b14a8850-165e-4afa-8a5a-9ed42f894537", uploadReponseYuki.getDocumentID());
    }
}
