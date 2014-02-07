package view;

import app.Window;
import controler.Persons;
import controler.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by krzysztof on 07/02/14.
 */
public class AddPerson {
    private JFormattedTextField _studentBook;
    private JFormattedTextField _staffCode;
    private JFormattedTextField _fName;
    private JFormattedTextField _lName;
    private JFormattedTextField _pesel;
    private JFormattedTextField _mail;
    private JFormattedTextField _sName;
    private JFormattedTextField _dateOfBirth;
    private JFormattedTextField _acadTitle;
    private JFormattedTextField _status;
    private JButton searchButton;
    private JFormattedTextField _placeOfBirth;
    private JFormattedTextField _room;
    private JFormattedTextField _post;
    private JFormattedTextField _phone;
    private JFormattedTextField _address;

    public AddPerson() {

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_status.getText().equals("student")) {
                    try {
                        Persons.addStudent(Long.parseLong(_studentBook.getText()), _fName.getText(), _sName.getText(), _lName.getText(), _pesel.getText(), _dateOfBirth.getText(), _placeOfBirth.getText(), _address.getText(), _phone.getText(), _mail.getText());
                    } catch(SQLException e1) {
                        JOptionPane.showConfirmDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if(_status.getText().equals("teacher")) {
                    try {
                        Persons.addStaff(_staffCode.getText(), _fName.getText(), _sName.getText(), _lName.getText(), _pesel.getText(), _dateOfBirth.getText(), _placeOfBirth.getText(), _address.getText(), _phone.getText(), _mail.getText(), _acadTitle.getText(), Integer.parseInt(_room.getText()), _post.getText(), null);
                    } catch(SQLException e1) {
                        JOptionPane.showConfirmDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    try {
                        Persons.addPerson(_fName.getText(), _sName.getText(), _lName.getText(), _pesel.getText(), _dateOfBirth.getText(), _placeOfBirth.getText(), _address.getText(), _phone.getText(), _mail.getText(), _status.getText());
                    } catch(SQLException e1) {
                        JOptionPane.showConfirmDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

}
