package ru.mail.timelimit.server.remote;

import java.io.IOException;
import ru.mail.timelimit.server.controller.session.ClientSession;

public class OneClientRequestResponceThreadGroup extends ThreadGroup
{
    public OneClientRequestResponceThreadGroup(ClientSession clientSession)
    {
        super("OneClientRequestResponceThreadGroup");
        this.clientSession = clientSession;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable)
    {
        interrupt();
        try
        {
            clientSession.close();
        }
        catch (IOException ex)
        {
            System.err.println("OneClientRequestResponceThreadGroup broken.");
            throw new RuntimeException(ex);
        }
    }

    private final ClientSession clientSession;
}
