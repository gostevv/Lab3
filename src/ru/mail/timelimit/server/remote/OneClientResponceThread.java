package ru.mail.timelimit.server.remote;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/* TODO: Threads with while(true) -> java.util.Timer or smt else. ThreadGroup implementation though... */
public class OneClientResponceThread extends Thread
{

    public OneClientResponceThread(OneClientRequestResponceThreadGroup group,
            Socket clientSocket, ConcurrentLinkedQueue<String> messageQueue)
    {
        super(group, "OneClientRequestThread");
        this.clientSocket = clientSocket;
        this.messageQueue = messageQueue;
    }
    
    @Override
    public void run() 
    {
        while(!this.isInterrupted())
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
                    System.err.println("ClientResponceThread broken.");
                    // close clientSession
                    throw new RuntimeException(ex);
                }
            }
            
            try
            {
                Thread.sleep(1);
            }
            catch (Exception ex)
            {
                System.err.println("ClientResponceThread broken.");
                // close clientSession
                throw new RuntimeException(ex);
            }
        }
    } 
    
    private final Socket clientSocket;
    private final ConcurrentLinkedQueue<String> messageQueue;
}
