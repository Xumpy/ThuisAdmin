package com.xumpy.documenprovider.services.implementations.yuki.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.theyukicompany.com/", name = "TransactionDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionDocument {
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "fileName")
    private String fileName;
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "filedata")
    private String filedata;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFiledata() {
        return filedata;
    }

    public void setFiledata(String filedata) {
        this.filedata = filedata;
    }
}
