package view;

import app.User;
import app.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by krzysztof on 05/02/14.
 */
public class MainMenu {
    private JButton _main;
    private JButton _search;
    private JButton _account;
    private JButton _courses;
    private JButton _settings;
    private JButton _logout;
    private JPanel menu;

    public MainMenu() {
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
    }
}
