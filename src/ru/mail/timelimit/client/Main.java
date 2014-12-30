package ru.mail.timelimit.client;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import ru.mail.timelimit.client.controller.Controller;
import ru.mail.timelimit.client.controller.ClientController;
import ru.mail.timelimit.client.model.ServerProxyModel;
import ru.mail.timelimit.client.view.SimpleView;
import ru.mail.timelimit.client.view.View;

public class Main 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, InvocationTargetException
    {
        try
        {
            model = new ServerProxyModel();
        }
        catch(Exception ex)
        {
            System.err.println("Unable to start program.");
            System.err.println(ex);
            throw new RuntimeException();
        }
        SwingUtilities.invokeAndWait(new Runnable() 
        {
            @Override
            public void run() 
            {
                view = new SimpleView(); // this piece of ... it doesn't compile without it.
            }
        });
        controller = new ClientController(view, model);
    }
    
    private static View view;
    private static Controller controller;
    private static ServerProxyModel model;
}

