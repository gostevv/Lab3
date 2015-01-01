package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetChapter")
public class GetChapter
{
    
    public static GetChapter ofRequest(int chapterId)
    {
        GetChapter getChapter = new GetChapter();
        getChapter.setChapterId(chapterId);
        return getChapter;
    }
    
    public static GetChapter ofResponce(int chapterId, int bookId, String title, String chapterText) 
    {
        GetChapter getChapter = new GetChapter();
        getChapter.setChapterId(chapterId);
        getChapter.setBookId(bookId);
        getChapter.setTitle(title);
        getChapter.setChapterText(chapterText);
        return getChapter;
    }
    
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public GetChapter()
    {
        
    }
    
    public static String toXml(GetChapter getChapter) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(GetChapter.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(getChapter, baos);
        return baos.toString();
    }
    
    public static GetChapter fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(GetChapter.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (GetChapter) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int chapterId;
    private int bookId;
    private String title;
    private String chapterText;
}
