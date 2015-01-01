package ru.mail.timelimit.server.remote;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ru.mail.timelimit.common.utils.SocketReadWriteHelper;
import ru.mail.timelimit.server.controller.session.ClientSession;

public class PrivatePortClientThread extends Thread
{
    
    public PrivatePortClientThread (Socket clientSocket, RequestProcessor requestProcessor) throws IOException
    {
        this.clientSocket = clientSocket;
        this.requestProcessor = requestProcessor;
    }
    
    @Override
    public void run() 
    {
        try
        {
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
    
    private final Socket clientSocket;
    private final RequestProcessor requestProcessor;
}
