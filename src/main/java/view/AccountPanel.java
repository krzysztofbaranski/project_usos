package view;

import app.Settings;
import app.User;
import app.Window;
import controler.Persons;
import controler.Utility;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by krzysztof on 06/02/14.
 */
class AccountPanel {
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
    private JButton addPhoto;
    private JLabel _photoLabel;
    private JButton removePhoto;


    public AccountPanel() {
        _name.setText(User.fName + " " + User.lName);
        _status.setText(User.status);
        _address.setText(User.address);
        _mail.setText(User.mail);
        _phone.setText(User.phone);

        if (User.photo != null) {
            _photoLabel.setIcon(new ImageIcon(User.photo));
            removePhoto.setEnabled(true);
        }


        _changeAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_address.isEditable()) {
                    _address.setEditable(true);
                    _changeAddress.setText("OK");
                } else {
                    String newAddress = _address.getText();
                    try {
                        Persons.changePerson(User.person_id, "address", newAddress);
                    } catch (SQLException e1) {
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
                if (!_mail.isEditable()) {
                    _mail.setEditable(true);
                    _changeMail.setText("OK");
                } else {
                    String newMail = _mail.getText();
                    try {
                        Persons.changePerson(User.person_id, "mail", newMail);
                    } catch (SQLException e1) {
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
                if (!_phone.isEditable()) {
                    _phone.setEditable(true);
                    _changePhone.setText("OK");
                } else {
                    String newPhone = _phone.getText();
                    try {
                        Persons.changePerson(User.person_id, "phone", newPhone);
                    } catch (SQLException e1) {
                        _phone.setText(User.phone);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                    User.phone = newPhone;
                    _phone.setText(User.phone);
                    _phone.setEditable(false);
                    _changePhone.setText("...");
                }
            }
        });
        addPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("(*.png, *.jpg, *.gif)", "png", "jpg", "gif"));
                fc.setAcceptAllFileFilterUsed(false);
                int returnVal = fc.showOpenDialog(Window.mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Utility.updatePhoto(User.person_id, file);
                    User.photo = Utility.getPhoto(User.person_id);
                    if (User.photo != null) {
                        User.photo = User.photo.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
                    }
                    removePhoto.setEnabled(true);
                    Window.mainFrame.setContentPane(new AccountPanel().getRoot());
                    Window.mainFrame.setVisible(true);
                }
            }
        });
        removePhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utility.updateData("update persons set photo = null where id = " + User.person_id);
                } catch (SQLException e1) {
                    JOptionPane.showConfirmDialog(Window.mainFrame, "Nie można usunąć", "Error", JOptionPane.ERROR_MESSAGE);
                }
                User.photo = null;
                removePhoto.setEnabled(false);

                Window.mainFrame.setContentPane(new AccountPanel().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });
    }

    public AccountPanel(final String mail) {
        Vector<Vector<Object>> v;
        v = Utility.getData("select id,fname,lname,(select name from statuses where id=status_id),address,mail,phone from persons where mail ='" + mail + "'");

        final Long id = (Long) v.elementAt(0).elementAt(0);
        _name.setText(v.elementAt(0).elementAt(1) + " " + v.elementAt(0).elementAt(2));
        _status.setText((String) v.elementAt(0).elementAt(3));
        _address.setText((String) v.elementAt(0).elementAt(4));
        _mail.setText((String) v.elementAt(0).elementAt(5));
        _phone.setText((String) v.elementAt(0).elementAt(6));

        Image _photo = Utility.getPhoto(id);
        if (_photo != null) {
            _photo = _photo.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
            _photoLabel.setIcon(new ImageIcon(_photo));
            removePhoto.setEnabled(true);
        }

        if (User.person_id != Settings.superuser) {
            _changeAddress.setVisible(false);
            _changeMail.setVisible(false);
            _changePhone.setVisible(false);
            addPhoto.setVisible(false);
            removePhoto.setVisible(false);
        } else {
            final Vector<Vector<Object>> finalV = v;
            _changeAddress.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!_address.isEditable()) {
                        _address.setEditable(true);
                        _changeAddress.setText("OK");
                    } else {
                        String newAddress = _address.getText();
                        try {
                            Persons.changePerson(id, "address", newAddress);
                        } catch (SQLException e1) {
                            _address.setText((String) finalV.elementAt(0).elementAt(4));
                            _address.setEditable(false);
                            _changeAddress.setText("...");
                        }
                        _address.setText(newAddress);
                        _address.setEditable(false);
                        _changeAddress.setText("...");
                    }
                }
            });
            _changeMail.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!_mail.isEditable()) {
                        _mail.setEditable(true);
                        _changeMail.setText("OK");
                    } else {
                        String newMail = _mail.getText();
                        try {
                            Persons.changePerson(id, "mail", newMail);
                        } catch (SQLException e1) {
                            _mail.setText((String) finalV.elementAt(0).elementAt(5));
                            _mail.setEditable(false);
                            _changeMail.setText("...");
                        }
                        _mail.setText(newMail);
                        _mail.setEditable(false);
                        _changeMail.setText("...");
                    }
                }
            });
            _changePhone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!_phone.isEditable()) {
                        _phone.setEditable(true);
                        _changePhone.setText("OK");
                    } else {
                        String newPhone = _phone.getText();
                        try {
                            Persons.changePerson(id, "phone", newPhone);
                        } catch (SQLException e1) {
                            _phone.setText((String) finalV.elementAt(0).elementAt(6));
                            _phone.setEditable(false);
                            _changePhone.setText("...");
                        }
                        _phone.setText(newPhone);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                }
            });
            addPhoto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fc = new JFileChooser();
                    fc.setFileFilter(new FileNameExtensionFilter("(*.png, *.jpg, *.gif)", "png", "jpg", "gif"));
                    fc.setAcceptAllFileFilterUsed(false);
                    int returnVal = fc.showOpenDialog(Window.mainFrame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        Utility.updatePhoto(id, file);

                        removePhoto.setEnabled(true);
                        Window.mainFrame.setContentPane(new AccountPanel(mail).getRoot());
                        Window.mainFrame.setVisible(true);
                    }
                }
            });
            removePhoto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Utility.updateData("update persons set photo = null where id = " + id);
                    } catch (SQLException e1) {
                        JOptionPane.showConfirmDialog(Window.mainFrame, "Nie można usunąć", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    removePhoto.setEnabled(false);

                    Window.mainFrame.setContentPane(new AccountPanel(mail).getRoot());
                    Window.mainFrame.setVisible(true);
                }
            });
        }
    }

    public JPanel getRoot() {

        return root;
    }

}
