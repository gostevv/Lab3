package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReceivePort")
public class ReceivePort
{
    
    public ReceivePort()
    {
        
    }
    
    public ReceivePort(int portId) 
    {
        this.portId = portId;
    }
    
    public int getPortId()
    {
        return portId;
    }
    
    public void setPortId(int portId)
    {
        this.portId = portId;
    }
    
    public static String toXml(ReceivePort receivePort) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(ReceivePort.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(receivePort, baos);
        return baos.toString();
    }
    
    public static ReceivePort fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(ReceivePort.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ReceivePort) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int portId;
}
