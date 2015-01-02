package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorCallback")
public class ErrorCallback 
{

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorCallback(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public ErrorCallback()
    {
        
    }
    
    public static String toXml(ErrorCallback errorCallback) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(ErrorCallback.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(errorCallback, baos);
        return baos.toString();
    }
    
    public static ErrorCallback fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(ErrorCallback.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ErrorCallback) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private String errorMessage;
}
