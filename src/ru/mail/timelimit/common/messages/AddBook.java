package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddBook")
public class AddBook
{

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AddBook()
    {
    }
    
    public String getAnnotation() 
    {
        return annotation;
    }

    public String getAuthor() 
    {
        return author;
    }

    public int getBookId() 
    {
        return bookId;
    }

    public String getIsbn() 
    {
        return isbn;
    }

    public String getTitle() 
    {
        return title;
    }

    public AddBook(int bookId, String title, String author, String isbn, String annotation) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.annotation = annotation;
    }
    
    public static String toXml(AddBook addBook) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(AddBook.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(addBook, baos);
        return baos.toString();
    }
    
    public static AddBook fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(AddBook.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (AddBook) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String annotation;
}
