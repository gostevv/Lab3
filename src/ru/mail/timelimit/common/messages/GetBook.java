package ru.mail.timelimit.common.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetBook")
public class GetBook
{
   
    public static GetBook ofRequest(int bookId) 
    {
        GetBook getBookClientRequest = new GetBook();
        getBookClientRequest.setBookId(bookId);
        return getBookClientRequest;
    }
    
    public static GetBook ofResponce(int bookId, String title, String author, String isbn, String annotation) 
    {
        GetBook getBookClientRequest = new GetBook();
        getBookClientRequest.setBookId(bookId);
        getBookClientRequest.setTitle(title);
        getBookClientRequest.setAuthor(author);
        getBookClientRequest.setIsbn(isbn);
        getBookClientRequest.setAnnotation(annotation);
        return getBookClientRequest;
    }
    
    public GetBook()
    {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public static String toXml(GetBook getBook) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(GetBook.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(getBook, baos);
        return baos.toString();
    }
    
    public static GetBook fromXml(String xml) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(GetBook.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (GetBook) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }
    
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String annotation;
}
