package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LockChapter")
public class LockChapter
{
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public LockChapter(int chapterId) {
        this.chapterId = chapterId;
    }
    
    public LockChapter()
    {
        
    }
    
    public static String toXml(LockChapter lockChapter) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(LockChapter.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(lockChapter, baos);
        return baos.toString();
    }
    
    public static LockChapter fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(LockChapter.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (LockChapter) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int chapterId;
}
