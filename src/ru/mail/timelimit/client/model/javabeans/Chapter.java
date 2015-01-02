package ru.mail.timelimit.client.model.javabeans;

import java.io.Serializable;

/* Used only as a container for communications between model and controller */
public class Chapter implements Serializable
{

    public Chapter(int chapterId, int bookId, String title, String chapterText)
    {
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.title = title;
        this.chapterText = chapterText;
    } 

    public void setBookId(int bookId) 
    {
        this.bookId = bookId;
    }
    
    public int getBookId()
    {
        return bookId;
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
        return "Chapter{" + "chapterId=" + chapterId + ", bookId=" + bookId + ", title=" + title + ", chapterText=" + chapterText + '}';
    }
    
    private int chapterId;
    private int bookId;
    private String title;
    private String chapterText;
    private static final long serialVersionUID = 3L;
}
