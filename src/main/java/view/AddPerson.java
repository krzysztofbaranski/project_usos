package view;

import app.Window;
import controler.Persons;
import controler.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

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
    private JPanel root;

    public JPanel getRoot() {
        return root;
    }

    public AddPerson() {






        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String studentBook = (_studentBook.getText().equals("") ? "-1" : _studentBook.getText());
                final String staffCode = (_staffCode.getText().equals("") ? null : _staffCode.getText());
                final String fName = (_fName.getText().equals("") ? null : _fName.getText());
                final String lName = (_lName.getText().equals("") ? null : _lName.getText());
                final String pesel = (_pesel.getText().equals("") ? null : _pesel.getText());
                final String mail = (_mail.getText().equals("") ? null : _mail.getText());
                final String sName = (_sName.getText().equals("") ? null : _sName.getText());
                final String dateOfBirth = (_dateOfBirth.getText().equals("") ? null : _dateOfBirth.getText());
                final String acadTitle = (_acadTitle.getText().equals("") ? null : _acadTitle.getText());
                final String status = (_status.getText().equals("") ? null : _status.getText());
                final String placeOfBirth = (_placeOfBirth.getText().equals("") ? null : _placeOfBirth.getText());
                final String room = (_room.getText().equals("") ? "-1" : _room.getText());
                final String post = (_post.getText().equals("") ? null : _post.getText());
                final String phone = (_phone.getText().equals("") ? null : _phone.getText());
                final String address = (_address.getText().equals("") ? null : _address.getText());
                boolean ok = true;

                if(_status.getText().equals("student")) {
                    try {
                        Persons.addStudent(Long.parseLong(studentBook),
                                fName,
                                sName,
                                lName,
                                pesel,
                                dateOfBirth,
                                placeOfBirth,
                                address,
                                phone,
                                mail);
                    } catch(Exception e1) {
                        ok = false;
                        JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                } else if(_status.getText().equals("teacher")) {
                    try {
                        Persons.addStaff(staffCode, fName, sName, lName, pesel, dateOfBirth, placeOfBirth, address, phone, mail, acadTitle, Integer.parseInt(room), post, null);
                    } catch(Exception e1) {
                        ok = false;
                        JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        Persons.addPerson(fName, sName, lName, pesel, dateOfBirth, placeOfBirth, address, phone, mail, status);
                    } catch(Exception e1) {
                        ok = false;
                        JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                }

                Vector<Vector<Object>> w = Utility.getData("select mail from persons where pesel = '" + pesel+"'");

                if(ok) {
                    Window.mainFrame.setContentPane(new AddPerson((String) w.firstElement().firstElement()).getRoot());
                    Window.mainFrame.setVisible(true);
                }
            }
        });
    }








    public AddPerson(final String mail) {


        Vector<Vector<Object>> v = null;
        try {
            v = Utility.getDataWithException("select fname,sname,lname,address,mail," +
                    "(select name from statuses where id=status_id),phone,student_book,staff_code,academic_title,room,post,pesel,date_of_birth,place_of_birth" +
                    " " +
                    "from persons full join student_books on student_books.person_id = id full join staff_details on staff_details.person_id=id where mail='" + mail + "'");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(Window.mainFrame,"Błąd odczytu","Error",JOptionPane.ERROR_MESSAGE);
        }
        String fName = (String) v.elementAt(0).elementAt(0);
        String sName = (String) v.elementAt(0).elementAt(1);
        String lName = (String) v.elementAt(0).elementAt(2);
        String address = (String) v.elementAt(0).elementAt(3);
        String status = (String) v.elementAt(0).elementAt(5);
        String phone = (String) v.elementAt(0).elementAt(6);

        // student
        final String studentBook = String.valueOf(v.elementAt(0).elementAt(7));

        // staff
        final String staffCode = (String) v.elementAt(0).elementAt(8);
        String room = v.elementAt(0).elementAt(10) + "";
        String post = (String) v.elementAt(0).elementAt(11);
        String academicTitle = (v.elementAt(0).elementAt(9) == null ? "" : v.elementAt(0).elementAt(9) + " ");
        String pesel = (String) v.elementAt(0).elementAt(12);
        String dateOfBirth = v.elementAt(0).elementAt(13) + "";
        String placeOfBirth = (String) v.elementAt(0).elementAt(14);



        _studentBook.setText( studentBook );
        _staffCode.setText(staffCode  );
        _fName.setText( fName );
        _lName.setText( lName );
        _pesel.setText( pesel );
        _mail.setText( mail );
        _sName.setText( sName );
        _dateOfBirth.setText( dateOfBirth );
        _acadTitle.setText(  academicTitle);
        _status.setText(  status);
        _placeOfBirth.setText( placeOfBirth );
        _room.setText( room );
        _post.setText(  post);
        _phone.setText( phone );
        _address.setText(address);

        _studentBook.setEditable(false);
        _staffCode.setEditable(false);
        _status.setEditable(false);








        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //final String studentBook = (_studentBook.getText().equals("") ? "-1" : "'"+_studentBook.getText()+"'");
                //final String staffCode = (_staffCode.getText().equals("") ? null : "'"+_staffCode.getText()+"'");
                final String fName = (_fName.getText().equals("") ? null : "'"+_fName.getText()+"'");
                final String lName = (_lName.getText().equals("") ? null : "'"+_lName.getText()+"'");
                final String pesel = (_pesel.getText().equals("") ? null : "'"+_pesel.getText()+"'");
                final String __mail = (_mail.getText().equals("") ? null : "'"+_mail.getText()+"'");
                final String sName = (_sName.getText().equals("") ? null : "'"+_sName.getText()+"'");
                final String dateOfBirth = (_dateOfBirth.getText().equals("") ? null : "'"+_dateOfBirth.getText()+"'");
                final String acadTitle = (_acadTitle.getText().equals("") ? null : "'"+_acadTitle.getText()+"'");
                final String status = (_status.getText().equals("") ? null : "'"+_status.getText()+"'");
                final String placeOfBirth = (_placeOfBirth.getText().equals("") ? null : "'"+_placeOfBirth.getText()+"'");
                final String room = (_room.getText().equals("") ? "-1" : "'"+_room.getText()+"'");
                final String post = (_post.getText().equals("") ? null : "'"+_post.getText()+"'");
                final String phone = (_phone.getText().equals("") ? null : "'"+_phone.getText()+"'");
                final String address = (_address.getText().equals("") ? null : "'"+_address.getText()+"'");
                boolean ok = true;

                if(!_status.getText().equals("teacher")) {
                    try {
                        StringBuilder update = new StringBuilder("update persons set ");
                        update.append("fName = ").append(fName).append(", sName = ").append(sName).append(", lName = ").append(lName).append("  ,pesel = ").append(pesel).append(" ,date_of_birth = ").append(dateOfBirth).append(" ,place_of_birth = ").append(placeOfBirth).append(" ,address = ").append(address).append(",phone = ").append(phone).append(" ,mail = ").append(__mail).append(" where mail = '").append(mail).append("'");
                        System.out.println(update);
                        Utility.updateData(update.toString());

                    } catch(Exception e1) {
                        ok = false;
                        JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        StringBuilder update = new StringBuilder("update staff set ");
                        update.append("first_name = ").append(fName).append(", second_name = ").append(sName).append(", last_name = ").append(lName).append("  ,pesel = ").append(pesel).append(" ,date_of_birth = ").append(dateOfBirth).append(" ,place_of_birth = ").append(placeOfBirth).append(" ,address = ").append(address).append(",phone = ").append(phone).append(" ,mail = ").append(__mail).append(",room = ").append(room).append(",post = ").append(post).append(",academic_title = ").append(acadTitle)
                                .append(" where mail = '").append(mail).append("'");
                        //System.out.println(update);
                        Utility.updateData(update.toString());

                    } catch(Exception e1) {
                        ok = false;
                        JOptionPane.showMessageDialog(Window.mainFrame, "Błąd! Sprawdź poprawność danych!", "Error", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                }

                Vector<Vector<Object>> w = Utility.getData("select mail from persons where pesel = " + pesel);

                if(ok) {
                    Window.mainFrame.setContentPane(new AddPerson((String) w.firstElement().firstElement()).getRoot());
                    Window.mainFrame.setVisible(true);
                }
            }
        });
    }

}
