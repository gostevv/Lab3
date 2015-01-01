package ru.mail.timelimit.server.controller.session;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import ru.mail.timelimit.common.messages.*;
import ru.mail.timelimit.server.model.Model;
import ru.mail.timelimit.server.model.javabeans.Book;
import ru.mail.timelimit.server.model.javabeans.Chapter;
import ru.mail.timelimit.server.remote.PrivatePortClientThread;
import ru.mail.timelimit.server.remote.RequestProcessor;
import ru.mail.timelimit.server.remote.WaitForClientThread;

public class ClientSession
{

    public static ClientSession startClientSession(Model model, int portId) throws Exception
    {
        ClientSession clientSession = new ClientSession(portId);
        RequestProcessor requestProcessor = new RequestProcessor(model, clientSession);
        clientSession.setRequestProcessor(requestProcessor);
        WaitForClientThread waitForClientThread = new WaitForClientThread(clientSession);
        waitForClientThread.start();
        return clientSession;
    }
    
    public void sendAddChapter(Chapter chapter) throws JAXBException 
    {
        AddChapter addChapter = new AddChapter();
        addChapter.setChapterId(chapter.getChapterId());
        addChapter.setBookId(chapter.getBook().getBookId());
        addChapter.setTitle(chapter.getTitle());
        addChapter.setChapterText(chapter.getChapterText());
        messageQueue.add(AddChapter.toXml(addChapter));
    }
    
    public void sendAddBook(Book book) throws JAXBException 
    {
        AddBook addBook = new AddBook();
        addBook.setAnnotation(book.getAnnotation());
        addBook.setAuthor(book.getAuthor());
        addBook.setBookId(book.getBookId());
        addBook.setIsbn(book.getIsbn());
        addBook.setTitle(book.getTitle());
        messageQueue.add(AddBook.toXml(addBook));
    }
    
    public void sendDeleteBook(Book book) throws JAXBException
    {
        DeleteBook deleteBook = new DeleteBook();
        deleteBook.setBookId(book.getBookId());
        messageQueue.add(DeleteBook.toXml(deleteBook));
    }
    
    public void sendDeleteChapter(Chapter chapter) throws JAXBException
    {
        DeleteChapter deleteChapter = new DeleteChapter();
        deleteChapter.setChapterId(chapter.getChapterId());
        messageQueue.add(DeleteChapter.toXml(deleteChapter));
    }
    
    public void sendGetChapter(Chapter chapter) throws JAXBException
    {
        GetChapter getChapter = GetChapter.ofResponce(chapter.getChapterId(), 
                chapter.getBook().getBookId(), chapter.getTitle(), chapter.getChapterText());
        messageQueue.add(GetChapter.toXml(getChapter));
    }
    
    public void sendGetBook(Book book) throws JAXBException
    {
        GetBook getBook = GetBook.ofResponce(book.getBookId(), book.getTitle(), 
                book.getAuthor(), book.getIsbn(), book.getAnnotation());
        messageQueue.add(GetBook.toXml(getBook));
    }
    
    public Queue<String> getMessageQueue()
    {
        return messageQueue;
    }
    
    public int getPortId()
    {
        return portId;
    }

    private ClientSession(int portId)
    {
        System.out.println("Start new client session with port " + portId);
        this.portId = portId;
    }

    public RequestProcessor getRequestProcessor() 
    {
        return requestProcessor;
    }

    public void setRequestProcessor(RequestProcessor requestProcessor) 
    {
        this.requestProcessor = requestProcessor;
    }
    
    private RequestProcessor requestProcessor;
    private final Queue<String> messageQueue = new ConcurrentLinkedQueue<String>();
    private final int portId;
}
