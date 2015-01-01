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
    private static final long serialVersionUID = 2L;
}
