package ru.mail.timelimit.server;

import ru.mail.timelimit.server.controller.ServerController;
import ru.mail.timelimit.server.model.Model;
import ru.mail.timelimit.server.model.SimpleModel;

public class Main 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        model = new SimpleModel();
        serverController = new ServerController(model);
    }
    
    private static ServerController serverController;
    private static Model model;
}

