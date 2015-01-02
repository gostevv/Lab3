package ru.mail.timelimit.server.controller.session;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.bind.JAXBException;
import ru.mail.timelimit.common.messages.*;
import ru.mail.timelimit.server.controller.ServerController;
import ru.mail.timelimit.server.model.Model;
import ru.mail.timelimit.server.model.javabeans.Book;
import ru.mail.timelimit.server.model.javabeans.Chapter;
import ru.mail.timelimit.server.remote.OneClientRequestProcessor;
import ru.mail.timelimit.server.remote.WaitForClientThread;

public class ClientSession
{

    public static ClientSession open(Model model, int portId, ServerController controller) throws Exception
    {   
        ClientSession clientSession = new ClientSession(portId, controller);
        clientSession.serverSocket = new ServerSocket(portId);
        OneClientRequestProcessor requestProcessor = new OneClientRequestProcessor(model, clientSession);
        clientSession.setRequestProcessor(requestProcessor);
        WaitForClientThread waitForClientThread = new WaitForClientThread(clientSession);
        waitForClientThread.start();
        return clientSession;
    }
    
    public void close() throws IOException
    {
        System.out.println("Close client session on portid " + portId);
        controller.removeClient(this);
        serverSocket.close();
        clientSocket.close();
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
    
    public void sendErrorCallback(Exception exception) throws JAXBException
    {
        ErrorCallback errorCallback = new ErrorCallback(exception.getMessage());
        messageQueue.add(ErrorCallback.toXml(errorCallback));
    }
    
    public ConcurrentLinkedQueue<String> getMessageQueue()
    {
        return messageQueue;
    }
    
    public int getPortId()
    {
        return portId;
    }

    private ClientSession(int portId, ServerController controller)
    {
        System.out.println("Start new client session with port " + portId);
        this.portId = portId;
        this.controller = controller;
    }

    public OneClientRequestProcessor getRequestProcessor() 
    {
        return requestProcessor;
    }
    
    public ServerSocket getServerSocket() 
    {
        return serverSocket;
    }

    public void setRequestProcessor(OneClientRequestProcessor requestProcessor) 
    {
        this.requestProcessor = requestProcessor;
    }
    
    public void setClientSocket(Socket clientSocket) 
    {
        this.clientSocket = clientSocket;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final ClientSession other = (ClientSession) obj;
        if (this.portId != other.portId) 
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 53 * hash + this.portId;
        return hash;
    }
    
    private OneClientRequestProcessor requestProcessor;
    private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
    private final int portId;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final ServerController controller;
}
