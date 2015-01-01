package ru.mail.timelimit.server.model;

import java.beans.PropertyChangeListener;
import ru.mail.timelimit.server.controller.ModelObserver;
import ru.mail.timelimit.server.controller.session.ClientSession;
import ru.mail.timelimit.server.exceptions.*;
import ru.mail.timelimit.server.model.javabeans.*;

public interface Model
{
    public void addBook(ClientSession originator, int bookId, String title, String author, String isbn, String annotation) throws BeanAlreadyExistsException;
    public void addChapter(ClientSession originator, int chapterId, int bookId, String title, String chapterText) throws BeanAlreadyExistsException, BeanNotFoundException;        
    public void updateBook(ClientSession originator, int bookId, String title, String author, String isbn, String annotation) throws BeanNotFoundException, LockException;
    public void updateChapter(ClientSession originator, int chapterId, int bookId, String title, String chapterText) throws BeanNotFoundException, LockException;
    public void deleteBook(ClientSession originator, int bookId) throws BeanNotFoundException, LockException;
    public void deleteChapter(ClientSession originator, int chapterId) throws BeanNotFoundException, LockException;
    public void lockBook(ClientSession originator, int bookId) throws LockException;
    public void unlockBook(ClientSession originator, int bookId) throws LockException;
    public void lockChapter(ClientSession originator, int chapterId) throws LockException;
    public void unlockChapter(ClientSession originator, int chapterId) throws LockException;
    
    public void getBook(ClientSession originator, int bookId) throws BeanNotFoundException;
    public void getChapter(ClientSession originator, int chapterId) throws BeanNotFoundException;
    
    public void addListener(ModelObserver modelObserver);
}
