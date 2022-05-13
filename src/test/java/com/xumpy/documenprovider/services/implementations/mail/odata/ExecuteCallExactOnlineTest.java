package com.xumpy.documenprovider.services.implementations.mail.odata;

import com.xumpy.documenprovider.services.implementations.mail.odata.handler.ExactCookie;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.thuisadmin.services.model.BedragAccountingSrvPojo;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-junit.properties")
@Ignore
public class ExecuteCallExactOnlineTest {
    @Value( "${exact.user_agent}" )
    private String userAgent;
    @Value( "${exact.username}" )
    private String username;
    @Value( "${exact.password}" )
    private String password;
    @Value( "${exact.division}" )
    private String division;
    @Value( "${exact.base_url}" )
    private String baseUrl;

    @Spy ExactCookie exactCookie;
    @InjectMocks
    ExecuteCallExactOnline executeCall;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(executeCall, "userAgent", userAgent);
        ReflectionTestUtils.setField(executeCall, "username", username);
        ReflectionTestUtils.setField(executeCall, "password", password);
        ReflectionTestUtils.setField(executeCall, "division", division);
        ReflectionTestUtils.setField(executeCall, "baseUrl", baseUrl);

    }

    @Before
    public void mockCookie(){
        //exactCookie.setCookie("ASP.NET_SessionId=2eabxex1vt2xalmz2vvqb4x0; ExactOnlineClient=PX74eNhEuJDF13qwH8AZ42DhMuIsQIzvh4R/pPnhSKmQusJyeyZ2Sbss3ZFCQVyeSp5v6KmFGDTer4Iw/Et4kZWfe2h1m2EFD0TuPZJ28ZvN6tctXGgjdoepGYNDQNb4loZ6C+K2E1OL7TKjwyzJDi6oucPmPBiX5+upHxV/G5k=; ExactOnlineLogin=4D9F86D8BB76D7EC4FCD3F0C6EAA1F9A98B7378E6623F69651C10479BFF32EDAA6F0B6682D9E4D6187BC0B856EB4B350F7332C0FD124C5EB7736E27B33D0FCB4AD083CBF54692B291124B2F284B4112B6017A18629A123BF67612901B12973A25A1105E1EA1D0B066179DB528BCF9CA16D0117C862C9F9DC1F32E20D60F1A34867FB6CA1A0316E39960E39165FC63FD837A62F108EAE4023441233336FA075415006FF1E19D0099ADBC7B4129DC994148B25A84F9E7DB292AFC70C10C70CF148BA6C4EB001658D79490306D2AEF9805D1A482BCAEBCAFD29A3BFC410B78779DAF3543E22AD822B29C4202617A2D4B53C94F2DFF3FCCC930114EF90FDBA55C40CA4AB7D895BC1963251C7FBC4B25E56A78851380C6F9865924F526C4114E3BD1B9F98B947396E697F069736F1F8444A58D06213C01E53BDF7299F458B55AAC885CE2A7C554B0380269DC19CC4E725A4EE70147BCB02ABC75D5763CE9AA8B79B02146D1DCC3215FB39C9EAEA8F2C44B24B933A5C3313F522FB80C919C7885D086BD5242812E3E7C8DBD4FDBF9068A7FD751CAC3E8C1F5972D0503D2A314F714568BCD7B42EFFA148081A1E7DDC1FE74D29BF5035A3D6E20C57D1570F7279908CEF273758E9B353DB583C985B13A4DADD20302B26B7ABE975E03B554ACFB63882CD; ExactServer{b885a638-10df-40c2-9b3c-d0ae5f3193ff}=Division=922052; _evga_58a2=5d55d0362ef1908b.; EOL.TOTP.OKaFuN8QwkCbPNCuXzGT_w=yo4TypKEl_YZuaZ-W18kYuqVnnE08EnUfeEi1LrdT6jD-Y-V7ube-u2NtntidiZdSX99VZR1rqUOUaiJmbt52o0cYI-yTZLeAWgkRF9tWTZa0a8gFs0tWYC1zJjBpa543hXMoCWd-9zvgcJlL2QtLpgj3Q7pwD3UamdNqMHFKS_zuIiFi_OV6ss7WDdaEtrsum7_yS580WwsvIsD60BoksL-M1YfvnurpgDp9tNW85Ntx9P-_6ZdVtaXmamUq3FAiQe9dBEKyQJ60YCGkMhUFZrKVKzSTCorxjZICTEf7aM!");
        executeCall.setAuthKey("459362");
    }

    @Test
    public void testFetchDocumentsByPrice() throws PinNotValidException {
        BedragenSrvPojo bedragenSrvPojo = new BedragenSrvPojo();
        bedragenSrvPojo.setBedrag(new BigDecimal( 638.06));

        List<DPDocument> documentList = executeCall.fetchDocumentsByPrice(bedragenSrvPojo);
        for(DPDocument document: documentList){
            if (document.getFinancialTransactionEntryGuid() != null){
                for(BedragAccountingSrvPojo bedragAccounting : executeCall.getBedragAccounting(document.getFinancialTransactionEntryGuid(), bedragenSrvPojo)){
                    System.out.println(bedragAccounting);
                }
            }
        }
    }
}