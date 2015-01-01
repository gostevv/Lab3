package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddChapter")
public class AddChapter
{

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterText() {
        return chapterText;
    }

    public void setChapterText(String chapterText) {
        this.chapterText = chapterText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AddChapter(int chapterId, int bookId, String title, String chapterText) {
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.title = title;
        this.chapterText = chapterText;
    }
    
    public AddChapter()
    {
        
    }
    
    public static String toXml(AddChapter addChapter) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(AddChapter.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(addChapter, baos);
        return baos.toString();
    }
    
    public static AddChapter fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(AddChapter.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (AddChapter) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
            
    private int chapterId;
    private int bookId;
    private String title;
    private String chapterText;
}
