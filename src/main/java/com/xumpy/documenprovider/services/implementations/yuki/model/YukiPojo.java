package com.xumpy.documenprovider.services.implementations.yuki.model;

import com.xumpy.documenprovider.model.Folder;

import java.io.InputStream;

public class YukiPojo {
    private String domain;
    private String webServiceAccessKey;
    private String administrationGUID;
    private Folder folder;
    private String fileName;
    private InputStream file;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWebServiceAccessKey() {
        return webServiceAccessKey;
    }

    public void setWebServiceAccessKey(String webServiceAccessKey) {
        this.webServiceAccessKey = webServiceAccessKey;
    }

    public String getAdministrationGUID() {
        return administrationGUID;
    }

    public void setAdministrationGUID(String administrationGUID) {
        this.administrationGUID = administrationGUID;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
