package ru.mail.timelimit.server.remote.initserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ru.mail.timelimit.server.controller.ServerController;

/* TODO: Threads with while(true) -> java.util.Timer or smt else */
public class DemonStartThread extends Thread
{
    public DemonStartThread(ServerController serverController) throws Exception
    {
        serverSocketPublicPort = new ServerSocket(PUBLIC_PORT);
        this.serverController = serverController;
    }
            
    @Override
    public void run() 
    {
        try
        {
            while(true) 
            {
                Socket clientSocket = serverSocketPublicPort.accept();
                System.out.println("Accepted socket");
                
                EstablishConnectionWithClientThread establishConnectionWithClientThread = new EstablishConnectionWithClientThread(clientSocket, serverController);
                establishConnectionWithClientThread.start();
            }
        }
        catch (Exception ex)
        {
            try
            {
                serverSocketPublicPort.close();
            } 
            catch (IOException exc)
            {
                System.err.println("DemonStartThread broken. Clients won't be able to connect from now on");
                // Die DemonStartThread
                throw new RuntimeException(exc);
            }
        }
    }
    
    private final ServerController serverController;
    private final ServerSocket serverSocketPublicPort;
    private static final int PUBLIC_PORT = 4444;    
}
