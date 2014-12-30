package ru.mail.timelimit.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import ru.mail.timelimit.client.view.utils.SpringUtilities;

@Deprecated
class DeleteForm
{
    DeleteForm()
    {
        frame = new JFrame("Удаление");
        JPanel panel = new JPanel(new SpringLayout());

        JLabel labelForChapterIdOrBookId = new JLabel("ID: ", JLabel.TRAILING);
        panel.add(labelForChapterIdOrBookId);
        chapterIdOrBookId = new JTextField(10);
        labelForChapterIdOrBookId.setLabelFor(chapterIdOrBookId);
        panel.add(chapterIdOrBookId);

        delete = new JButton("Удалить");
        JButton discard = new JButton("Отменить");
        panel.add(delete);
        panel.add(discard);

        SpringUtilities.makeCompactGrid(panel,
                2, 2,
                6, 6,        
                6, 6);  

        frame.setContentPane(panel);

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        discard.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                frame.setVisible(false);
            }
        });  
    }

    public void show()
    {
        if (!frame.isVisible())
        {
            frame.setVisible(true);
            chapterIdOrBookId.setText(EMPTY_STRING);
        }
    }

    final JButton delete;
    final JTextField chapterIdOrBookId;
    final JFrame frame;
    private static final String EMPTY_STRING = "";
}