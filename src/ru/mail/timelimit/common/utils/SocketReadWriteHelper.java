package ru.mail.timelimit.common.utils;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReadWriteHelper 
{
    public static String readString(DataInputStream dis, int stringLength) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = new byte[stringLength];
        while(true)
        {
            int symbolsRead = dis.read(bytes);
            stringBuilder.append(new String(bytes, 0, symbolsRead));
            //System.out.println(" SocketReadWriteHelper symbolsRead " + symbolsRead + " new String(bytes, 0, symbolsRead) " + new String(bytes, 0, symbolsRead));
            if (stringBuilder.length() == stringLength)
            {
                break;
            }
        }
        return stringBuilder.toString();
    }  
}
