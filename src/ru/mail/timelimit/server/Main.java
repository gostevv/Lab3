package ru.mail.timelimit.server;

import ru.mail.timelimit.server.controller.ServerController;
import ru.mail.timelimit.server.model.Model;
import ru.mail.timelimit.server.model.SimpleModel;
import ru.mail.timelimit.server.remote.PublicPortListenerThread;

public class Main 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        model = new SimpleModel();
        model.addBook(1, "book title 1", "book author 1", "987654321", "ssa egu");
        model.addBook(2, "book title 2", "book author 2", "987654322", "ssa egu2");
        model.addChapter(1,1,"chapter title 1", "bla bla 1");
        model.addChapter(2,1,"chapter title 2", "bla bla 2");        
        model.addChapter(3,2,"chapter title 1", "bla bla 1");
        model.addChapter(4,2,"chapter title 2", "bla bla 2");
        serverController = new ServerController(model);
    }
    
    private static ServerController serverController;
    private static Model model;
}

