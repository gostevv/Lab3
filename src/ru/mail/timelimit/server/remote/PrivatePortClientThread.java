package ru.mail.timelimit.server.remote;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ru.mail.timelimit.common.utils.SocketReadWriteHelper;

public class PrivatePortClientThread extends Thread
{
    
    public PrivatePortClientThread (ServerSocket serverSocket, RequestProcessor requestProcessor) throws IOException
    {
        this.serverSocket = serverSocket;
        this.requestProcessor = requestProcessor;
    }
    
    @Override
    public void run() 
    {
        try
        {
            this.clientSocket = serverSocket.accept();
            
            while(true)
            {
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                int length = dis.readInt();

                System.out.println("message length " + length);

                String message = SocketReadWriteHelper.readString(dis, length);

                System.out.println("getMessage " + message);
                 
                requestProcessor.process(message);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    private Socket clientSocket;
    private final ServerSocket serverSocket;
    private final RequestProcessor requestProcessor;
}
