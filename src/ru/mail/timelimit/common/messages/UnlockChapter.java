package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UnlockChapter")
public class UnlockChapter
{
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public UnlockChapter(int chapterId) {
        this.chapterId = chapterId;
    }
    
    public UnlockChapter()
    {
        
    }
    
    public static String toXml(UnlockChapter unlockChapter) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(UnlockChapter.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(unlockChapter, baos);
        return baos.toString();
    }
    
    public static UnlockChapter fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(UnlockChapter.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (UnlockChapter) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int chapterId;
}
