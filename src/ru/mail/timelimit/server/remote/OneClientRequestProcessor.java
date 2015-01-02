package ru.mail.timelimit.server.remote;

import ru.mail.timelimit.common.messages.AddBook;
import ru.mail.timelimit.common.messages.GetChapter;
import ru.mail.timelimit.common.messages.DeleteBook;
import ru.mail.timelimit.common.messages.GetBook;
import ru.mail.timelimit.common.messages.AddChapter;
import ru.mail.timelimit.common.messages.DeleteChapter;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.mail.timelimit.common.messages.*;
import ru.mail.timelimit.server.controller.session.ClientSession;
import ru.mail.timelimit.server.model.Model;

public class OneClientRequestProcessor 
{

    public OneClientRequestProcessor(Model model, ClientSession clientSession) throws ParserConfigurationException
    {
        this.model = model;
        this.clientSession = clientSession;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }
    
    public void process(String xmlRequest) throws Exception
    {
        System.out.println("Process xmlRequest " + xmlRequest);
        Document xml = documentBuilder.parse(new ByteArrayInputStream(xmlRequest.getBytes()));
        Element xmlRootElement = xml.getDocumentElement();
        String requestName = xmlRootElement.getTagName();
        requstNameToRequestClass.get(requestName).process(xmlRequest);
    }

    public void sendErrorCallback(Exception exception) throws JAXBException
    {
        clientSession.sendErrorCallback(exception);
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
            model.addBook(clientSession, addBook.getBookId(), addBook.getTitle(), addBook.getAuthor(), addBook.getIsbn(), addBook.getAnnotation());
        }
        
    }
    
    private class AddChapterXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            AddChapter addChapter = AddChapter.fromXml(xmlResponce);
            model.addChapter(clientSession, addChapter.getChapterId(), addChapter.getBookId(), addChapter.getTitle(), addChapter.getChapterText());
        }
    }
    
    private class DeleteBookXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            DeleteBook deleteBook = DeleteBook.fromXml(xmlResponce);
            model.deleteBook(clientSession, deleteBook.getBookId());
        }
    }
    
    private class DeleteChapterXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            DeleteChapter deleteChapter = DeleteChapter.fromXml(xmlResponce);
            model.deleteChapter(clientSession, deleteChapter.getChapterId());
        }
    }
    
    private class GetBookXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            GetBook getBook = GetBook.fromXml(xmlResponce);
            model.getBook(clientSession, getBook.getBookId());
        }
    }
    
    private class GetChapterXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            GetChapter getChapter = GetChapter.fromXml(xmlResponce);
            model.getChapter(clientSession, getChapter.getChapterId());
        }
    }
    
    private class LockChapterXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            LockChapter lockChapter = LockChapter.fromXml(xmlResponce);
            model.lockChapter(clientSession, lockChapter.getChapterId());
        }
    }
    
    private class UnlockChapterXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            UnlockChapter unlockChapter = UnlockChapter.fromXml(xmlResponce);
            model.unlockChapter(clientSession, unlockChapter.getChapterId());
        }
    }
    
    private class LockBookXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            LockBook lockBook = LockBook.fromXml(xmlResponce);
            model.lockBook(clientSession, lockBook.getBookId());
        }
    }
    
    private class UnlockBookXmlRequestProcessor implements XmlRequestProcessor
    {
        @Override
        public void process(String xmlResponce) throws Exception
        {
            UnlockBook unlockBook = UnlockBook.fromXml(xmlResponce);
            model.unlockBook(clientSession, unlockBook.getBookId());
        }
    }
    
    
    /*
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
    }*/
    
    private final Model model;
    private final ClientSession clientSession;
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
                    put("LockBook", new LockBookXmlRequestProcessor());
                    put("UnlockBook", new UnlockBookXmlRequestProcessor());
                    put("LockChapter", new LockChapterXmlRequestProcessor());
                    put("UnlockChapter", new UnlockChapterXmlRequestProcessor());
                    
                    /*put("UpdateBook", new UpdateBookXmlRequestProcessor());
                    put("UpdateChapter", new UpdateChapterXmlRequestProcessor());*/
                }
            };
}

