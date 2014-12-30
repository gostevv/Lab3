package ru.mail.timelimit.client.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AsynchronousResponseThread extends Thread
{
    public AsynchronousResponseThread(Socket socket, AsynchronousResponceProcessor processor)
    {
        this.socket = socket;
        this.processor = processor;
        BufferedReader tempBr = null;
        try
        {
            tempBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException ex)
        {
            System.out.println(ex);
            ///////////////////////////////////////TODO
        }
        bufferedReader = tempBr;
    }
            
    @Override
    public void run() 
    {
        try
        {
            while(true)
            {
                char[] buffer = new char[1024];
                StringBuilder xmlResponce = new StringBuilder();
                int readSimbols;
                while((readSimbols = bufferedReader.read(buffer)) != -1)
                {
                    xmlResponce.append(new String(buffer, 0, readSimbols));
                }
                processor.process(xmlResponce.toString());
            }
        }
        catch (Exception ex)
        {
            try
            {
                bufferedReader.close();
                socket.close();
            } 
            catch (IOException exc)
            {
                throw new RuntimeException(exc);
            }
        }
    }
    
    private final Socket socket;
    private final BufferedReader bufferedReader;
    private final AsynchronousResponceProcessor processor;
}
