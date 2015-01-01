package ru.mail.timelimit.common.utils;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReadWriteHelper 
{
    public static synchronized String readString(DataInputStream dis, int stringLength) throws IOException
    {
        StringBuilder receivedPort = new StringBuilder();
        byte[] bytes = new byte[stringLength];
        while(true)
        {
            int symbolsRead = dis.read(bytes);
            receivedPort.append(new String(bytes, 0, symbolsRead));
            //System.out.println(" SocketReadWriteHelper symbolsRead " + symbolsRead + " new String(bytes, 0, symbolsRead) " + new String(bytes, 0, symbolsRead));
            if (receivedPort.length() == stringLength)
            {
                break;
            }
        }
        return receivedPort.toString();
    }  
}
