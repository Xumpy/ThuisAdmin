package com.xumpy.documenprovider.services.implementations.yuki.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
