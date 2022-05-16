package com.xumpy.documenprovider.services.implementations.mail.odata;

import com.xumpy.documenprovider.services.implementations.mail.odata.handler.ExactCookie;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.services.model.BedragAccountingSrvPojo;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ExecuteCallExactOnline {
    private String userAgent;
    @Value( "${exact.username}" )
    private String username;
    @Value( "${exact.password}" )
    private String password;
    @Value( "${exact.division}" )
    private String division;
    @Value( "${exact.base_url}" )
    private String baseUrl;

    private String authKey;

    @Autowired private ExactCookie exactCookie;

    public void setAuthKey(String authKey){
        this.authKey = authKey;
    }

    public void setUserAgent(String userAgent){
        this.userAgent = userAgent;
    }

    private HttpHeaders buildHttpHeaders(Boolean refreshCookie) throws PinNotValidException {
        //if (refreshCookie) exactCookie.fetchCookie(baseUrl, userAgent,username, password, authKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", exactCookie.getCookie());
        headers.set("User-Agent", userAgent);
        headers.set("Accept", "application/json");

        return headers;
    }

    private Map executeCall(String url) throws PinNotValidException {
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<HashMap<String, Object>> responseType =
                new ParameterizedTypeReference<HashMap<String, Object>>() {};
        HttpEntity<String> entity = new HttpEntity<>(null, buildHttpHeaders(false));

        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType).getBody();
    }

    private String urlBuilder(String path, String filter, String select){
        return baseUrl + "api/v1/" + division + "/" + path + "?$filter=" + filter + "&$select=" + select;
    }

    private List<Map<String, Object>> getResults(Map returnValue){
        Map<String, List> d = (Map<String, List>) returnValue.get("d");
        return d.get("results");
    }

    private Date extractDate(String date){
        String dateValue = date.substring(6, date.length() - 2);

        return new Date(Long.parseLong(dateValue));
    }

    public List<DPDocument> fetchDocumentsByPrice(Bedragen bedragen) throws PinNotValidException {
        List<DPDocument> documents = new ArrayList<>();
        String url = urlBuilder("documents/Documents", "AmountFC eq " + bedragen.getBedrag().setScale(2, RoundingMode.HALF_UP).toString(), "ID, Subject, DocumentDate, AmountFC, FinancialTransactionEntryID");
        for(Map<String, Object> result: getResults(executeCall(url))){
            DPDocument document = new DPDocument();

            document.setGuid(result.get("ID").toString());
            document.setFinancialTransactionEntryGuid(result.get("FinancialTransactionEntryID") != null ? result.get("FinancialTransactionEntryID").toString() : null);
            document.setDescription(result.get("Subject").toString());
            document.setDate(extractDate(result.get("DocumentDate").toString()));
            document.setAmount(new BigDecimal(result.get("AmountFC").toString()));

            documents.add(document);
        }

        return documents;
    }

    public List<BedragAccountingSrvPojo> getBedragAccounting(String documentsGuid, BedragenSrvPojo bedragenSrvPojo) throws PinNotValidException {
        String url = urlBuilder("financialtransaction/TransactionLines", "EntryID eq guid'" + documentsGuid + "'", "YourRef");
        String yourRef = ((Map) getResults(executeCall(url)).get(0)).get("YourRef").toString();

        List<BedragAccountingSrvPojo> bedragAccountingSrvPojos = new ArrayList<>();
        url = urlBuilder("financialtransaction/TransactionLines", "YourRef eq '" + yourRef + "'", "GLAccountCode,AccountName,Created,DocumentSubject,AmountFC,EntryID,VATCode,VATCodeDescription");
        for(Map<String, Object> result: getResults(executeCall(url))){
            BedragAccountingSrvPojo bedragAccounting = new BedragAccountingSrvPojo();
            bedragAccounting.setBedrag(bedragenSrvPojo);
            bedragAccounting.setAccountBedrag(new BigDecimal(result.get("AmountFC").toString()));
            bedragAccounting.setAccountCode(result.get("GLAccountCode").toString());
            bedragAccounting.setDatum(extractDate(result.get("Created").toString()));
            bedragAccounting.setOmschrijving(result.get("DocumentSubject") != null ? result.get("DocumentSubject").toString() : null);
            bedragAccounting.setCustomerName(result.get("AccountName").toString());
            bedragAccounting.setTaxDescription(result.get("VATCodeDescription") != null ? result.get("VATCodeDescription").toString() : null);
            bedragAccounting.setVatNumber(result.get("VATCode") != null ? result.get("VATCode").toString() : null);

            bedragAccountingSrvPojos.add(bedragAccounting);
        }
        return bedragAccountingSrvPojos;
    }
}
