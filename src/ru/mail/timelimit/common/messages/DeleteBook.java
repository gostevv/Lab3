package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeleteBook")
public class DeleteBook
{

    public DeleteBook(int bookId) {
        this.bookId = bookId;
    }
    public DeleteBook()
    {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    
    public static String toXml(DeleteBook deleteBook) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(DeleteBook.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(deleteBook, baos);
        return baos.toString();
    }
    
    public static DeleteBook fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(DeleteBook.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (DeleteBook) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int bookId;
}
