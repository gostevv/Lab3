package ru.mail.timelimit.server.controller;

public enum Command 
{
    AddChapter("AddChapter"),
    AddBook("AddBook"),
    DeleteChapter("DeleteChapter"),
    DeleteBook("DeleteBook"),
    GetBook("GetBook"),
    GetChapter("GetChapter");
    
    private Command(String command)
    {
        this.command = command;
    }
    
    private final String command;
}
