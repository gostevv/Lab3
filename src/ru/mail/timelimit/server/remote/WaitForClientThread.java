package ru.mail.timelimit.server.remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ru.mail.timelimit.server.controller.session.ClientSession;

public class WaitForClientThread extends Thread
{
    
    public WaitForClientThread(ClientSession clientSession) throws IOException
    {
        this.clientSession = clientSession;
        serverSocket = clientSession.getServerSocket();
    }
    
    @Override
    public void run()
    {
        try 
        {
            Socket clientSocket = serverSocket.accept();
            clientSession.setClientSocket(clientSocket);
            
            OneClientRequestResponceThreadGroup oneClientRequestResponceThreadGroup = 
                    new OneClientRequestResponceThreadGroup(clientSession);
            
            OneClientResponceThread oneClientResponceThread = 
                    new OneClientResponceThread(oneClientRequestResponceThreadGroup, clientSocket, clientSession.getMessageQueue());
            oneClientResponceThread.start();
            
            OneClientRequestThread oneClientRequestThread = 
                    new OneClientRequestThread(oneClientRequestResponceThreadGroup, clientSocket, clientSession.getRequestProcessor());
            oneClientRequestThread.start();
        } 
        catch (IOException ex) 
        {
            // Die WaitForClientThread
            throw new RuntimeException(ex);
        }
    }
    
    
    
    private final ServerSocket serverSocket;
    private final ClientSession clientSession;
}
