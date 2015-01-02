package ru.mail.timelimit.server.model;

import java.beans.PropertyChangeListener;
import java.util.*;
import javax.swing.event.SwingPropertyChangeSupport;
import ru.mail.timelimit.server.controller.Command;
import ru.mail.timelimit.server.controller.ModelObserver;
import ru.mail.timelimit.server.controller.session.ClientSession;
import ru.mail.timelimit.server.model.javabeans.*;
import ru.mail.timelimit.server.exceptions.*;

public class SimpleModel implements Model
{

    @Override
    public synchronized void addBook(ClientSession originator, int bookId, String title, String author, String isbn, String annotation) throws BeanAlreadyExistsException
    {
        if (idToBook.get(bookId) != null)
        {
            throw new BeanAlreadyExistsException("Book with Id " + bookId + " already exists.");
        }

        Book book = new Book(bookId, title, author, isbn, annotation);
        idToBook.put(bookId, book);
        modelObserver.receiveCommandFromModel(Command.AddBook, book, originator);
    }

    @Override
    public synchronized void addChapter(ClientSession originator, int chapterId, int bookId, String title, String chapterText) throws BeanAlreadyExistsException, BeanNotFoundException
    {
        if (idToChapter.get(chapterId) != null)
        {
            throw new BeanAlreadyExistsException("Chapter with Id " + chapterId + " already exists.");
        }

        if (idToBook.get(bookId) == null)
        {
            throw new BeanNotFoundException("An attempt to insert a chapter which references to a book which doesn't exist. Book Id - " + bookId);
        }

        Chapter chapter = new Chapter(chapterId, idToBook.get(bookId), title, chapterText);
        idToChapter.put(chapterId, chapter);

        Book book = idToBook.get(bookId);
        Collection<Chapter> bookChapters = book.getChapters();
        bookChapters.add(chapter);
        book.setChapters(bookChapters);
        modelObserver.receiveCommandFromModel(Command.AddChapter, chapter, originator);
    }
    
    @Override 
    public synchronized void deleteBook(ClientSession originator, int bookId) throws BeanNotFoundException, LockException
    {
        if (idToBook.get(bookId) == null)
        {
            throw new BeanNotFoundException("Book with Id " + bookId + " doesn't exist.");
        }

        if ((clientSessionToLockedBooks.get(originator) != bookId)
            && clientSessionToLockedBooks.values().contains(bookId))
        {
            throw new LockException("Book is locked");
        }
        
        for (Chapter chapter : idToBook.get(bookId).getChapters())
        {
            deleteChapter(originator, chapter.getChapterId()); 
        }

        Book book = idToBook.remove(bookId);
        modelObserver.receiveCommandFromModel(Command.DeleteBook, book, originator);
    }
    
    @Override 
    public synchronized void deleteChapter(ClientSession originator, int chapterId) throws BeanNotFoundException, LockException
    {
        if (idToChapter.get(chapterId) == null)
        {
            throw new BeanNotFoundException("Chapter with Id " + chapterId + " doesn't exist");
        }
        
        if ((clientSessionToLockedChapters.get(originator) != chapterId)
            && clientSessionToLockedChapters.values().contains(chapterId))
        {
            throw new LockException("Chapter is locked");
        }

        Chapter chapter = idToChapter.remove(chapterId);

        Book book = chapter.getBook();
        Collection<Chapter> bookChapters = book.getChapters();
        bookChapters.remove(chapter); 
        book.setChapters(bookChapters);
        
        modelObserver.receiveCommandFromModel(Command.DeleteChapter, chapter, originator);
    }
    
    @Override
    public void addListener(ModelObserver modelObserver)
    { 
        this.modelObserver = modelObserver;
    }
    
    @Override
    public synchronized void getBook(ClientSession originator, int bookId) throws BeanNotFoundException
    {
        Book book = idToBook.get(bookId);
        if (book == null)
        {
            throw new BeanNotFoundException("Book with Id " + bookId + " was not found.");
        }
        modelObserver.receiveCommandFromModel(Command.GetBook, book, originator);
    }
    
    @Override
    public synchronized void getChapter(ClientSession originator, int chapterId) throws BeanNotFoundException
    {
        Chapter chapter = idToChapter.get(chapterId);
        if (chapter == null)
        {
            throw new BeanNotFoundException("Chapter with Id " + chapterId + " was not found.");
        }
        modelObserver.receiveCommandFromModel(Command.GetChapter, chapter, originator);
    }
    
    @Override
    public synchronized void lockChapter(ClientSession originator, int chapterId) throws LockException
    {
        if (clientSessionToLockedChapters.values().contains(chapterId))
        {
            throw new LockException("Chapter has been already locked.");
        }
        
        clientSessionToLockedChapters.put(originator, chapterId);
    }
    
    @Override
    public synchronized void lockBook(ClientSession originator, int bookId) throws LockException
    {
        if (clientSessionToLockedBooks.values().contains(bookId))
        {
            throw new LockException("Book has already been locked.");
        }
        
        clientSessionToLockedBooks.put(originator, bookId);
    }
    
    @Override
    public synchronized void unlockChapter(ClientSession originator, int chapterId) throws LockException
    {
        if (clientSessionToLockedChapters.get(originator) != chapterId)
        {
            throw new LockException("Chapter was not locked.");
        }
        
        clientSessionToLockedChapters.remove(originator);
    }
    
    @Override
    public synchronized void unlockBook(ClientSession originator, int bookId) throws LockException
    {
        if (clientSessionToLockedBooks.get(originator) != bookId)
        {
            throw new LockException("Book was not locked.");
        }
        
        clientSessionToLockedBooks.remove(originator);
    }
    
    private final Map<Integer, Book> idToBook = new HashMap<>();
    private final Map<Integer, Chapter> idToChapter = new HashMap<>();
    private final Map<ClientSession, Integer> clientSessionToLockedChapters = new HashMap<>();
    private final Map<ClientSession, Integer> clientSessionToLockedBooks = new HashMap<>();
    private ModelObserver modelObserver;
    private static final long serialVersionUID = 1L;
}
