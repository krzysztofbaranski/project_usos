package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by krzysztof on 05/02/14.
 */
public class SearchPanel {
    private JPanel menu;
    private JCheckBox advCheckBox;
    private JButton searchButton;
    private JFormattedTextField _studentBook;
    private JFormattedTextField _staffCode;
    private JFormattedTextField _fName;
    private JFormattedTextField _lName;
    private JFormattedTextField _pesel;
    private JFormattedTextField _mail;
    private JFormattedTextField _sName;
    private JFormattedTextField _dateOfBirth;
    private JFormattedTextField _acadTitle;
    private JComboBox _status;
    private JComboBox _department;
    private JPanel root;
    private JPanel advancedSearch;

    public SearchPanel() { advCheckBox.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(advCheckBox.isSelected()) {
                advancedSearch.setVisible(true);
            } else {
                advancedSearch.setVisible(false);
            }
        }
    });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }
}
