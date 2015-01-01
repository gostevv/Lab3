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
    public synchronized void updateBook(ClientSession originator, int bookId, String title, String author, String isbn, String annotation) throws BeanNotFoundException, LockException
    {
        deleteBook(originator, bookId);
        try
        {
            addBook(originator, bookId, title, author, isbn, annotation);
        }
        catch (BeanAlreadyExistsException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public synchronized void updateChapter(ClientSession originator, int chapterId, int bookId, String title, String chapterText) throws BeanNotFoundException, LockException
    {
        deleteChapter(originator, chapterId);
        try
        {
            addChapter(originator, chapterId, bookId, title, chapterText);
        }
        catch (BeanAlreadyExistsException exception)
        {
            throw new RuntimeException(exception);
        }
    } 
    
    @Override 
    public synchronized void deleteBook(ClientSession originator, int bookId) throws BeanNotFoundException, LockException
    {
        if (idToBook.get(bookId) == null)
        {
            throw new BeanNotFoundException("Book with Id " + bookId + " doesn't exist.");
        }

        if (lockedChapters.contains(bookId))
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
        
        if (lockedChapters.contains(chapterId))
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
        if (lockedChapters.contains(chapterId))
        {
            throw new LockException("Chapter has been already locked.");
        }
        
        lockedChapters.add(chapterId);
    }
    
    @Override
    public synchronized void lockBook(ClientSession originator, int bookId) throws LockException
    {
        if (lockedBooks.contains(bookId))
        {
            throw new LockException("Book has been already locked.");
        }
        
        lockedBooks.add(bookId);
    }
    
    @Override
    public synchronized void unlockChapter(ClientSession originator, int chapterId) throws LockException
    {
        if (!lockedChapters.contains(chapterId))
        {
            throw new LockException("Chapter was not locked.");
        }
        
        lockedChapters.remove(chapterId);
    }
    
    @Override
    public synchronized void unlockBook(ClientSession originator, int bookId) throws LockException
    {
        if (!lockedBooks.contains(bookId))
        {
            throw new LockException("Book was not locked.");
        }
        
        lockedBooks.remove(bookId);
    }
    
    private final Map<Integer, Book> idToBook = new HashMap<>();
    private final Map<Integer, Chapter> idToChapter = new HashMap<>();
    private final Set<Integer> lockedChapters = new HashSet<>();
    private final Set<Integer> lockedBooks = new HashSet<>();
    private ModelObserver modelObserver;
    private static final long serialVersionUID = 1L;
}
