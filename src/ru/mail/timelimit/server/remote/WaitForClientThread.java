package ru.mail.timelimit.server.remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import ru.mail.timelimit.server.controller.session.ClientSession;

public class WaitForClientThread extends Thread
{
    
    public WaitForClientThread(ClientSession clientSession) throws IOException
    {
        this.clientSession = clientSession;
        serverSocket = new ServerSocket(clientSession.getPortId());
    }
    
    @Override
    public void run()
    {
        try 
        {
            Socket clientSocket = serverSocket.accept();
            
            PrivatePortClientThread privatePortClientThread = new PrivatePortClientThread(clientSocket, clientSession.getRequestProcessor());
            ClientResponceThread clientResponceTimerTask = new ClientResponceThread(clientSocket, clientSession.getMessageQueue());
            
            privatePortClientThread.start();
            
            Timer clientResponceThread = new Timer();
            clientResponceThread.schedule(clientResponceTimerTask, 0, 1);
        } 
        catch (IOException ex) 
        {
            throw new RuntimeException(ex);
        }
    }
    
    private final ServerSocket serverSocket;
    private final ClientSession clientSession;
}
