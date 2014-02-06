package view;

import app.User;
import controler.Persons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by krzysztof on 06/02/14.
 */
public class AccountPanel {
    private JPanel menu;
    private JLabel _name;
    private JLabel _status;
    private JTextField _address;
    private JTextField _mail;
    private JTextField _phone;
    private JPanel root;
    private JButton _changeAddress;
    private JButton _changeMail;
    private JButton _changePhone;

    public AccountPanel() {
        _name.setText(User.fName + " " + User.lName);
        _status.setText(User.status);
        _address.setText(User.address);
        _mail.setText(User.mail);
        _phone.setText(User.phone);
        _changeAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_address.isEditable()) {
                    _address.setEditable(true);
                    _changeAddress.setText("OK");
                } else {
                    String newAddress = _address.getText();
                    try {
                        Persons.changePerson(User.person_id,"address",newAddress);
                    } catch(SQLException e1) {
                        _address.setText(User.address);
                        _address.setEditable(false);
                        _changeAddress.setText("...");
                    }
                    User.address = newAddress;
                    _address.setText(User.address);
                    _address.setEditable(false);
                    _changeAddress.setText("...");
                }
            }
        });
        _changeMail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_mail.isEditable()) {
                    _mail.setEditable(true);
                    _changeMail.setText("OK");
                } else {
                    String newMail = _mail.getText();
                    try {
                        Persons.changePerson(User.person_id,"address",newMail);
                    } catch(SQLException e1) {
                        _mail.setText(User.mail);
                        _mail.setEditable(false);
                        _changeMail.setText("...");
                    }
                    User.mail = newMail;
                    _mail.setText(User.mail);
                    _mail.setEditable(false);
                    _changeMail.setText("...");
                }
            }
        });
        _changePhone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_phone.isEditable()) {
                    _phone.setEditable(true);
                    _changePhone.setText("OK");
                } else {
                    String newPhone = _phone.getText();
                    try {
                        Persons.changePerson(User.person_id,"address",newPhone);
                    } catch(SQLException e1) {
                        _phone.setText(User.mail);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                    User.mail = newPhone;
                    _phone.setText(User.mail);
                    _phone.setEditable(false);
                    _changePhone.setText("...");
                }
            }
        });
    }

    public JPanel getRoot() {

        return root;
    }
}
