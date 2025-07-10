package com.xumpy.documenprovider.services.implementations.yuki.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement(namespace = "http://www.theyukicompany.com/", name="string")
public class SessionID {
    private String value;

    @XmlValue
    public String getValue(){
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
