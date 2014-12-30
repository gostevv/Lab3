package ru.mail.timelimit.client.model;

import java.beans.PropertyChangeListener;
import java.net.Socket;
import javax.swing.event.SwingPropertyChangeSupport;
import ru.mail.timelimit.client.model.javabeans.Book;
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
    
    private SwingPropertyChangeSupport propertyChangeCaller = new SwingPropertyChangeSupport(this);
    private final AsynchronousResponseThread asynchronousResponceThread;
    private final AsynchronousResponceProcessor asynchronousResponceProcessor;
    
}
