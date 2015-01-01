package ru.mail.timelimit.server.controller;

import ru.mail.timelimit.server.controller.session.ClientSession;

public interface ModelObserver 
{
    public void receiveCommandFromModel(Command command, Object object, ClientSession originator);
}
