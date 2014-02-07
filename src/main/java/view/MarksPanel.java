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
 * Created by Użytkownik on 06.02.14.
 */
public class MarksPanel {
    private JTable table1;
    private JPanel root;
    private JScrollPane scroll1;
    Vector<Vector<Object>> v;

    public MarksPanel() {
        v = Utility.getData(Learning.getMarks(-1, User.person_id, false));

        TableModel dataModel = new AbstractTableModel() {
            private final Object[] columnNames = {"prowadzący", "grupa", "ocena", "opis", "uwagi", "data"};

            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            public int getColumnCount() {
                return 6;
            }

            public int getRowCount() {
                return v.size();
            }

            public Object getValueAt(int row, int col) {
                if(col == 0) return v.get(row).get(1) + " " + v.get(row).get(2);
                return v.get(row).get(col + 3);
            }
        };


        table1.setModel(dataModel);
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = table1.columnAtPoint(new Point(e.getX(), e.getY()));
                if(row == -1) return;
                if(col == 0) {
                    app.Window.mainFrame.setContentPane(new TeacherDetails((long) v.get(row).get(0)).getRoot());
                    app.Window.mainFrame.setVisible(true);
                    //TODO
                }
                if(col == 1) {
                    System.out.println(v.get(row).get(3));
                    app.Window.mainFrame.setContentPane(new GroupDetails((long) v.get(row).get(3)).getRoot());
                    app.Window.mainFrame.setVisible(true);
                }


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
