package com.xumpy.documenprovider.services.implementations.yuki.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UploadReponse")
public class UploadReponse {
    @XmlElement(name = "UploadSuccess")
    private Boolean UploadSuccess;
    @XmlElement(name = "Message")
    private String Message;
    @XmlElement(name = "DocumentID")
    private String DocumentID;

    public Boolean getUploadSuccess() {
        return UploadSuccess;
    }

    public void setUploadSuccess(Boolean uploadSuccess) {
        UploadSuccess = uploadSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }
}
