package view;

import app.*;
import app.Window;
import controler.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by krzysztof on 05/02/14.
 */
public class LoginPage {
    private JPanel root;
    private JFormattedTextField _mail;
    private JPasswordField _passwd;
    private JButton loginButton;

    public LoginPage() {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<Vector<Object>> v;
                try {
                    v = Utility.getDataWithException("select decode(md5('" +
                            _passwd.getText() +
                            "'), 'hex') = (select passwd from passwords where person_id = (select id from persons where mail = '" +
                            _mail.getText() + "')),(select id from persons where mail = '" + _mail.getText() + "')");


                    // udalo sie zalogowac
                    if(v.elementAt(0).elementAt(0) != null && ((Boolean) v.elementAt(0).elementAt(0))) {

                        // ustawienia konta
                        User.person_id = (long) v.elementAt(0).elementAt(1);

                        v = Utility.getDataWithException("select fname,sname,lname,address,mail," +
                                "(select name from statuses where id=status_id),phone,student_book,staff_code,academic_title,room,post," +
                                "(select name from cathedrals where id=cathedral_id) " +
                                "from persons full join student_books on student_books.person_id = id full join staff_details on staff_details.person_id=id where id=" + User.person_id);
                        User.fName = (String) v.elementAt(0).elementAt(0);
                        User.sName = (String) v.elementAt(0).elementAt(1);
                        User.lName = (String) v.elementAt(0).elementAt(2);
                        User.address = (String) v.elementAt(0).elementAt(3);
                        User.mail = (String) v.elementAt(0).elementAt(4);
                        User.status = (String) v.elementAt(0).elementAt(5);
                        User.phone = (String) v.elementAt(0).elementAt(6);

                        // student
                        User.studentBook = String.valueOf(v.elementAt(0).elementAt(7));

                        // staff
                        User.staffCode = (String) v.elementAt(0).elementAt(8);
                        User.room = String.valueOf((Integer) v.elementAt(0).elementAt(9));
                        User.post = (String) v.elementAt(0).elementAt(10);
                        User.academicTitle = (v.elementAt(0).elementAt(11) == null ? "" : v.elementAt(0).elementAt(11) + " ");
                        User.cathedral = (String) v.elementAt(0).elementAt(12);


                        // photo
                        User.photo = Utility.getPhoto(User.person_id);
                        if(User.photo != null) {
                            User.photo = User.photo.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
                        }


                        Window.mainFrame.setContentPane(new MainPanel().getRoot());
                        Window.mainFrame.setTitle("USOS");
                        Window.mainFrame.setVisible(true);
                    }
                } catch(SQLException exc) {
                    //System.out.println(v);
                    //exc.printStackTrace();
                    _mail.setText("");
                    _passwd.setText("");
                }
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }

}
