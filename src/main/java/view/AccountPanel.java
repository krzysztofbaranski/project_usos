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
    private JTextField _studentBook;
    private JTextField _staffCode;
    private JTextField _cath;
    private JTextField _post;
    private JLabel _studentBookLabel;
    private JButton _changeRoom;
    private JButton _changeCath;
    private JButton _changePost;
    private JTextField _room;
    private JLabel _staffCodeLabel;
    private JLabel _roomLabel;
    private JLabel _cathLabel;
    private JLabel _postLabel;

    /**
     * Moje konto
     */
    public AccountPanel() {
        // ladowanie etykiet
        _name.setText(User.academicTitle + User.fName + " " + User.lName);
        _status.setText(User.status);
        _address.setText(User.address);
        _mail.setText(User.mail);
        _phone.setText(User.phone);

        // ladowanie zdjecia
        if(User.photo != null) {
            _photoLabel.setIcon(new ImageIcon(User.photo));
            removePhoto.setEnabled(true);
        }

        // student
        if(_status.equals("student")) {
            Vis(false, _staffCode, _post, _cath, _room, _changeCath, _changePost, _changeRoom, _cathLabel, _postLabel, _roomLabel, _staffCodeLabel);
            _studentBook.setText(User.studentBook);
            // teacher
        } else if(_status.equals("teacher")) {
            Vis(false, _studentBook, _studentBookLabel);
            _room.setText(User.room);
            _cath.setText(User.cathedral);
            _staffCode.setText(User.staffCode);
            _post.setText(User.post);
        } else {
            Vis(false, _staffCode, _post, _cath, _room, _changeCath, _changePost, _changeRoom, _cathLabel, _postLabel, _roomLabel, _staffCodeLabel, _studentBook, _studentBookLabel);
        }


        addPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("(*.png, *.jpg, *.gif)", "png", "jpg", "gif"));
                fc.setAcceptAllFileFilterUsed(false);
                int returnVal = fc.showOpenDialog(Window.mainFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Utility.updatePhoto(User.person_id, file);
                    User.photo = Utility.getPhoto(User.person_id);
                    if(User.photo != null) {
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
                } catch(SQLException e1) {
                    JOptionPane.showConfirmDialog(Window.mainFrame, "Nie można usunąć", "Error", JOptionPane.ERROR_MESSAGE);
                }
                User.photo = null;
                removePhoto.setEnabled(false);

                Window.mainFrame.setContentPane(new AccountPanel().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });

        /**
         *
         *
         * Przyciski do zmiany
         *
         *
         */
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(((JButton)e.getSource()).getName());
                String button = ((JButton) e.getSource()).getName();
                if(button.equals("address")) {                                              // address
                    if(!_address.isEditable()) {
                        _address.setEditable(true);
                        _changeAddress.setText("OK");
                    } else {
                        String newAddress = _address.getText();
                        try {
                            Persons.changePerson(User.person_id, "address", newAddress);
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
                } else if(button.equals("mail")) {                                          // mail
                    if(!_mail.isEditable()) {
                        _mail.setEditable(true);
                        _changeMail.setText("OK");
                    } else {
                        String newMail = _mail.getText();
                        try {
                            Persons.changePerson(User.person_id, "mail", newMail);
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
                } else if(button.equals("phone")) {                                         // phone
                    if(!_phone.isEditable()) {
                        _phone.setEditable(true);
                        _changePhone.setText("OK");
                    } else {
                        String newPhone = _phone.getText();
                        try {
                            Persons.changePerson(User.person_id, "phone", newPhone);
                        } catch(SQLException e1) {
                            _phone.setText(User.phone);
                            _phone.setEditable(false);
                            _changePhone.setText("...");
                        }
                        User.phone = newPhone;
                        _phone.setText(User.phone);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                } else if(button.equals("room")) {                                         // room
                    if(!_phone.isEditable()) {
                        _phone.setEditable(true);
                        _changePhone.setText("OK");
                    } else {
                        String newPhone = _phone.getText();
                        try {
                            Persons.changePerson(User.person_id, "phone", newPhone);
                        } catch(SQLException e1) {
                            _phone.setText(User.phone);
                            _phone.setEditable(false);
                            _changePhone.setText("...");
                        }
                        User.phone = newPhone;
                        _phone.setText(User.phone);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                } else if(button.equals("cath")) {                                          // cathedra
                    if(!_phone.isEditable()) {
                        _phone.setEditable(true);
                        _changePhone.setText("OK");
                    } else {
                        String newPhone = _phone.getText();
                        try {
                            Persons.changePerson(User.person_id, "phone", newPhone);
                        } catch(SQLException e1) {
                            _phone.setText(User.phone);
                            _phone.setEditable(false);
                            _changePhone.setText("...");
                        }
                        User.phone = newPhone;
                        _phone.setText(User.phone);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                } else if(button.equals("post")) {                                          // post
                    if(!_phone.isEditable()) {
                        _phone.setEditable(true);
                        _changePhone.setText("OK");
                    } else {
                        String newPhone = _phone.getText();
                        try {
                            Persons.changePerson(User.person_id, "phone", newPhone);
                        } catch(SQLException e1) {
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
            }
        };

        _changeAddress.addActionListener(listener);
        _changeMail.addActionListener(listener);
        _changePhone.addActionListener(listener);
        _changeRoom.addActionListener(listener);
        _changeCath.addActionListener(listener);
        _changePost.addActionListener(listener);
    }


    /**
     * konto uzytkownika
     *
     * @param mail
     */
    public AccountPanel(final String mail) {
        final Vector<Vector<Object>> v = Utility.getData("select persons.id,fname,lname,(select name as status from statuses where id=status_id),address,mail," +
                "phone,student_book,staff_code,academic_title,room,post," +
                "(select name from cathedrals where id=cathedral_id) " +
                "from persons full join student_books on student_books.person_id = id full join staff_details on staff_details.person_id=id where mail='" + mail + "'");

        final Long id = (Long) v.elementAt(0).elementAt(0);
        _name.setText((v.elementAt(0).elementAt(9) == null ? "" : v.elementAt(0).elementAt(9) + " ") + v.elementAt(0).elementAt(1) + " " + v.elementAt(0).elementAt(2));
        _status.setText((String) v.elementAt(0).elementAt(3));
        _address.setText((String) v.elementAt(0).elementAt(4));
        _mail.setText((String) v.elementAt(0).elementAt(5));
        _phone.setText((String) v.elementAt(0).elementAt(6));

        Image _photo = Utility.getPhoto(id);
        if(_photo != null) {
            _photo = _photo.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
            _photoLabel.setIcon(new ImageIcon(_photo));
            removePhoto.setEnabled(true);
        }

        // student
        if(_status.getText().equals("student")) {
            //System.out.println(v.elementAt(0).elementAt(7));
            Vis(false, _staffCode, _post, _cath, _room, _changeCath, _changePost, _changeRoom, _cathLabel, _postLabel, _roomLabel, _staffCodeLabel);
            _studentBook.setText(String.valueOf(v.elementAt(0).elementAt(7)));
            // teacher
        } else if(_status.getText().equals("teacher")) {
            Vis(false, _studentBook, _studentBookLabel);
            _room.setText(String.valueOf(v.elementAt(0).elementAt(10)));
            _cath.setText((String) v.elementAt(0).elementAt(12));
            _staffCode.setText((String) v.elementAt(0).elementAt(8));
            _post.setText((String) v.elementAt(0).elementAt(11));
        } else {
            Vis(false, _staffCode, _post, _cath, _room, _changeCath, _changePost, _changeRoom, _cathLabel, _postLabel, _roomLabel, _staffCodeLabel, _studentBook, _studentBookLabel);
        }


        /**
         *
         *
         * Przyciski do zmiany dla admina
         *
         *
         */
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(((JButton)e.getSource()).getName());
                String button = ((JButton) e.getSource()).getName();
                if(button.equals("address")) {                                              // address
                    if(!_address.isEditable()) {
                        _address.setEditable(true);
                        _changeAddress.setText("OK");
                    } else {
                        String newAddress = _address.getText();
                        try {
                            Persons.changePerson(id, "address", newAddress);
                        } catch(SQLException e1) {
                            _address.setText((String) v.elementAt(0).elementAt(4));
                            _address.setEditable(false);
                            _changeAddress.setText("...");
                        }
                        _address.setText(newAddress);
                        _address.setEditable(false);
                        _changeAddress.setText("...");
                    }
                } else if(button.equals("mail")) {                                          // mail
                    if(!_mail.isEditable()) {
                        _mail.setEditable(true);
                        _changeMail.setText("OK");
                    } else {
                        String newMail = _mail.getText();
                        try {
                            Persons.changePerson(id, "mail", newMail);
                        } catch(SQLException e1) {
                            _mail.setText((String) v.elementAt(0).elementAt(5));
                            _mail.setEditable(false);
                            _changeMail.setText("...");
                        }
                        _mail.setText(newMail);
                        _mail.setEditable(false);
                        _changeMail.setText("...");
                    }
                } else if(button.equals("phone")) {                                         // phone
                    if(!_phone.isEditable()) {
                        _phone.setEditable(true);
                        _changePhone.setText("OK");
                    } else {
                        String newPhone = _phone.getText();
                        try {
                            Persons.changePerson(id, "phone", newPhone);
                        } catch(SQLException e1) {
                            _phone.setText((String) v.elementAt(0).elementAt(6));
                            _phone.setEditable(false);
                            _changePhone.setText("...");
                        }
                        _phone.setText(newPhone);
                        _phone.setEditable(false);
                        _changePhone.setText("...");
                    }
                } else if(button.equals("room")) {                                         // room
                    if(!_room.isEditable()) {
                        _room.setEditable(true);
                        _changeRoom.setText("OK");
                    } else {
                        int newRoom = Integer.parseInt(_room.getText());
                        try {
                            Persons.changeStaff(_staffCode.getText(), "room", newRoom);
                        } catch(SQLException e1) {
                            _room.setText((String) v.elementAt(0).elementAt(6));
                            _room.setEditable(false);
                            _changeRoom.setText("...");
                        }
                        _room.setText(String.valueOf(newRoom));
                        _room.setEditable(false);
                        _changeRoom.setText("...");
                    }
                } else if(button.equals("cath")) {                                          // cathedra
                    if(!_cath.isEditable()) {
                        _cath.setEditable(true);
                        _changeCath.setText("OK");
                    } else {
                        String newValue = _cath.getText();
                        try {
                            Persons.changeStaff(_staffCode.getText(), "cathedral", newValue);
                        } catch(SQLException e1) {
                            _cath.setText((String) v.elementAt(0).elementAt(12));
                            _cath.setEditable(false);
                            _changeCath.setText("...");
                        }
                        _cath.setText(String.valueOf(newValue));
                        _cath.setEditable(false);
                        _changeCath.setText("...");
                    }
                } else if(button.equals("post")) {                                          // post
                    if(!_post.isEditable()) {
                        _post.setEditable(true);
                        _changePost.setText("OK");
                    } else {
                        String newValue = _post.getText();
                        try {
                            Persons.changeStaff(_staffCode.getText(), "post", newValue);
                        } catch(SQLException e1) {
                            _post.setText((String) v.elementAt(0).elementAt(10));
                            _post.setEditable(false);
                            _changePost.setText("...");
                        }
                        _post.setText(String.valueOf(newValue));
                        _post.setEditable(false);
                        _changePost.setText("...");
                    }
                }
            }
        };

        _changeAddress.addActionListener(listener);
        _changeMail.addActionListener(listener);
        _changePhone.addActionListener(listener);
        _changeRoom.addActionListener(listener);
        _changeCath.addActionListener(listener);
        _changePost.addActionListener(listener);
        addPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("(*.png, *.jpg, *.gif)", "png", "jpg", "gif"));
                fc.setAcceptAllFileFilterUsed(false);
                int returnVal = fc.showOpenDialog(Window.mainFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
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
                } catch(SQLException e1) {
                    JOptionPane.showConfirmDialog(Window.mainFrame, "Nie można usunąć", "Error", JOptionPane.ERROR_MESSAGE);
                }
                removePhoto.setEnabled(false);

                Window.mainFrame.setContentPane(new AccountPanel(mail).getRoot());
                Window.mainFrame.setVisible(true);
            }
        });

        if(User.person_id != Settings.superuser) {
            Vis(false, _changeRoom, _changePost, _changeCath, _changeAddress, _changeMail, _changePhone, addPhoto, removePhoto);
        }


    }

    public JPanel getRoot() {

        return root;
    }

    private void Vis(boolean v, JComponent... c) {
        for(JComponent i : c) i.setVisible(v);
    }

}
