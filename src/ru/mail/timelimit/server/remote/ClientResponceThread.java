package ru.mail.timelimit.server.remote;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.TimerTask;
import ru.mail.timelimit.server.controller.session.ClientSession;

public class ClientResponceThread extends TimerTask
{

    public ClientResponceThread(Socket clientSocket, Queue<String> messageQueue)
    {
        this.clientSocket = clientSocket;
        this.messageQueue = messageQueue;
    }
    
    @Override
    public void run() 
    {
        while (!messageQueue.isEmpty())
        {
            String message = messageQueue.poll();
            System.out.println("vigo message " + message);
            try 
            {
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                dos.writeInt(message.length());
                dos.writeBytes(message);
            } 
            catch (IOException ex) 
            {
                throw new RuntimeException(ex);
            }
        }
    } 
    
    private final Socket clientSocket;
    private final Queue<String> messageQueue;
}
