package ru.mail.timelimit.server.controller.session;

import java.net.ServerSocket;
import ru.mail.timelimit.server.remote.PrivatePortClientThread;
import ru.mail.timelimit.server.remote.RequestProcessor;

public class ClientSession 
{
    public ClientSession(int portId, RequestProcessor reqiestProcessor) throws Exception
    {
        System.out.println("Open new port " + portId);
        this.portId = portId;
        this.serverSocket = new ServerSocket(portId);
        this. requestProcessor = reqiestProcessor;
        
        privatePortClientThread = new PrivatePortClientThread(serverSocket, requestProcessor);
        privatePortClientThread.start();
    }
       
    private final int portId;
    private final ServerSocket serverSocket;
    private final PrivatePortClientThread privatePortClientThread;
    private final RequestProcessor requestProcessor;
}
