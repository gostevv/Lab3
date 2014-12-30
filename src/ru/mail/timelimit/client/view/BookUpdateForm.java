package ru.mail.timelimit.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import ru.mail.timelimit.client.view.utils.SpringUtilities;

class BookUpdateForm 
{

    BookUpdateForm(JFrame parent)
    {
        dialog = new JDialog(parent, true);
        JPanel panel = new JPanel(new SpringLayout());

        JLabel labelForBookTitle = new JLabel("Название: ", JLabel.TRAILING);
        panel.add(labelForBookTitle);
        bookTitle = new JTextField(10);
        labelForBookTitle.setLabelFor(bookTitle);
        panel.add(bookTitle);

        JLabel labelForBookAuthor = new JLabel("Автор: ", JLabel.TRAILING);
        panel.add(labelForBookAuthor);
        bookAuthor = new JTextField(10);
        labelForBookAuthor.setLabelFor(bookAuthor);
        panel.add(bookAuthor);

        JLabel labelForIsbn = new JLabel("ISBN: ", JLabel.TRAILING);
        panel.add(labelForIsbn);
        bookIsbn = new JTextField(10);
        labelForIsbn.setLabelFor(bookIsbn);
        panel.add(bookIsbn);

        JLabel labelForAnnotation = new JLabel("Аннотация: ", JLabel.TRAILING);
        panel.add(labelForAnnotation);
        bookAnnotation = new JTextField(10);
        labelForAnnotation.setLabelFor(bookAnnotation);
        panel.add(bookAnnotation);

        updateBook = new JButton("Обновить книгу");
        panel.add(updateBook);
        JButton discard = new JButton("Отменить");
        panel.add(discard);

        SpringUtilities.makeCompactGrid(panel,
                5, 2,
                6, 6,        
                6, 6);       

        dialog.setContentPane(panel);

        dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        dialog.pack();
        discard.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                dialog.setVisible(false);
            }
        });  
    }

    void show(String bookTitleAsString, String bookAuthorAsString, String bookIsbnAsString, String bookAnnotationAsString)
    {
        if (!dialog.isVisible())
        {
            bookTitle.setText(bookTitleAsString);
            bookAuthor.setText(bookAuthorAsString);
            bookIsbn.setText(bookIsbnAsString);
            bookAnnotation.setText(bookAnnotationAsString);
            dialog.setVisible(true);
        }
    }

    void hide()
    {
        dialog.setVisible(false);
    }
    
    final JTextField bookTitle;
    final JTextField bookAuthor;
    final JTextField bookIsbn;
    final JTextField bookAnnotation;   
    final JButton updateBook;
    final JDialog dialog;
    private static final String EMPTY_STRING = "";
}