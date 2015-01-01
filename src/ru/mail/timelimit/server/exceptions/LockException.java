package ru.mail.timelimit.server.exceptions;

public class LockException extends Exception
{
    
    public LockException(String exceptionMessage) 
    {
        super(exceptionMessage);
    }
    
}
