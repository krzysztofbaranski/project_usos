package view;

import app.Window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by krzysztof on 05/02/14.
 */
class SearchPanel {
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
    private JPanel root;
    private JPanel advancedSearch;
    private JFormattedTextField _status;
    private JFormattedTextField _department;

    public SearchPanel() {

        advCheckBox.addChangeListener(new ChangeListener() {
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
                String[] columnNames;
                StringBuilder query = new StringBuilder("select ");
                Vector<String> where = new Vector<>();

                // if student
                if(!_studentBook.getText().equals("") || _status.getText().toLowerCase().equals("student")) {
                    query.append("student_book,fname,lname,mail,name from persons full join student_books on student_books.person_id=id join statuses on status_id=statuses.id ");
                    columnNames = new String[]{"Numer indeksu", "Imię", "Nazwisko", "E-mail", "Status"};
                // teacher
                } else if(!_staffCode.getText().equals("") || _status.getText().toLowerCase().equals("teacher") || !_acadTitle.getText().equals("")) {
                    query.append("staff_code,fname,lname,mail,name from persons full join staff_details on staff_details.person_id=id join statuses on status_id=statuses.id ");
                    columnNames = new String[]{"Kod pracownika", "Imię", "Nazwisko", "E-mail", "Status"};
                } else {
                    query.append("fname,lname,mail,name from persons full join student_books on student_books.person_id=id full join staff_details on staff_details.person_id=id join statuses on status_id=statuses.id ");
                    columnNames = new String[]{"Imię", "Nazwisko", "E-mail", "Status"};
                }



                if(!_staffCode.getText().equals("")) where.add("staff_code='"+_staffCode.getText()+"'");
                if(!_acadTitle.getText().equals("")) where.add("academic_title='"+_acadTitle.getText()+"'");
                if(!_studentBook.getText().equals("")) where.add("student_book="+_studentBook.getText());
                if(!_fName.getText().equals("")) where.add("fname='"+_fName.getText()+"'");
                if(!_sName.getText().equals("")) where.add("sname='"+_sName.getText()+"'");
                if(!_lName.getText().equals("")) where.add("lname='"+_lName.getText()+"'");
                if(!_pesel.getText().equals("")) where.add("pesel='"+_pesel.getText()+"'");
                if(!_dateOfBirth.getText().equals("")) where.add("date_of_birth'"+_dateOfBirth.getText()+"'");
                if(!_mail.getText().equals("")) where.add("mail='"+_mail.getText()+"'");
                if(!_status.getText().equals("")) where.add("name='"+_status.getText()+"'");
                if(!where.isEmpty()) {
                    query.append(" where ").append(where.firstElement());
                    for(int i = 1; i < where.size(); ++i) {
                        query.append(" and ").append(where.elementAt(i));
                    }
                }

                query.append(" order by lname");

                //System.out.println(query);
                Window.mainFrame.setContentPane(new SearchResultPanel(columnNames,query.toString()).getRoot());
                Window.mainFrame.setVisible(true);
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }
}
