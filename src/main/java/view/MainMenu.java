package view;

import app.Settings;
import app.User;
import app.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
/**
 * Created by krzysztof on 05/02/14.
 */
class MainMenu {
    private JButton _main;
    private JButton _search;
    private JButton _account;
    private JButton _logout;
    private JPanel menu;
    private JButton _marks;
    private JButton addPerson;

    public MainMenu() {
        if(User.person_id != Settings.superuser) {
            addPerson.setVisible(false);
        }


        // MAIN PANEL
        _main.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.mainFrame.setContentPane(new MainPanel().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });

        // SEARCH
        _search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.mainFrame.setContentPane(new SearchPanel().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });

        _marks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.mainFrame.setContentPane(new MarksPanel().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });

        _account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.mainFrame.setContentPane(new AccountPanel().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });
        _logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.person_id = -1;
                User.fName = null;
                User.sName = null;
                User.lName = null;
                User.status = null;
                User.address = null;
                User.mail = null;
                User.phone = null;

                Window.mainFrame.setContentPane(new LoginPage().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });

        addPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.mainFrame.setContentPane(new AddPerson().getRoot());
                Window.mainFrame.setVisible(true);
            }
        });
    }

}
