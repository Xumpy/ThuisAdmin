package com.xumpy.documenprovider.services.implementations.yuki.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@XmlRootElement(namespace = "http://www.theyukicompany.com/", name = "ArrayOfTransactionInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrayOfTransactionInfo {
    @XmlElement(namespace = "http://www.theyukicompany.com/", name = "TransactionInfo") private List<TransactionInfo> transactionInfo;

    public List<TransactionInfo> getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(List<TransactionInfo> transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public String toString(){
        try {
            JAXBContext context =  JAXBContext.newInstance(getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter out = new StringWriter();
            marshaller.marshal(this, out);

            return out.toString();

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayOfTransactionInfo(){}

    public ArrayOfTransactionInfo(String arrayOfTransactionInfo){
        try {
            JAXBContext context =  JAXBContext.newInstance(getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.transactionInfo = ((ArrayOfTransactionInfo) unmarshaller.unmarshal(new StringReader(arrayOfTransactionInfo))).getTransactionInfo();

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
