package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YukiBuilder {
    @Value( "${yuki.domain}" )
    private String domain;
    @Value( "${yuki.webserviceaccesskey}" )
    private String webserviceaccesskey;
    @Value( "${yuki.administrationguid}" )
    private String administrationguid;

    public YukiPojo build(){
        YukiPojo yukiPojo = new YukiPojo();
        yukiPojo.setDomain(domain);
        yukiPojo.setWebServiceAccessKey(webserviceaccesskey);
        yukiPojo.setAdministrationGUID(administrationguid);

        return yukiPojo;
    }
}
