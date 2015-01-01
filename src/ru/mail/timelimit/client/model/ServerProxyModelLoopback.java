package ru.mail.timelimit.client.model;

import java.beans.PropertyChangeListener;
import java.net.Socket;
import javax.swing.event.SwingPropertyChangeSupport;
import ru.mail.timelimit.client.model.javabeans.Book;
import ru.mail.timelimit.client.model.javabeans.Chapter;
import ru.mail.timelimit.client.remote.AsynchronousResponceProcessor;
import ru.mail.timelimit.client.remote.AsynchronousResponseThread;

public class ServerProxyModelLoopback 
{
    ServerProxyModelLoopback(Socket socket) throws Exception
    {
        asynchronousResponceProcessor = new AsynchronousResponceProcessor(this);
        asynchronousResponceThread = new AsynchronousResponseThread(socket, asynchronousResponceProcessor);
        asynchronousResponceThread.start();
    }
    
    public void addListener(PropertyChangeListener propertyChangeListener)
    { 
        propertyChangeCaller = new SwingPropertyChangeSupport(this);
        propertyChangeCaller.addPropertyChangeListener(propertyChangeListener);
    }
    
    public void addBookLoopback(int bookId, String title, String author, String isbn, String annotation)
    {
        propertyChangeCaller.firePropertyChange("AddBook", null, new Book(bookId, title, author, isbn, annotation)); 
    }
    
    public void addChapterLoopback(int chapterId, int bookId, String title, String chapterText)
    {
        propertyChangeCaller.firePropertyChange("AddChapter", null, new Chapter(chapterId, bookId, title, chapterText)); 
    }
    
    public void deleteChapterLoopback(int chapterId)
    {
        propertyChangeCaller.firePropertyChange("DeleteChapter", new Integer(chapterId), null); 
    }
    
    public void deleteBookLoopback(int bookId)
    {
        propertyChangeCaller.firePropertyChange("DeleteBook", new Integer(bookId), null); 
    }
    
    public void getChapterLoopback(int chapterId, int bookId, String title, String chapterText)
    {
        propertyChangeCaller.firePropertyChange("GetChapter", new Chapter(chapterId, bookId, title, chapterText), null); 
    }
    
    public void getBookLoopback(int bookId, String title, String author, String isbn, String annotation)
    {
        propertyChangeCaller.firePropertyChange("GetBook", new Book(bookId, title, author, isbn, annotation), null); 
    }
    
    private SwingPropertyChangeSupport propertyChangeCaller = new SwingPropertyChangeSupport(this);
    private final AsynchronousResponseThread asynchronousResponceThread;
    private final AsynchronousResponceProcessor asynchronousResponceProcessor;
    
}
