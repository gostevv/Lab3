package ru.mail.timelimit.client.remote;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import ru.mail.timelimit.common.utils.SocketReadWriteHelper;

/* TODO: Threads with while(true) -> java.util.Timer or smt else */
public class AsynchronousResponseThread extends Thread
{
    public AsynchronousResponseThread(Socket socket, AsynchronousResponceProcessor processor)
    {
        this.socket = socket;
        this.processor = processor;
        DataInputStream temp = null;
        try
        {
            temp = new DataInputStream(socket.getInputStream());
        }
        catch(IOException ex)
        {
            throw new RuntimeException(ex);
        }
        dis = temp;
    }
            
    @Override
    public void run() 
    {
        try
        {
            while(true)
            {
                int length = dis.readInt();

                String xmlResponce = SocketReadWriteHelper.readString(dis, length);
            
                processor.process(xmlResponce);
            }
        }
        catch (Exception ex)
        {
            try
            {
                dis.close();
                socket.close();
            } 
            catch (IOException exc)
            {
                throw new RuntimeException(exc);
            }
        }
    }
    
    private final Socket socket;
    private final DataInputStream dis;
    private final AsynchronousResponceProcessor processor;
}
