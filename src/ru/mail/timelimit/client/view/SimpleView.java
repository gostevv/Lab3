package ru.mail.timelimit.client.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class SimpleView implements View
{
    
    @Override
    public JTree getBooksAndChapters() 
    {
        return booksAndChapters;
    }

    @Override
    public JMenuItem getSaveBooksAndChapters() 
    {
        return saveBooksAndChapters;
    }

    @Override
    public JMenuItem getLoadBooksAndChapters() 
    {
        return loadBooksAndChapters;
    }

    @Override
    public JButton getAddBook() 
    {
        return bookAddForm.addBook;
    }

    @Override
    public JTextField getAddBookAnnotation() 
    {
        return bookAddForm.bookAnnotation;
    }

    @Override
    public JTextField getAddBookId() 
    {
        return bookAddForm.bookId;
    }

    @Override
    public JTextField getAddBookTitle() 
    {
        return bookAddForm.bookTitle;
    }

    @Override
    public JTextField getAddBookAuthor() 
    {
        return bookAddForm.bookAuthor;
    }
    
    @Override
    public JTextField getAddBookIsbn()
    {
        return bookAddForm.bookIsbn;
    }

    @Override
    public JButton getAddChapter() 
    {
        return chapterAddForm.addChapter;
    }

    @Override
    public JTextField getAddChapterId() 
    {
        return chapterAddForm.chapterId;
    }

    @Override
    public JTextField getAddBookIdOfChapter() 
    {
        return chapterAddForm.bookIdOfChapter;
    }

    @Override
    public JTextField getAddChapterTitle() 
    {
        return chapterAddForm.chapterTitle;
    }

    @Override
    public JTextField getAddChapterText() 
    {
        return chapterAddForm.chapterText;
    }

    @Override
    public JButton getUpdate() 
    {
        return update;
    }

    @Override
    public JButton getDelete()
    {
        return delete;
    }
    
    @Override
    public void showErrorMessage(String errorMessage)
    {
        JOptionPane.showMessageDialog(frame, errorMessage);
    }
    
    @Override
    public JTextField getUpdateBookAnnotation() 
    {
        return bookUpdateForm.bookAnnotation;
    }

    @Override
    public JTextField getUpdateBookTitle() 
    {
        return bookUpdateForm.bookTitle;
    }

    @Override
    public JTextField getUpdateBookIsbn() 
    {
        return bookUpdateForm.bookIsbn;
    }

    @Override
    public JTextField getUpdateBookAuthor() 
    {
        return bookUpdateForm.bookAuthor;
    }

    @Override
    public JTextField getUpdateBookIdOfChapter() 
    {
        return chapterUpdateForm.bookIdOfChapter;
    }

    @Override
    public JTextField getUpdateChapterTitle() 
    {
        return chapterUpdateForm.chapterTitle;
    }

    @Override
    public JTextField getUpdateChapterText() 
    {
        return chapterUpdateForm.chapterText;
    }

    @Override
    public void showUpdateBookDialog(String bookTitle, String bookAuthor, String bookIsbn, String bookAnnotation)
    {
        bookUpdateForm.show(bookTitle, bookAuthor, bookIsbn, bookAnnotation);
    }
    
    @Override
    public void showUpdateChapterDialog(int bookIdOfChapter, String chapterTitle, String chapterText)
    {
        chapterUpdateForm.show(bookIdOfChapter, chapterTitle, chapterText);
    }
    
    @Override
    public JButton getUpdateBook() 
    {
        return bookUpdateForm.updateBook;
    }
    
    @Override
    public JButton getUpdateChapter() 
    {
        return chapterUpdateForm.updateChapter;
    }
    
    @Override
    public void hideUpdateBookDialog()
    {
        bookUpdateForm.hide();
    }
    
    @Override
    public void hideUpdateChapterDialog()
    {
        chapterUpdateForm.hide();
    }
    
    @Override
    public JButton getCancelUpdateBook()
    {
        return bookUpdateForm.cancel;
    }
    
    @Override 
    public JButton getCancelUpdateChapter()
    {
        return chapterUpdateForm.cancel;
    }

    public SimpleView()
    {
        DefaultMutableTreeNode treeTop = new DefaultMutableTreeNode("Книги");
        booksAndChapters = new JTree(treeTop);
        addBook = new JButton("Добавить книгу");
        addChapter = new JButton("Добавить главу");
        update = new JButton("Изменить");
        delete = new JButton("Удалить");

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Операции");
        
        saveBooksAndChapters = new JMenuItem("Сохранить книги и главы в файл");
        loadBooksAndChapters = new JMenuItem("Загрузить книги и главы из файла");
        
        menu.add(saveBooksAndChapters);
        menu.add(loadBooksAndChapters);
        
        menuBar.add(menu);
        
        JPanel booksAndChaptersPanel = new JPanel();
        booksAndChaptersPanel.add(booksAndChapters);
        booksAndChaptersPanel.setPreferredSize(new Dimension(640,480));
        
        JPanel booksAndChaptersOperationsPanel = new JPanel();
        booksAndChaptersOperationsPanel.add(addBook);
        booksAndChaptersOperationsPanel.add(addChapter);
        booksAndChaptersOperationsPanel.add(update);
        booksAndChaptersOperationsPanel.add(delete);
        
        frame = new JFrame("Lab 2 - desktop application");
        frame.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        
        frame.add(booksAndChaptersPanel, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        frame.add(booksAndChaptersOperationsPanel, c);
        
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        chapterAddForm = new ChapterAddForm(frame);
        bookAddForm = new BookAddForm(frame);
        chapterUpdateForm = new ChapterUpdateForm(frame);
        bookUpdateForm = new BookUpdateForm(frame);
        
        addBook.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                bookAddForm.show();
            }
        }); 
         
        addChapter.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                chapterAddForm.show();
            }
        });
    }
    
    private final JTree booksAndChapters;
    private final JButton addBook;
    private final JButton addChapter;
    private final JButton delete;
    private final JButton update;
    private final JMenuItem saveBooksAndChapters;
    private final JMenuItem loadBooksAndChapters;
    private final JFrame frame;
    private final ChapterAddForm chapterAddForm;
    private final BookAddForm bookAddForm;
    private final ChapterUpdateForm chapterUpdateForm;
    private final BookUpdateForm bookUpdateForm;
}
