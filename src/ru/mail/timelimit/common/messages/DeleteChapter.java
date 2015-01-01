package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeleteChapter")
public class DeleteChapter
{

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public DeleteChapter(int chapterId) {
        this.chapterId = chapterId;
    }
    
    public DeleteChapter()
    {
        
    }
    
    public static String toXml(DeleteChapter deleteChapter) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(DeleteChapter.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(deleteChapter, baos);
        return baos.toString();
    }
    
    public static DeleteChapter fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(DeleteChapter.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (DeleteChapter) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    private int chapterId;
}
