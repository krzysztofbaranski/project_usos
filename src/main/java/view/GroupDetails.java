package view;

import app.User;
import controler.Learning;
import controler.Utility;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * Created by UĹĽytkownik on 07.02.14.
 */
public class GroupDetails {
    private JPanel root;
    private JTextPane mainInfoPane;
    private JTextPane titlePane;
    private JTextPane przedmiotPane;
    private JTable oceny;
    private JScrollPane scroll1;
    private JTable prowadzacy;
    private JScrollPane scrollUczestnicy;
    private JTable uczestnicy;
    private JButton dodajOcene;
    Vector<Vector<Object>> vGeneral;
    Vector<Vector<Object>> vMarks;
    Vector<Vector<Object>> vClasses;
    Vector<Vector<Object>> vTeachers;
    Vector<Vector<Object>> vStudents;

    public GroupDetails(final long groupId) {
        vGeneral = Utility.getData(Learning.getGroupSQL(groupId));
        vClasses = Utility.getData(Learning.getClasses(groupId));

        StringBuilder classesString = new StringBuilder();
        for (Vector<Object> w : vClasses)
            classesString.append(w.get(0) + "\t" + w.get(1) + "-" + w.get(2) + "\tsala " + w.get(3) + "\n");

        titlePane.setText("" + vGeneral.get(0).get(1));
        przedmiotPane.setText("" + vGeneral.get(0).get(6));
        przedmiotPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                app.Window.mainFrame.setContentPane(new CourseDetails((long) vGeneral.get(0).get(5)).getRoot());
                app.Window.mainFrame.setVisible(true);
            }

        });
        mainInfoPane.setText(vGeneral.get(0).get(2) + "\nRok " + vGeneral.get(0).get(3) + "/" + ((Integer) vGeneral.get(0).get(3) + 1) + ", semestr " + vGeneral.get(0).get(4)
                + "\nLiczba godzin: " + vGeneral.get(0).get(7) + "\nZajęcia:\n" + classesString);


        /**** oceny ****/
        vMarks = Utility.getData(Learning.getMarks(groupId, (User.staffCode != null ? -1 : User.person_id), false));


        TableModel marksModel = new AbstractTableModel() {

            private final Object[] columnNames = {(User.staffCode != null ? "student" : "prowadzący"),
                    "ocena", "opis", "uwagi", "data", "ocena końcowa"};

            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            public int getColumnCount() {
                return 6;
            }

            public int getRowCount() {
                return vMarks.size();
            }

            public Object getValueAt(int row, int col) {
                if (col == 0) return vMarks.get(row).get(1) + " " + vMarks.get(row).get(2);
                return vMarks.get(row).get(col + 2);
            }
        };

        oceny.setModel(marksModel);
        oceny.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = oceny.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = oceny.columnAtPoint(new Point(e.getX(), e.getY()));
                if (row == -1) return;
                if (col == 0) {
                      Vector<Vector<Object>> w = Utility.getData("select mail from persons where id = " + vMarks.get(row).get(0));
                    app.Window.mainFrame.setContentPane(new AccountPanel((String)w.firstElement().firstElement()).getRoot());
                    app.Window.mainFrame.setVisible(true);

                }
            }

        });


        /****************************** prowadzÄ…cy ********************************************************/

        vTeachers = Utility.getData(Learning.getGroupTeachersSQL(groupId));
        TableModel teachersModel = new AbstractTableModel() {
            private final Object[] columnNames = {"imię i nazwisko"};

            public String getColumnName(int column) {
                return columnNames[column].toString();
            }


            public int getColumnCount() {
                return 1;
            }

            public int getRowCount() {
                return vTeachers.size();
            }

            public Object getValueAt(int row, int col) {
                return vTeachers.get(row).get(1) + " " + vTeachers.get(row).get(2);
            }
        };

        prowadzacy.setModel(teachersModel);
        prowadzacy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = oceny.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = oceny.columnAtPoint(new Point(e.getX(), e.getY()));
                if (row == -1) return;
                if (col == 0) {
                    Vector<Vector<Object>> w = Utility.getData("select mail from persons where id = " + vTeachers.get(row).get(0));
                    app.Window.mainFrame.setContentPane(new AccountPanel((String)w.firstElement().firstElement()).getRoot());
                    app.Window.mainFrame.setVisible(true);

                }
            }

        });

/****************************** uczestnicy ********************************************************/

        vStudents = Utility.getData(Learning.getGroupStudentsSQL(groupId, true));
        TableModel studentsModel = new AbstractTableModel() {

            private final Object[] columnNames = {"numer osoby", "imię i nazwisko", "kierunek"};

            public String getColumnName(int column) {
                return columnNames[column].toString();
            }


            public int getColumnCount() {
                return 3;
            }

            public int getRowCount() {
                return vStudents.size();
            }

            public Object getValueAt(int row, int col) {
                if (col == 0) return vStudents.get(row).get(0);
                if (col == 1) return vStudents.get(row).get(1) + " " + vStudents.get(row).get(2);
                return vStudents.get(row).get(3);
            }
        };

        uczestnicy.setModel(studentsModel);
        uczestnicy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = oceny.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = oceny.columnAtPoint(new Point(e.getX(), e.getY()));
                if (row == -1) return;
                if (col == 0) {
                    Vector<Vector<Object>> w = Utility.getData("select mail from persons where id = " + vStudents.get(row).get(0));
                    app.Window.mainFrame.setContentPane(new AccountPanel((String)w.firstElement().firstElement()).getRoot());
                    app.Window.mainFrame.setVisible(true);

                }
            }

        });


        if(User.staffCode == null || !Learning.groupDetailsPermissionTeacher(groupId))
            dodajOcene.setVisible(false);
        else dodajOcene.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                app.Window.mainFrame.setContentPane(new AddMarkPanel(groupId, User.person_id).getRoot());
                app.Window.mainFrame.setVisible(true);

            }
        });

    }

    public JPanel getRoot() {
        return root;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}