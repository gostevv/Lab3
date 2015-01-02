package ru.mail.timelimit.server.remote.initserver;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import ru.mail.timelimit.common.messages.GetPort;
import ru.mail.timelimit.common.messages.ReceivePort;
import ru.mail.timelimit.server.controller.ServerController;

public class EstablishConnectionWithClientThread extends Thread
{
    
    public EstablishConnectionWithClientThread (Socket clientSocket, ServerController controller)
    {
        this.clientSocket = clientSocket;
        this.controller = controller;
    }
    
    @Override
    public void run() 
    {
        try
        {
            System.out.println("start");
            
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            int length = dis.readInt();
            
            System.out.println("message length " + length);

            StringBuilder getPortMessage = new StringBuilder();
            byte[] bytes = new byte[1024];
            while(true)
            {
                int symbolsRead = dis.read(bytes);
                getPortMessage.append(new String(bytes, 0, symbolsRead));
                if (getPortMessage.length() == length)
                {
                    break;
                }
            }
            
            System.out.println("getPortMessage " + getPortMessage.toString());
            
            JAXBContext getPortContext = JAXBContext.newInstance(GetPort.class);
            Unmarshaller unmarshaller = getPortContext.createUnmarshaller();
            GetPort port = (GetPort) unmarshaller.unmarshal(
                    new ByteArrayInputStream(getPortMessage.toString().getBytes()));
            
            
            /* TODO: Replace dummy get port strategy. (15000 + rnd.nextInt(50000)) */
            ReceivePort sendPort = new ReceivePort(15000 + rnd.nextInt(50000));
            controller.addClient(sendPort.getPortId());
            
            JAXBContext receivePortContext = JAXBContext.newInstance(ReceivePort.class);
            Marshaller marshaller = receivePortContext.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(sendPort, baos);
            String sendPortRequest = baos.toString();

            System.out.println("sendPortRequest " + sendPortRequest);
            
            DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
            das.writeInt(sendPortRequest.length());
            das.writeBytes(sendPortRequest);
            
            System.out.println("Send port to client");
        }
        catch (Exception ex)
        {
            System.err.println("SendPortToClientThread broken.");
            // Die SendPortToClientThread
            throw new RuntimeException(ex);
        }
        
    }
    
    private final ServerController controller;    
    private final Random rnd = new Random();
    private final Socket clientSocket;
}
