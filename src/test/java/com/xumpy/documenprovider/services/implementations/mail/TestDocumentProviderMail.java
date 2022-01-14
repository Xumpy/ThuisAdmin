package com.xumpy.documenprovider.services.implementations.mail;

import com.xumpy.documenprovider.model.Folder;
import com.xumpy.thuisadmin.controllers.model.BedragenCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.DocumentenCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.GroepenCtrlPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-junit.properties")
public class TestDocumentProviderMail {
    @Value( "${mail.verkoopEmail}" )
    private String verkoopEmail;
    @Value( "${mail.aankoopEmail}" )
    private String aankoopEmail;
    @Value( "${mail.smtpHost}" )
    private String smtpHost;
    @Value( "${mail.smtpPort}" )
    private Integer smtpPort;
    @Value( "${mail.username}" )
    private String username;
    @Value( "${mail.password}" )
    private String password;

    @Mock SmtpEmailBuilder smtpEmailBuilder;
    @InjectMocks DocumentProviderMail documentProviderMail;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(documentProviderMail, "verkoopEmail", verkoopEmail);
        ReflectionTestUtils.setField(documentProviderMail, "aankoopEmail", aankoopEmail);
        ReflectionTestUtils.setField(smtpEmailBuilder, "smtpHost", smtpHost);
        ReflectionTestUtils.setField(smtpEmailBuilder, "smtpPort", smtpPort);
        ReflectionTestUtils.setField(smtpEmailBuilder, "username", username);
        ReflectionTestUtils.setField(smtpEmailBuilder, "password", password);

    }

    private byte[] createDocumentAsByte() throws IOException {
        return IOUtils.toByteArray(TestDocumentProviderMail.class.getResourceAsStream("/images/Tanken.pdf"));
    }

    private GroepenCtrlPojo createGroep(){
        GroepenCtrlPojo groepenCtrlPojo = new GroepenCtrlPojo();

        groepenCtrlPojo.setCategory(Folder.Aankoop.getOrdner());

        return groepenCtrlPojo;
    }

    private BedragenCtrlPojo createBedrag(){
        BedragenCtrlPojo bedragenCtrlPojo = new BedragenCtrlPojo();

        bedragenCtrlPojo.setOmschrijving("Test Bedrag");
        bedragenCtrlPojo.setGroep(createGroep());

        return bedragenCtrlPojo;
    }

    private Documenten createDocument() throws IOException {
        DocumentenCtrlPojo documentenCtrlPojo = new DocumentenCtrlPojo();

        documentenCtrlPojo.setDocument_mime("application/pdf");
        documentenCtrlPojo.setDocument_naam("Tanken.pdf");
        documentenCtrlPojo.setBedrag(createBedrag());
        documentenCtrlPojo.setDocument(createDocumentAsByte());

        return documentenCtrlPojo;
    }

    @Test
    public void testProcess() throws IOException {
        String feedback = documentProviderMail.process(createDocument());

        Assert.assertEquals("Mail Sent To: aankoop@mail.com", feedback);
    }
}
