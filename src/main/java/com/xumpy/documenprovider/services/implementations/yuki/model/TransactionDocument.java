package com.xumpy.documenprovider.services.implementations.yuki.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
