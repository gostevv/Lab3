package ru.mail.timelimit.client.model;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import ru.mail.timelimit.client.exceptions.BeanAlreadyExistsException;
import ru.mail.timelimit.client.exceptions.BeanNotFoundException;
import ru.mail.timelimit.client.model.javabeans.Book;
import ru.mail.timelimit.client.model.javabeans.Chapter;
import ru.mail.timelimit.common.messages.AddBook;
import ru.mail.timelimit.common.messages.GetPort;
import ru.mail.timelimit.common.messages.ReceivePort;
import ru.mail.timelimit.common.utils.SocketReadWriteHelper;

public class ServerProxyModel
{

    public ServerProxyModel() throws Exception
    {
        Socket socket = new Socket("localhost", SERVER_PUBLIC_PORT);

        GetPort getPort = new GetPort();
        String getPortRequest = GetPort.toXml(getPort);
        
        DataOutputStream das = new DataOutputStream(socket.getOutputStream());
        das.writeInt(getPortRequest.length());
        
        System.out.println(getPortRequest.length());
        
        das.writeBytes(getPortRequest);
        
        System.out.println(getPortRequest);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int length = dis.readInt();
        
        System.out.println("length " + length);
        
        String receivedPort = SocketReadWriteHelper.readString(dis, length);
        
        System.out.println("receivedPort " + receivedPort);
        
        ReceivePort port = ReceivePort.fromXml(receivedPort);
        
        System.out.println("Port received " + port.getPortId());
        
        clientSocket = new Socket("localhost", port.getPortId());
        serverProxyModelLoopback = new ServerProxyModelLoopback(clientSocket);
    }
    
    public void addBook(int bookId, String title, String author, String isbn, String annotation) throws BeanAlreadyExistsException
    {
        AddBook addBook = new AddBook(bookId, title, author, isbn, annotation);
        
        String xml = null;
        try
        {
            xml = AddBook.toXml(addBook);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException();
        }
        
        try
        {
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(IOException exception)
        {
            throw new RuntimeException();
        }
    }

    public void addChapter(int chapterId, int bookId, String title, String chapterText) throws BeanAlreadyExistsException, BeanNotFoundException
    {
        if (idToChapter.get(chapterId) != null)
        {
            throw new BeanAlreadyExistsException("Глава с Id " + chapterId + " уже существует");
        }
        
        if (idToBook.get(bookId) == null)
        {
            throw new BeanNotFoundException("Попытка вставить главу ссылающуюся на несуществующую книгу с Id " + bookId);
        }
        
        Chapter chapter = new Chapter(chapterId, idToBook.get(bookId), title, chapterText);
        idToChapter.put(chapterId, chapter);
        
        Book book = idToBook.get(bookId);
        Collection<Chapter> bookChapters = book.getChapters();
        bookChapters.add(chapter);
        book.setChapters(bookChapters);
        
        //propertyChangeCaller.firePropertyChange("AddChapter", null, chapter);
    } 
    
    public void updateBook(int bookId, String title, String author, String isbn, String annotation) throws BeanNotFoundException
    {
        deleteBook(bookId);
        try
        {
            addBook(bookId, title, author, isbn, annotation);
        }
        catch (BeanAlreadyExistsException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public void updateChapter(int chapterId, int bookId, String title, String chapterText) throws BeanNotFoundException
    {
        deleteChapter(chapterId);
        try
        {
            addChapter(chapterId, bookId, title, chapterText);
        }
        catch (BeanAlreadyExistsException exception)
        {
            throw new RuntimeException(exception);
        }
    } 
    
    public void deleteBook(int bookId) throws BeanNotFoundException
    {
        if (idToBook.get(bookId) == null)
        {
            throw new BeanNotFoundException("Книги с Id " + bookId + " не существует");
        }
        
        for (Chapter chapter : idToBook.get(bookId).getChapters())
        {
            deleteChapter(chapter.getChapterId()); 
        }
        
        Book book = idToBook.remove(bookId);
        
        //propertyChangeCaller.firePropertyChange("DeleteBook", book, null);
    }
    
    public void deleteChapter(int chapterId) throws BeanNotFoundException
    {
        if (idToChapter.get(chapterId) == null)
        {
            throw new BeanNotFoundException("Главы с Id " + chapterId + " не существует");
        }
        
        Chapter chapter = idToChapter.remove(chapterId);
        
        Book book = chapter.getBook();
        Collection<Chapter> bookChapters = book.getChapters();
        bookChapters.remove(chapter); 
        book.setChapters(bookChapters);
        
        //propertyChangeCaller.firePropertyChange("DeleteChapter", chapter, null);
    }
    
    public void addListener(PropertyChangeListener propertyChangeListener)
    { 
        serverProxyModelLoopback.addListener(propertyChangeListener);
    }
    
    public Book getBook(int bookId)
    {
        return idToBook.get(bookId);
    }
    
    public Chapter getChapter(int chapterId)
    {
        return idToChapter.get(chapterId);
    }
/*    
    public void destroyModel()
    {
        Collection <Integer> idsToDelete = new HashSet(idToBook.keySet());
        for (int bookId : idsToDelete)
        {
            try 
            {
                deleteBook(bookId);
            } 
            catch (BeanNotFoundException exception)
            {
                throw new RuntimeException(exception);
            }
        }
    }
    
    public void loadModel()
    {
        for (Book book : idToBook.values())
        {
            propertyChangeCaller.firePropertyChange("AddBook", null, book);
        }
        for (Chapter chapter : idToChapter.values())
        {
            propertyChangeCaller.firePropertyChange("AddChapter", null, chapter);
        }
    }*/
    
    public void lockBook(int bookId) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unlockBook(int bookId) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void lockChapter(int chapterId) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void unlockChapter(int chapterId) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private ServerProxyModelLoopback serverProxyModelLoopback;
    private Map<Integer, Book> idToBook = new HashMap<>();
    private Map<Integer, Chapter> idToChapter = new HashMap<>();
    private final Socket clientSocket;
    
    private static final Integer SERVER_PUBLIC_PORT = 4444;
}
