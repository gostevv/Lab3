package ru.mail.timelimit.client.model.javabeans;

import java.io.Serializable;

public class Chapter implements Serializable
{

    public Chapter(int chapterId, Book book, String title, String chapterText)
    {
        this.chapterId = chapterId;
        this.book = book;
        this.title = title;
        this.chapterText = chapterText;
    } 
    
    public Book getBook() 
    {
        return new Book(book.getBookId(), 
                book.getTitle(), 
                book.getAuthor(), 
                book.getIsbn(), 
                book.getAnnotation(), 
                book.getChapters());
    }

    public void setBook(Book book) 
    {
        this.book = book;
    }

    public int getChapterId() 
    {
        return chapterId;
    }

    public void setChapterId(int chapterId) 
    {
        this.chapterId = chapterId;
    }

    public String getChapterText() 
    {
        return chapterText;
    }

    public void setChapterText(String chapterText) 
    {
        this.chapterText = chapterText;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }
    
    public Chapter()
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
        final Chapter other = (Chapter) obj;
        if (this.chapterId != other.chapterId) 
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 43 * hash + this.chapterId;
        return hash;
    }

    @Override
    public String toString() 
    {
        return "Chapter{" + "chapterId=" + chapterId + ", bookId=" + book.getBookId() + ", title=" + title + ", chapterText=" + chapterText + '}';
    }
    
    private int chapterId;
    private Book book;
    private String title;
    private String chapterText;
    private static final long serialVersionUID = 2L;
}
