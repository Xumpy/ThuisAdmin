package com.xumpy.documenprovider.services.implementations.yuki;


import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderDumpImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDumpDaoPojo;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Ignore
public class DocumentProviderYukiTest {
    @Autowired DocumentProviderDumpImpl documentProviderDump;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocuments;
    @Autowired BedragAccountingDaoImpl bedragAccountingDao;

    @InjectMocks DocumentProviderYuki documentProviderYuki;

    @Test
    public void processDumpTest(){
        documentProviderYuki.setDaoWiring(documentProviderDocuments, bedragAccountingDao);

        DocumentProviderDumpDaoPojo documentProviderDumpDaoPojo = documentProviderDump.findById(4).get();

        String report = documentProviderYuki.processDumpToBedragAccounting(documentProviderDumpDaoPojo.getDump());

        System.out.println(report);
    }
}