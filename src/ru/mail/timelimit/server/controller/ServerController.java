package ru.mail.timelimit.server.controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import ru.mail.timelimit.server.controller.session.ClientSession;
import ru.mail.timelimit.server.model.Model;
import ru.mail.timelimit.server.remote.PublicPortListenerThread;
import ru.mail.timelimit.server.remote.RequestProcessor;

public class ServerController 
{
    
    public ServerController(Model model) throws Exception 
    {
        this.model = model;
        
        PublicPortListenerThread publicPortListenerThread = new PublicPortListenerThread(this);
        publicPortListenerThread.start();
    }
    
    synchronized public void addClient(int portId) throws Exception
    {
        RequestProcessor requestProcessor = new RequestProcessor(model);
        ClientSession clientSession = new ClientSession(portId, requestProcessor);
        clients.add(clientSession);
    }
    
    private Collection<ClientSession> clients = new LinkedList<>();
    private Model model;
}
