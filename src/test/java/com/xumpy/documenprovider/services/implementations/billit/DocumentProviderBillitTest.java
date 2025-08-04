package com.xumpy.documenprovider.services.implementations.billit;

import com.xumpy.Application;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-junit.properties")
public class DocumentProviderBillitTest {
    @Autowired DocumentenDaoImpl documentenDao;
    @Autowired DocumentProviderBillit documentProviderBillit;

    @Test
    public void testProcess(){
        DocumentenDaoPojo documentenDaoPojo = documentenDao.findById(2884).get();

        documentenDaoPojo.getBedrag().getInvoice().setVatNumber("NL000000000B45");

        documentProviderBillit.process(documentenDaoPojo);
    }
}