package ru.mail.timelimit.server.remote;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.xml.bind.JAXBException;
import ru.mail.timelimit.common.utils.SocketReadWriteHelper;

/* TODO: Threads with while(true) -> java.util.Timer or smt else. ThreadGroup implementation though... */
public class OneClientRequestThread extends Thread
{
    
    public OneClientRequestThread(OneClientRequestResponceThreadGroup group, 
            Socket clientSocket, OneClientRequestProcessor requestProcessor) throws IOException
    {
        super(group, "OneClientRequestThread");
        this.clientSocket = clientSocket;
        this.requestProcessor = requestProcessor;
    }
    
    @Override
    public void run() 
    {
        while(!this.isInterrupted())
        {
            try
            {
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                int length = dis.readInt();

                System.out.println("message length " + length);

                String message = SocketReadWriteHelper.readString(dis, length);

                System.out.println("getMessage " + message);

                try
                {
                    requestProcessor.process(message);
                }
                catch(Exception exception)
                {
                    System.out.println("Send exception to client " + exception);
                    requestProcessor.sendErrorCallback(exception);
                }
            }
            catch (IOException | JAXBException ex)
            {
                System.err.println(ex);
                // close clientSession
                throw new RuntimeException(ex);
            }
        }
    }
    
    private final Socket clientSocket;
    private final OneClientRequestProcessor requestProcessor;
}
