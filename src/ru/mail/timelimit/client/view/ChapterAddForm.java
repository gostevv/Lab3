package ru.mail.timelimit.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import ru.mail.timelimit.client.view.utils.SpringUtilities;

class ChapterAddForm
{
    
    ChapterAddForm(JFrame parent)
    {
        dialog = new JDialog(parent, true);
        JPanel panel = new JPanel(new SpringLayout());

        JLabel labelForChapterId = new JLabel("ID: ", JLabel.TRAILING);
        panel.add(labelForChapterId);
        chapterId = new JTextField(10);
        labelForChapterId.setLabelFor(chapterId);
        panel.add(chapterId);

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

        addChapter = new JButton("Добавить главу");
        panel.add(addChapter);
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

    void show()
    {
        if (!dialog.isVisible())
        {
            chapterId.setText(EMPTY_STRING);
            bookIdOfChapter.setText(EMPTY_STRING);
            chapterTitle.setText(EMPTY_STRING);
            chapterText.setText(EMPTY_STRING);
            dialog.setVisible(true);
        }
    }

    final JButton addChapter;
    final JTextField chapterId;
    final JTextField bookIdOfChapter;
    final JTextField chapterTitle;
    final JTextField chapterText;
    final JDialog dialog;
    private static final String EMPTY_STRING = "";
}