package ru.mail.timelimit.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import ru.mail.timelimit.client.view.utils.SpringUtilities;

class ChapterUpdateForm
{
    
    ChapterUpdateForm(JFrame parent)
    {
        dialog = new JDialog(parent, true);
        JPanel panel = new JPanel(new SpringLayout());

        JLabel labelForBookId = new JLabel("ID книги:", JLabel.TRAILING);
        panel.add(labelForBookId);
        bookIdOfChapter = new JTextField(10);
        labelForBookId.setLabelFor(bookIdOfChapter);
        panel.add(bookIdOfChapter);

        JLabel labelForChapterTitle = new JLabel("Название: ", JLabel.TRAILING);
        panel.add(labelForChapterTitle);
        chapterTitle = new JTextField(10);
        labelForChapterTitle.setLabelFor(chapterTitle);
        panel.add(chapterTitle);

        JLabel labelForChapterText = new JLabel("Текст главы: ", JLabel.TRAILING);
        panel.add(labelForChapterText);
        chapterText = new JTextField(10);
        labelForChapterText.setLabelFor(chapterText);
        panel.add(chapterText);

        updateChapter = new JButton("Обновить главу");
        panel.add(updateChapter);
        cancel = new JButton("Отменить");
        panel.add(cancel);

        SpringUtilities.makeCompactGrid(panel,
                4, 2,
                6, 6,        
                6, 6);  

        dialog.setContentPane(panel);

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        cancel.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                dialog.setVisible(false);
            }
        });  
    }

    void show(int bookIdOfChapterAsInt, String chapterTitleAsString, String chapterTextAsString)
    {
        if (!dialog.isVisible())
        {
            bookIdOfChapter.setText(Integer.toString(bookIdOfChapterAsInt));
            chapterTitle.setText(chapterTitleAsString);
            chapterText.setText(chapterTextAsString);
            dialog.setVisible(true);
        }
    }
    
    void hide()
    {
        dialog.setVisible(false);
    }

    final JButton updateChapter;
    final JTextField bookIdOfChapter;
    final JTextField chapterTitle;
    final JTextField chapterText;
    final JButton cancel;
    final JDialog dialog;
    private static final String EMPTY_STRING = "";
}