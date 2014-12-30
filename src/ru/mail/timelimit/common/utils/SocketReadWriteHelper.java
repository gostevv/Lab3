package ru.mail.timelimit.common.utils;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReadWriteHelper 
{
    public static String readString(DataInputStream dis, int stringLength) throws IOException
    {
        StringBuilder receivedPort = new StringBuilder();
        byte[] bytes = new byte[1024];
        while(true)
        {
            int symbolsRead = dis.read(bytes);
            receivedPort.append(new String(bytes, 0, symbolsRead));
            if (receivedPort.length() == stringLength)
            {
                break;
            }
        }
        return receivedPort.toString();
    }
    
}
