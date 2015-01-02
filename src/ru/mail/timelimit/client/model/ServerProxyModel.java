package ru.mail.timelimit.client.model;

import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
        serverProxyModelLoopback = ServerProxyModelLoopback.startListenToSocket(clientSocket);
    }
    
    public void addBook(int bookId, String title, String author, String isbn, String annotation)
    {
        /* TODO: Dozens of similar code below. Should be refactored, but i only have 2 hands after all */
        try
        {
            AddBook addBook = new AddBook(bookId, title, author, isbn, annotation);
            String xml = AddBook.toXml(addBook);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }

    public void addChapter(int chapterId, int bookId, String title, String chapterText)
    {
        try
        {
            AddChapter addChapter = new AddChapter(chapterId, bookId, title, chapterText);
            String xml = AddChapter.toXml(addChapter);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
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
        try
        {
            DeleteBook deleteBook = new DeleteBook(bookId);        
            String xml = DeleteBook.toXml(deleteBook);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }
    
    public void deleteChapter(int chapterId)
    {
        try
        {
            DeleteChapter deleteChapter = new DeleteChapter(chapterId);
            String xml = DeleteChapter.toXml(deleteChapter);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }
    
    public void getBook(int bookId)
    {
        try
        {
            GetBook getBook = GetBook.ofRequest(bookId);        
            String xml = GetBook.toXml(getBook);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }
    
    public void getChapter(int chapterId)
    {
        try
        {
            GetChapter getChapter = GetChapter.ofRequest(chapterId);
            String xml = GetChapter.toXml(getChapter);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }
    
    public void lockBook(int bookId) 
    {
        try
        {
            LockBook lockBook = new LockBook(bookId);
            String xml = LockBook.toXml(lockBook);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }

    public void unlockBook(int bookId) 
    {
        try
        {
            UnlockBook unlockBook = new UnlockBook(bookId);
            String xml = UnlockBook.toXml(unlockBook);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }

    public void lockChapter(int chapterId) 
    {
        try
        {
            LockChapter lockChapter = new LockChapter(chapterId);
            String xml = LockChapter.toXml(lockChapter);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
        }
    }

    public void unlockChapter(int chapterId) 
    {
        try
        {
            UnlockChapter unlockChapter = new UnlockChapter(chapterId);
            String xml = UnlockChapter.toXml(unlockChapter);
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(xml.length());
            das.writeBytes(xml);
        }
        catch(JAXBException | IOException exception)
        {
            // Unexpected exception. Here everything should be ok.
            throw new RuntimeException(exception);
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
