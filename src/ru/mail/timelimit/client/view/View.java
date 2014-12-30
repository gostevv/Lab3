package ru.mail.timelimit.client.view;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JTree;

public interface View 
{
    public JTree getBooksAndChapters();
    public JMenuItem getSaveBooksAndChapters();
    public JMenuItem getLoadBooksAndChapters();
    
    public JButton getAddBook();
    public JTextField getAddBookAnnotation();
    public JTextField getAddBookId();
    public JTextField getAddBookTitle();
    public JTextField getAddBookIsbn();
    public JTextField getAddBookAuthor();
    
    public JButton getAddChapter();
    public JTextField getAddChapterId();
    public JTextField getAddBookIdOfChapter();
    public JTextField getAddChapterTitle();
    public JTextField getAddChapterText();
    
    public JButton getUpdate();
    
    public JButton getUpdateBook();
    public void showUpdateBookDialog(String bookTitle, String bookAuthor, String bookIsbn, String bookAnnotation);
    public void hideUpdateBookDialog();
    public JTextField getUpdateBookAnnotation();
    public JTextField getUpdateBookTitle();
    public JTextField getUpdateBookIsbn();
    public JTextField getUpdateBookAuthor();
    
    public JButton getUpdateChapter();
    public void showUpdateChapterDialog(int bookIdOfChapter, String chapterTitle, String chapterText);
    public void hideUpdateChapterDialog();
    public JTextField getUpdateBookIdOfChapter();
    public JTextField getUpdateChapterTitle();
    public JTextField getUpdateChapterText();
    
    public JButton getDelete();
    
    public void showErrorMessage(String errorMessage);
}
