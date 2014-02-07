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
                    //System.out.println(v);
                    if(v.elementAt(0).elementAt(0) != null && (boolean) v.elementAt(0).elementAt(0)) {

                        User.person_id = (Long) v.elementAt(0).elementAt(1);

                        v = Utility.getDataWithException("select fname,sname,lname,address,mail,(select name from statuses where id=status_id),phone from persons where id=" + User.person_id);
                        User.fName = (String) v.elementAt(0).elementAt(0);
                        User.sName = (String) v.elementAt(0).elementAt(1);
                        User.lName = (String) v.elementAt(0).elementAt(2);
                        User.address = (String) v.elementAt(0).elementAt(3);
                        User.mail = (String) v.elementAt(0).elementAt(4);
                        User.status = (String) v.elementAt(0).elementAt(5);
                        User.phone = (String) v.elementAt(0).elementAt(6);
                        User.photo = Utility.getPhoto(User.person_id);
                        if(User.photo != null) {
                            User.photo = User.photo.getScaledInstance(120,140, Image.SCALE_SMOOTH);
                        }


                        Window.mainFrame.setContentPane(new MainPanel().getRoot());
                        Window.mainFrame.setTitle("USOS - Main Page");
                        Window.mainFrame.repaint();
                        Window.mainFrame.setVisible(true);
                    }
                } catch(SQLException exc) {
                    //System.out.println(v);
                    exc.printStackTrace();
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
