package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetPort")
public class GetPort 
{    
    public GetPort()
    {
    }
    
    public static String toXml(GetPort getPort) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(GetPort.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(getPort, baos);
        return baos.toString();
    }
    
    public static GetPort fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(GetPort.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (GetPort) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
}
