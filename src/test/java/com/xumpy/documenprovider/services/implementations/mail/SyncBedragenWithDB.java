package com.xumpy.documenprovider.services.implementations.mail;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.services.implementations.exceptions.ExactOnlineDocumentNotFound;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.services.implementations.mail.odata.ExecuteCallExactOnline;
import com.xumpy.documenprovider.services.implementations.mail.odata.handler.ExactCookie;
import com.xumpy.documenprovider.services.model.DocumentProviderDocumentsSrvPojo;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
public class SyncBedragenWithDB {

    @Autowired DocumentenDaoImpl documentenDao;
    @Autowired ExecuteCallExactOnline executeCallExactOnline;
    @Autowired ExactCookie exactCookie;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocuments;
    @Autowired DocumentProviderMail documentProviderMail;

    Map<String, String> securityKeys = new HashMap();
    public SyncBedragenWithDB(){
        securityKeys.put("cookie", "ExactOnlineClient=5Y4P1b7TxG3ditOfhfiqiU9/cii8rm5AQiQiLTu7J4T7yLml14JS1CXM0jr5aGLiUHrJpH3vBYbTE8j9x6iFXLQ5dntAnM8J54eVlrFQfmV1iKop3Ij7VEoHNM0rpSkSzaaz9Udzz9ZZgMjJfL3iQS40r5aL4Hle+x7LuLDrPIQ=; _evga_58a2=ea0d6a605c4f933d.0m8; ASP.NET_SessionId=jhu5rjwoguakwchrknmj1bmq; ExactServer{b885a638-10df-40c2-9b3c-d0ae5f3193ff}=Division=922052; EOL.TOTP.OKaFuN8QwkCbPNCuXzGT_w=L7ANXSvZrJcY3vMy66zotgKXPSv0wLM9HCxSqmMZulkTfNUfZpzVl7Eic6eaxaMB4qnMIAXd3W496sSgqADjzx11vOxrXbMUwu3Eg7adx35g5QH7Iwpz2lf6Zxnko9AW9xYhJ23nPm7ABuX7hyfa0dfPexLohF8nxdw86iCYGIQQPThaJ2R801d4q7QhJT8HF7GDKwo7vwTzssYxL2pz6nVXSljBsdo_QoPgAS21yyeLTFSmKXJkxP6lzd1t3mxuW9DR6enpplpjmCSAKQwT7Iym3dzafogFUBr09u0dXsQ!; ExactOnlineLogin=2A690CE3FB42A346C4D2CF936433227399E632191DEB8688D6863CAF659A42077311C4F05B89F4A8E2E4593A23CEED33CDF1B01EF6BD62E5976DB49A1743FF988ADE468F020E5071F4CA269E6264FADE7436AA7CD0CA376B40DD7E02A72718C7A2AC29EF291EEC86D5011E2CCB6D8A3A38135EC4C7AAC7AD93B52F9286D1BDA16BAE89B2A6C03F14C06234305F180D94FA56F0C288EC14A652C8554C70C5D321B9A765DA1A10C6C680002E1942FC52FD8A010E15E25F74DFBBAFC110397F26015CFD51E22020085315A7DA99AB204B5E989E6CB4891359EA5C405A0ECE1A4F5431E7DED95E6C29448B108F269407311689D3F4839FF3A254DF121B41C020DFAF15AF382D2EB10A2B0709A797A1CABEDE2C9B01814D78E89B98EAE1E08E95DE196F16E1043486E7B845193611B5D2559A7EB36DBB834CC9808850EF9E617CF0415E1A0A4764BC27B33C2DD340F2B11AF1E3AEA1C25DCA5C38341BC2FE8380A716C04B6611082EE6D5E8BAEA3AC6ACF7C9AD512730CCBFD95EC3FD02E93F033D468EFDD80EC5CE5F50C2DF54FE23EBAF7ADFCBBE893AB18F83DF51A18609933632D1939C117235203239B6BB9EFC07C2E9F940413D93BF2665763407F1C304C56DA0C9CDF648BE15CC2A90DCC3E7D4FB4FB10684EC0BFA032D2C9D7383E52AF6F4; _evgn_58a2={\"puid\":\"IuD-wNhNWgIi3G7moedZdLhUUURQjda9dnLQg1Ot9_KBUJijWNOC7kDTPHACgl2b\",\"paid\":\"ZpXA3L7VVuGjug-ZG8swvQzseEa_LEnnNY2RNGYrG9U\"}");
        securityKeys.put("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.25");
    }

    @Test
    @Ignore
    public void sync() {
        exactCookie.setCookie(securityKeys.get("cookie"));
        executeCallExactOnline.setUserAgent(securityKeys.get("userAgent"));

        List<DocumentProviderDocumentsDaoPojo> documentProviderDocumentsDaoPojos = documentProviderDocuments.getDocumentProviderDocumentsByFeedback("Mail Sent To");

        for(DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo: documentProviderDocumentsDaoPojos){
            Integer size = documentProviderDocumentsDaoPojo.getDocumenten().getDocument().length;
            BigDecimal amount = documentProviderDocumentsDaoPojo.getDocumenten().getBedrag().getBedrag();

            try {
                documentProviderDocumentsDaoPojo.setFeedback("Exact ID: " + executeCallExactOnline.findDocumentIdExactOnline(size, amount));
                documentProviderDocuments.save(documentProviderDocumentsDaoPojo);
            } catch (ExactOnlineDocumentNotFound e) {
                System.out.println("Document for bedrag with pk_id: "+ documentProviderDocumentsDaoPojo.getDocumenten().getBedrag().getPk_id() + " not found");
            }
        }
    }

    @Test
    public void updateAccountingBedragenExactOnline() throws PinNotValidException {
        List<DocumentProviderDocumentsDaoPojo> documentProviderDocumentsDaoPojos = documentProviderDocuments.getDocumentProviderDocumentsByFeedbackNoAccounting("Exact ID");

        for(DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo: documentProviderDocumentsDaoPojos){
            try{
                documentProviderMail.updateAccountingBedragen(new DocumentProviderDocumentsSrvPojo(documentProviderDocumentsDaoPojo), securityKeys);
            } catch(Exception exception){
                System.out.println("Document for bedrag with pk_id: "+ documentProviderDocumentsDaoPojo.getDocumenten().getBedrag().getPk_id() + " could not be updated with accounting info");
            }
        }
    }
}
