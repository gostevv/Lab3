package ru.mail.timelimit.client.model.javabeans;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class Book implements Serializable
{

    public Book(int bookId, String title, String author, String isbn, String annotation)
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.annotation = annotation;
    }
    
    Book(int bookId, String title, String author, String isbn, String annotation, Collection<Chapter> chapters)
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.annotation = annotation;
        this.chapters = chapters;
    }
    
    public String getAnnotation() 
    {
        return annotation;
    }

    public void setAnnotation(String annotation) 
    {
        this.annotation = annotation;
    }

    public String getAuthor() 
    {
        return author;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public int getBookId() 
    {
        return bookId;
    }

    public void setBookId(int bookId) 
    {
        this.bookId = bookId;
    }

    public Collection<Chapter> getChapters() 
    {
        Collection<Chapter> chaptersDefensiveCopy = new HashSet<>(chapters);
        return chaptersDefensiveCopy;
    }

    public void setChapters(Collection<Chapter> chapters) 
    {
        this.chapters = chapters;
    }

    public String getIsbn() 
    {
        return isbn;
    }

    public void setIsbn(String isbn) 
    {
        this.isbn = isbn;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }
    
    public Book()
    {
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final Book other = (Book) obj;
        if (this.bookId != other.bookId) 
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 31 * hash + this.bookId;
        return hash;
    }

    @Override
    public String toString() 
    {
        return "Book{" + "bookId=" + bookId + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", annotation=" + annotation + '}';
    }

    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String annotation;
    private Collection<Chapter> chapters = new HashSet<>();
    private static final long serialVersionUID = 1L;
}
