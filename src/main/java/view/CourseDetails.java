package view;

import app.Settings;
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
 * Created by Użytkownik on 07.02.14.
 */
public class CourseDetails {
    private JTextPane nazwaTextPane;
    private JPanel root;
    private JTextPane titlePane;
    private JTextArea opis;
    private JScrollPane scroll;
    private JTable grupy;
    private JTable kierunki;
    Vector<Vector<Object>> v;
    Vector<Vector<Object>> vGroups;

    public CourseDetails(long courseId) {
        v = Utility.getData(Learning.getCourse(courseId));

        StringBuilder classesString = new StringBuilder();
        titlePane.setText("" + v.get(0).get(0));
        opis.setText("ECTS: " + v.get(0).get(1));

        final boolean onlyCurrent = false;

            vGroups = Utility.getData(Learning.getCourseGroups(courseId, Settings.academic_year, Settings.semester));
        System.out.println(Learning.getCourseGroups(courseId, -1, -1));
        System.out.println(vGroups.size());
        for (Vector<Object> vv : vGroups)
            for (Object o : vv)
                System.out.println(o);
        System.out.println(User.post);

        TableModel model = new AbstractTableModel() {

            private final Object[] columnNames = {"grupa", "typ", "rok", "semestr"};

            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            public int getColumnCount() {
                return 4;
            }

            public int getRowCount() {
                return vGroups.size();
            }

            public Object getValueAt(int row, int col) {
                    if (col == 2) return Settings.academic_year;
                    if (col == 3) return Settings.semester;
                return vGroups.get(row).get(col + 1);
            }
        };

        grupy.setModel(model);
        grupy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = grupy.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = grupy.columnAtPoint(new Point(e.getX(), e.getY()));
                if (row == -1) return;
                if (col == 0) {
                    app.Window.mainFrame.setContentPane(new GroupDetails((long) vGroups.get(row).get(0)).getRoot());
                    app.Window.mainFrame.setVisible(true);
                }
            }

        });




    }

    public JPanel getRoot() {

        return root;
    }

}
