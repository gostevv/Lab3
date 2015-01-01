package ru.mail.timelimit.server.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.bind.JAXBException;
import ru.mail.timelimit.server.controller.session.ClientSession;
import ru.mail.timelimit.server.model.Model;
import ru.mail.timelimit.server.model.javabeans.Book;
import ru.mail.timelimit.server.model.javabeans.Chapter;
import ru.mail.timelimit.server.remote.initserver.DemonStartThread;
import ru.mail.timelimit.server.remote.RequestProcessor;

public class ServerController implements ModelObserver
{
    
    public ServerController(Model model) throws Exception 
    {
        this.model = model;
        model.addListener(this);
        DemonStartThread publicPortListenerThread = new DemonStartThread(this);
        publicPortListenerThread.start();
    }
    
    public void addClient(int portId) throws Exception
    {
        synchronized(clients)
        {
            ClientSession clientSession = ClientSession.startClientSession(model, portId);
            clients.add(clientSession);
        }
    }
    
    @Override
    public void receiveCommandFromModel(Command command, Object object, ClientSession originator) 
    {
        synchronized(clients)
        {
            try
            {
                if (Command.AddBook == command)
                {
                    Book book = (Book) object;
                    for (ClientSession client : clients)
                    {
                        client.sendAddBook(book);
                    }
                }
                else if (Command.AddChapter == command)
                {
                    Chapter chapter = (Chapter) object;
                    for (ClientSession client : clients)
                    {
                        client.sendAddChapter(chapter);
                    }
                }
                else if (Command.DeleteChapter == command)
                {
                    Chapter chapter = (Chapter) object;
                    for (ClientSession client : clients)
                    {
                        client.sendDeleteChapter(chapter);
                    }
                }
                else if (Command.DeleteBook == command)
                {
                    Book book = (Book) object;
                    for (ClientSession client : clients)
                    {
                        client.sendDeleteBook(book);
                    }
                }
                else if (Command.GetBook == command)
                {
                    Book book = (Book) object;
                    originator.sendGetBook(book);
                }
                else if (Command.GetChapter == command)
                {
                    Chapter chapter = (Chapter) object;
                    originator.sendGetChapter(chapter);
                }
            }
            catch(JAXBException exception)
            {
                throw new RuntimeException(exception);
            }
        }
    }
    
    private final Collection<ClientSession> clients = new LinkedList<>();
    private final Model model;
}
