package view;

import app.Window;
import controler.Utility;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by UĹĽytkownik on 07.02.14.
 */
public class AddMarkPanel {
    private JPanel root;
    private JFormattedTextField numerOsoby;
    private JFormattedTextField ocena;
    private JFormattedTextField opis;
    private JFormattedTextField uwagi;
    private JTextArea numerOsobyTextArea;
    private JTextArea ocenaTextArea;
    private JTextArea opisTextArea;
    private JTextArea uwagiTextArea;
    private JButton dodajButton;
    private JCheckBox ocenaKoncowaCheckBox;


    public AddMarkPanel (final long groupId, final long teatcherId){
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String studentId = (numerOsoby.getText());
                final String mark = (ocena.getText());
                final String description = (opis.getText());
                final String notice = (uwagi.getText());
                boolean ok = true;

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                try{
                    if(  Utility.insertSchema("marks", "student_id",
                            Utility.getData("select student_branch_id from students_branches__groups join " +
                                    "students_branches on students_branches.id = student_branch_id join groups " +
                                    "on group_id = groups.id where group_id = " + groupId + "and student_id = "
                                    + studentId).get(0).get(0),
                            "staff_id", teatcherId, "group_id", groupId, "mark", mark, "description", description,
                            "notice", notice, "date", dateFormat.format(date),
                            "is_final_mark", ocenaKoncowaCheckBox.isSelected()))
                    {
                        app.Window.mainFrame.setContentPane(new GroupDetails(groupId).getRoot());
                        app.Window.mainFrame.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (Exception f) {
                    JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                }




            }
        });
    }
    public JPanel getRoot() {
        return root;
    }

}
