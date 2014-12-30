package ru.mail.timelimit.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import ru.mail.timelimit.client.exceptions.BeanAlreadyExistsException;
import ru.mail.timelimit.client.exceptions.BeanNotFoundException;
import ru.mail.timelimit.client.model.ServerProxyModel;
import ru.mail.timelimit.client.model.javabeans.Book;
import ru.mail.timelimit.client.model.javabeans.Chapter;
import ru.mail.timelimit.client.view.View;

public class ClientController implements Controller, PropertyChangeListener
{

    @Override
    public final void setupViewEvents() 
    { 
        view.getAddBook().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                String bookIdAsString = view.getAddBookId().getText();
                try
                {
                    int bookId = Integer.parseInt(bookIdAsString);
                    String bookTitle = view.getAddBookTitle().getText();
                    String bookAuthor = view.getAddBookAuthor().getText();
                    String bookIsbn = view.getAddBookIsbn().getText();
                    String bookAnnotation = view.getAddBookAnnotation().getText();
                    try
                    {
                        model.addBook(bookId, bookTitle, bookAuthor, bookIsbn, bookAnnotation);
                    }
                    catch(BeanAlreadyExistsException exception)
                    {
                        view.showErrorMessage(exception.getMessage());
                    }
                }
                catch (NumberFormatException exception)
                {
                    view.showErrorMessage(BOOK_ID_NOT_NUMBER_EXCEPTION_MESSAGE);
                } 
            } 
            
