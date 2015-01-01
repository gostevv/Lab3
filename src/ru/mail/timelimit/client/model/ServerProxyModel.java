package ru.mail.timelimit.client.model;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import ru.mail.timelimit.common.messages.*;
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
    
    public void addBook(int bookId, String title, String author, String isbn, String annotation)
    {
        AddBook addBook = new AddBook(bookId, title, author, isbn, annotation);
        
        String xml = null;
        try
        {
            xml = AddBook.toXml(addBook);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
        }
        
        try
        {
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public void addChapter(int chapterId, int bookId, String title, String chapterText)
    {
        AddChapter addChapter = new AddChapter(chapterId, bookId, title, chapterText);
        
        String xml = null;
        try
        {
            xml = AddChapter.toXml(addChapter);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
        }
        
        try
        {
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(IOException exception)
        {
            throw new RuntimeException(exception);
        }
    } 
    
    public void updateBook(int bookId, String title, String author, String isbn, String annotation)
    {
        deleteBook(bookId);
        addBook(bookId, title, author, isbn, annotation);
    }

    public void updateChapter(int chapterId, int bookId, String title, String chapterText)
    {
        deleteChapter(chapterId);
        addChapter(chapterId, bookId, title, chapterText);
    } 
    
    public void deleteBook(int bookId)
    {
        DeleteBook deleteBook = new DeleteBook(bookId);
        
        String xml = null;
        try
        {
            xml = DeleteBook.toXml(deleteBook);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
        }
        
        try
        {
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }
    
    public void deleteChapter(int chapterId)
    {
        DeleteChapter deleteChapter = new DeleteChapter(chapterId);
        
        String xml = null;
        try
        {
            xml = DeleteChapter.toXml(deleteChapter);
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
            throw new RuntimeException(exception);
        }
    }
    
    public void getBook(int bookId)
    {
        GetBook getBook = GetBook.ofRequest(bookId);
        
        String xml = null;
        try
        {
            xml = GetBook.toXml(getBook);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
        }
        
        try
        {
            System.out.println(" GetBook " + xml);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
            System.out.println(" GetBook written ");
        }
        catch(IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }
    
    public void getChapter(int chapterId)
    {
        GetChapter getChapter = GetChapter.ofRequest(chapterId);
        
        String xml = null;
        try
        {
            xml = GetChapter.toXml(getChapter);
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
            throw new RuntimeException(exception);
        }
    }
    
    public void lockBook(int bookId) 
    {
        LockBook lockBook = new LockBook(bookId);
        
        String xml = null;
        try
        {
            xml = LockBook.toXml(lockBook);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
        }
        
        try
        {
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public void unlockBook(int bookId) 
    {
        UnlockBook unlockBook = new UnlockBook(bookId);
        
        String xml = null;
        try
        {
            xml = UnlockBook.toXml(unlockBook);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
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

    public void lockChapter(int chapterId) 
    {
        LockChapter lockChapter = new LockChapter(chapterId);
        
        String xml = null;
        try
        {
            xml = LockChapter.toXml(lockChapter);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
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

    public void unlockChapter(int chapterId) 
    {
        UnlockChapter unlockChapter = new UnlockChapter(chapterId);
        
        String xml = null;
        try
        {
            xml = UnlockChapter.toXml(unlockChapter);
        }
        catch (JAXBException exception)
        {
            throw new RuntimeException(exception);
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
    
    public void addListener(PropertyChangeListener propertyChangeListener)
    { 
        serverProxyModelLoopback.addListener(propertyChangeListener);
    }
    
    private ServerProxyModelLoopback serverProxyModelLoopback;
    private final Socket clientSocket;
    
    private static final Integer SERVER_PUBLIC_PORT = 4444;
}
