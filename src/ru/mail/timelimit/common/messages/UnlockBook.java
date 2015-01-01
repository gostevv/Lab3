package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UnlockBook")
public class UnlockBook
{
    public UnlockBook(int bookId) {
        this.bookId = bookId;
    }
    public UnlockBook()
    {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    
    public static String toXml(UnlockBook unlockBook) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(UnlockBook.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(unlockBook, baos);
        return baos.toString();
    }
    
    public static UnlockBook fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(UnlockBook.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (UnlockBook) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int bookId;
}