            private static final String BOOK_ID_NOT_NUMBER_EXCEPTION_MESSAGE = "Id книги обязано быть числом";
        });
        
        view.getAddChapter().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                String chapterIdAsString = view.getAddChapterId().getText();
                int chapterId = -1;
                try
                {
                     chapterId = Integer.parseInt(chapterIdAsString);
                }
                catch (NumberFormatException exception)
                {
                    view.showErrorMessage(CHAPTER_ID_NOT_NUMBER_EXCEPTION_MESSAGE);
                } 
                
                if (chapterId != -1)
                {
                    String bookIdOfChapterIdAsString = view.getAddBookIdOfChapter().getText();
                    int bookIdOfChapterId = -1; 
                    try
                    {
                        bookIdOfChapterId = Integer.parseInt(bookIdOfChapterIdAsString);
                    }
                    catch (NumberFormatException exception)
                    {
                        view.showErrorMessage(BOOK_ID_NOT_NUMBER_EXCEPTION_MESSAGE);
                    } 

                    if (bookIdOfChapterId != -1)
                    {
                        String chapterTitle = view.getAddChapterTitle().getText();
                        String chapterText = view.getAddChapterText().getText();
                        try
                        {
                            model.addChapter(chapterId, bookIdOfChapterId, chapterTitle, chapterText);
                        }
                        catch(BeanAlreadyExistsException | BeanNotFoundException exception)
                        {
                            view.showErrorMessage(exception.getMessage());
                        }
                    }
                }
            } 
            
            private static final String CHAPTER_ID_NOT_NUMBER_EXCEPTION_MESSAGE = "Id главы обязано быть числом";
            private static final String BOOK_ID_NOT_NUMBER_EXCEPTION_MESSAGE = "Id книги обязано быть числом";
        });
        
        view.getUpdate().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                DefaultMutableTreeNode selected = (DefaultMutableTreeNode)
                        view.getBooksAndChapters().getLastSelectedPathComponent();
                
                if (selected == null)
                {
                    view.showErrorMessage(OBJECT_FOR_UPDATE_NOT_SPECIFIED_EXCEPTION_MESSAGE);
                }
                else
                {
                    Integer bookId = treeNodeToBookId.get(selected);
                    if (bookId != null)
                    {
                        Book book = model.getBook(bookId);
                        view.showUpdateBookDialog(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getAnnotation());
                    }
                    else
                    {
                        Integer chapterId = treeNodeToChapterId.get(selected);
                        if (chapterId != null)
                        {
                            Chapter chapter = model.getChapter(chapterId);
                            view.showUpdateChapterDialog(chapter.getBook().getBookId(), chapter.getTitle(), chapter.getChapterText());
                        }
                        else
                        {
                            view.showErrorMessage(OBJECT_FOR_UPDATE_IS_UNKNOWN_EXCEPTION_MESSAGE);
                        }
                    } 
                }   
            }
            private static final String OBJECT_FOR_UPDATE_NOT_SPECIFIED_EXCEPTION_MESSAGE = "Выберите, пожалуйста, сначала элемент дерева";
            private static final String OBJECT_FOR_UPDATE_IS_UNKNOWN_EXCEPTION_MESSAGE = "Выбранный элемент дерева не является ни книгой, ни главой";
        });
        
        view.getUpdateBook().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                DefaultMutableTreeNode selected = (DefaultMutableTreeNode)
                        view.getBooksAndChapters().getLastSelectedPathComponent();
                Integer bookId = treeNodeToBookId.get(selected);
                String bookTitle = view.getUpdateBookTitle().getText();
                String bookAuthor = view.getUpdateBookAuthor().getText();
                String bookIsbn = view.getUpdateBookIsbn().getText();
                String bookAnnotation = view.getUpdateBookAnnotation().getText();
                try
                {
                    model.updateBook(bookId, bookTitle, bookAuthor, bookIsbn, bookAnnotation);
                    view.hideUpdateBookDialog();
                }
                catch(BeanNotFoundException exception)
                {
                    view.showErrorMessage(exception.getMessage());
                }
            }
        });
        
        view.getUpdateChapter().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                DefaultMutableTreeNode selected = (DefaultMutableTreeNode)
                        view.getBooksAndChapters().getLastSelectedPathComponent();
                Integer chapterId = treeNodeToChapterId.get(selected);
                
                String bookIdOfChapterIdAsString = view.getUpdateBookIdOfChapter().getText();
                int bookIdOfChapterId = -1; 
                try
                {
                    bookIdOfChapterId = Integer.parseInt(bookIdOfChapterIdAsString);
                }
                catch (NumberFormatException exception)
                {
                    view.showErrorMessage(BOOK_ID_NOT_NUMBER_EXCEPTION_MESSAGE);
                } 

                if (bookIdOfChapterId != -1)
                {
                    String chapterTitle = view.getUpdateChapterTitle().getText();
                    String chapterText = view.getUpdateChapterText().getText();
                    try
                    {
                        model.updateChapter(chapterId, bookIdOfChapterId, chapterTitle, chapterText);
                        view.hideUpdateChapterDialog();
                    }
                    catch(BeanNotFoundException exception)
                    {
                        view.showErrorMessage(exception.getMessage());
                    }
                }
            } 
            
            private static final String BOOK_ID_NOT_NUMBER_EXCEPTION_MESSAGE = "Id книги обязано быть числом";
        });
        
        view.getDelete().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                DefaultMutableTreeNode selected = (DefaultMutableTreeNode)
                        view.getBooksAndChapters().getLastSelectedPathComponent();
                
                if (selected == null)
                {
                    view.showErrorMessage(OBJECT_FOR_UPDATE_NOT_SPECIFIED_EXCEPTION_MESSAGE);
                }
                else
                {
                    try
                    {
                        Integer bookId = treeNodeToBookId.get(selected);
                        if (bookId != null)
                        {
                            model.deleteBook(treeNodeToBookId.get(selected));
                        }
                        else
                        {
                            Integer chapterId = treeNodeToChapterId.get(selected);
                            if (chapterId != null)
                            {
                                model.deleteChapter(treeNodeToChapterId.get(selected));
                            }
                            else
                            {
                                view.showErrorMessage(OBJECT_FOR_UPDATE_IS_UNKNOWN_EXCEPTION_MESSAGE);
                            }
                        }
                    }
                    catch (BeanNotFoundException exception)
                    { 
                        view.showErrorMessage(exception.getMessage());
                    }
                }   
            }
            private static final String OBJECT_FOR_UPDATE_NOT_SPECIFIED_EXCEPTION_MESSAGE = "Выберите, пожалуйста, сначала элемент дерева";
            private static final String OBJECT_FOR_UPDATE_IS_UNKNOWN_EXCEPTION_MESSAGE = "Выбранный элемент дерева не является ни книгой, ни главой";
        });
     
     /*   view.getSaveBooksAndChapters().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                final JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) 
                {
                    File file = fileChooser.getSelectedFile();
                    ObjectOutputStream outputStream = null;
                    try 
                    {
                        outputStream = new ObjectOutputStream(new FileOutputStream(file));
                        outputStream.writeObject(model);
                    }
                    catch (IOException exception) 
                    {
                        view.showErrorMessage(exception.getMessage());
                    }
                    finally
                    {
                        if (outputStream != null)
                        {
                            close(outputStream);
                        }
                    }
                } 
            }                
            
            private void close(Closeable closeable)
            {
                try
                {
                    closeable.close();
                }
                catch(IOException exception)
                {
                    view.showErrorMessage(exception.getMessage());
                }
            }
        }); */
        
       /* view.getLoadBooksAndChapters().addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                final JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) 
                {
                    File file = fileChooser.getSelectedFile();
                    ObjectInputStream inputStream = null;
                    Model newModel = null;
                    try 
                    {
                        inputStream = new ObjectInputStream(new FileInputStream(file));
                        newModel = (Model) inputStream.readObject();
                    } 
                    catch (ClassNotFoundException | IOException exception) 
                    {
                        view.showErrorMessage(exception.getMessage());
                    }
                    finally
                    {
                        if (inputStream != null)
                        {
                            close(inputStream);
                        }
                    }
                    
                    /**
                     * SimpleModel specific logic - recreate model after serialization.
                     * SimpleModel kinda only 1 model in this project, but i am
                     * working with interfaces :)
                     * 
                     * Thoughts of improvements... Model shouldn't extend Serializable interface
                     * yet SimpleModel should. Probably. According to Bloch :)
                     */
        /*            if (newModel != null
                        && newModel instanceof ServerProxyModel
                        && model instanceof ServerProxyModel)
                    {
                        ServerProxyModel newSimpleModel = (ServerProxyModel) newModel;
                        ((ServerProxyModel) model).destroyModel();
                        model = newSimpleModel;
                        newSimpleModel.addListener(ClientController.this);
                        newSimpleModel.loadModel();
                    }
                    else
                    {
                        model = newModel;
                    }
                }
            }
            
            private void close(Closeable closeable)
            {
                try
                {
                    closeable.close();
                }
                catch(IOException exception)
                {
                    view.showErrorMessage(exception.getMessage());
                }
            }
        });*/
    }
    
    /**
     * Not good enough. Remember not to use it again. Custom implementation with
     * enum class instead of String eventName would be better.
     * 
     * Also the way it works is hidden in the code of an unknown value.
     * This means whether SwingPropertyChangeSupport starts new threads or not is a secret.
     * 
     * Can be improved by implementing HashMap (String, LoopbackModelOperation -> interface) instead of a 4 if's
     * 
     * @param event parameter fired by s model
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) 
    {
        String eventName = event.getPropertyName();
        if ("AddBook".equalsIgnoreCase(eventName))
        {
            Book book = (Book) event.getNewValue();
            final DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(book.toString());
            bookIdToTreeNode.put(book.getBookId(), treeNode); 
            treeNodeToBookId.put(treeNode, book.getBookId());
            SwingUtilities.invokeLater(new Runnable() 
            {

                @Override
                public void run() 
                {
                    JTree tree = view.getBooksAndChapters();
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
                    root.add(treeNode);
                    treeModel.reload(root);
                } 
            });
        }
        else if ("AddChapter".equalsIgnoreCase(eventName))
        {
            final Chapter chapter = (Chapter) event.getNewValue();
            final DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(chapter.toString());
            chapterIdToTreeNode.put(chapter.getChapterId(), treeNode); 
            treeNodeToChapterId.put(treeNode, chapter.getChapterId());
            SwingUtilities.invokeLater(new Runnable() 
            {

                @Override
                public void run() 
                {
                    JTree tree = view.getBooksAndChapters();
                    DefaultMutableTreeNode bookNode = bookIdToTreeNode.get(chapter.getBook().getBookId());
                    bookNode.add(treeNode);
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    treeModel.reload(bookNode);
                } 
            });
        }
        else if ("DeleteChapter".equalsIgnoreCase(eventName))
        {
            final Chapter oldChapter = (Chapter) event.getOldValue();
            final DefaultMutableTreeNode chapterTreeNode = chapterIdToTreeNode.remove(oldChapter.getChapterId());
            treeNodeToChapterId.remove(chapterTreeNode);
            SwingUtilities.invokeLater(new Runnable() 
            {

                @Override
                public void run() 
                {
                    JTree tree = view.getBooksAndChapters();
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) chapterTreeNode.getParent();
                    chapterTreeNode.removeFromParent();
                    treeModel.reload(parentNode);
                } 
            });
        }
        else if ("DeleteBook".equalsIgnoreCase(eventName))
        {
            final Book oldBook = (Book) event.getOldValue();
            final DefaultMutableTreeNode bookTreeNode = bookIdToTreeNode.remove(oldBook.getBookId());
            treeNodeToBookId.remove(bookTreeNode);
            SwingUtilities.invokeLater(new Runnable() 
            {

                @Override
                public void run() 
                {
                    JTree tree = view.getBooksAndChapters();
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    
                    bookTreeNode.removeFromParent();
                    treeModel.reload((DefaultMutableTreeNode) treeModel.getRoot());
                } 
            });
        }
    }
    
    public ClientController(View view, ServerProxyModel model)
    {
        this.view = view;
        this.model = model;
        this.model.addListener(this);
        setupViewEvents();
    }
    
    private final View view;
    private final ServerProxyModel model;
    private final Map<Integer, DefaultMutableTreeNode> bookIdToTreeNode = new HashMap<>();
    private final Map<Integer, DefaultMutableTreeNode> chapterIdToTreeNode = new HashMap<>();
    private final Map<DefaultMutableTreeNode, Integer> treeNodeToBookId = new HashMap<>();
    private final Map<DefaultMutableTreeNode, Integer> treeNodeToChapterId = new HashMap<>();
}
