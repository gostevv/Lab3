package ru.mail.timelimit.server.exceptions;

public class BeanAlreadyExistsException extends Exception
{

    public BeanAlreadyExistsException(String exceptionMessage) 
    {
        super(exceptionMessage);
    }
    
}
