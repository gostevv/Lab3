package ru.mail.timelimit.server.remote;

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
import ru.mail.timelimit.server.model.Model;

public class RequestProcessor 
{

    public RequestProcessor(Model model) throws ParserConfigurationException
    {
        this.model = model;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }
    
    void process(String xmlRequest) throws Exception
    {
        System.out.println("Process xmlRequest " + xmlRequest);
        Document xml = documentBuilder.parse(new ByteArrayInputStream(xmlRequest.getBytes()));
        Element xmlRootElement = xml.getDocumentElement();
        String requestName = xmlRootElement.getTagName();
        requstNameToRequestClass.get(requestName).process(xmlRequest);
    }

    private interface XmlRequestProcessor
    {
        public void process(String xmlResponce) throws Exception;
    }
    
    private class AddBookXmlRequestProcessor implements XmlRequestProcessor
    {

        @Override
        public void process(String xmlResponce) throws Exception
        {
            AddBook addBook = AddBook.fromXml(xmlResponce);
            model.addBook(addBook.getBookId(), addBook.getTitle(), addBook.getAuthor(), addBook.getIsbn(), addBook.getAnnotation());
        }
        
    }
    
    private class AddChapterXmlRequestProcessor implements XmlRequestProcessor
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
    
    private class DeleteBookXmlRequestProcessor implements XmlRequestProcessor
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
    
    private class DeleteChapterXmlRequestProcessor implements XmlRequestProcessor
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
    
    private class GetBookXmlRequestProcessor implements XmlRequestProcessor
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
    
    private class GetChapterXmlRequestProcessor implements XmlRequestProcessor
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
    
    private class UpdateBookXmlRequestProcessor implements XmlRequestProcessor
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
    
    private class UpdateChapterXmlRequestProcessor implements XmlRequestProcessor
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
    
    private final Model model;
    private final DocumentBuilder documentBuilder;
    private final Map<String, XmlRequestProcessor> requstNameToRequestClass = 
            new HashMap<String, XmlRequestProcessor>()
            {
                {
                    put("AddBook", new AddBookXmlRequestProcessor());
                    put("AddChapter", new AddChapterXmlRequestProcessor());
                    put("DeleteBook", new DeleteBookXmlRequestProcessor());
                    put("DeleteChapter", new DeleteChapterXmlRequestProcessor());
                    put("GetBook", new GetBookXmlRequestProcessor());
                    put("GetChapter", new GetChapterXmlRequestProcessor());
                    put("UpdateBook", new UpdateBookXmlRequestProcessor());
                    put("UpdateChapter", new UpdateChapterXmlRequestProcessor());
                }
            };
}

