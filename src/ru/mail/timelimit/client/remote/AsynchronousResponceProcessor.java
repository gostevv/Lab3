package ru.mail.timelimit.client.remote;

import ru.mail.timelimit.common.messages.AddBook;
import ru.mail.timelimit.common.messages.GetChapter;
import ru.mail.timelimit.common.messages.DeleteBook;
import ru.mail.timelimit.common.messages.GetBook;
import ru.mail.timelimit.common.messages.UpdateChapter;
import ru.mail.timelimit.common.messages.UpdateBook;
import ru.mail.timelimit.common.messages.AddChapter;
import ru.mail.timelimit.common.messages.DeleteChapter;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.mail.timelimit.client.model.ServerProxyModelLoopback;

public class AsynchronousResponceProcessor 
{

    public AsynchronousResponceProcessor(ServerProxyModelLoopback model) throws ParserConfigurationException
    {
        this.model = model;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }
    
    void process(String xmlResponce) throws Exception
    {
        Document xml = documentBuilder.parse(new ByteArrayInputStream(xmlResponce.getBytes()));
        Element xmlRootElement = xml.getDocumentElement();
        String requestName = xmlRootElement.getTagName();
        requstNameToRequestClass.get(requestName).process(xmlResponce);
    }

    private interface XmlResponceProcessor
    {
        public void process(String xmlResponce) throws Exception;
    }
    
    private class AddBookXmlResponceProcessor implements XmlResponceProcessor
    {

        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(AddBook.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            AddBook addBook = (AddBook) unmarshaller.unmarshal(bais);
            model.addBookLoopback(addBook.getBookId(), addBook.getTitle(), addBook.getAuthor(), addBook.getIsbn(), addBook.getAnnotation());
        }
        
    }
    
    private class AddChapterXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(AddChapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            AddChapter cs = (AddChapter) unmarshaller.unmarshal(bais);
        }
    }
    
    private class DeleteBookXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(DeleteBook.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DeleteBook cs = (DeleteBook) unmarshaller.unmarshal(bais);
        }
    }
    
    private class DeleteChapterXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(DeleteChapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DeleteChapter cs = (DeleteChapter) unmarshaller.unmarshal(bais);
        }
    }
    
    private class GetBookXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(GetBook.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GetBook cs = (GetBook) unmarshaller.unmarshal(bais);
        }
    }
    
    private class GetChapterXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(GetChapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GetChapter cs = (GetChapter) unmarshaller.unmarshal(bais);
        }
    }
    
    private class UpdateBookXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(UpdateBook.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            UpdateBook cs = (UpdateBook) unmarshaller.unmarshal(bais);
        }
    }
    
    private class UpdateChapterXmlResponceProcessor implements XmlResponceProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponce.getBytes());
            JAXBContext context = JAXBContext.newInstance(UpdateChapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            UpdateChapter cs = (UpdateChapter) unmarshaller.unmarshal(bais);
        }
    }
    
    private final ServerProxyModelLoopback model;
    private final DocumentBuilder documentBuilder;
    private final Map<String, XmlResponceProcessor> requstNameToRequestClass = 
            new HashMap<String, XmlResponceProcessor>()
            {
                {
                    put("AddBook", new AddBookXmlResponceProcessor());
                    put("AddChapter", new AddChapterXmlResponceProcessor());
                    put("DeleteBook", new DeleteBookXmlResponceProcessor());
                    put("DeleteChapter", new DeleteChapterXmlResponceProcessor());
                    put("GetBook", new GetBookXmlResponceProcessor());
                    put("GetChapter", new GetChapterXmlResponceProcessor());
                    put("UpdateBook", new UpdateBookXmlResponceProcessor());
                    put("UpdateChapter", new UpdateChapterXmlResponceProcessor());
                }
            };
}

