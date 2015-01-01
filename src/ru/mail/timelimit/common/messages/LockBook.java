package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LockBook")
public class LockBook
{

    public LockBook(int bookId) {
        this.bookId = bookId;
    }
    public LockBook()
    {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    
    public static String toXml(LockBook lockBook) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(LockBook.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(lockBook, baos);
        return baos.toString();
    }
    
    public static LockBook fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(LockBook.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (LockBook) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int bookId;
}
