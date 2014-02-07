package view;

import app.Window;
import controler.Utility;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by krzysztof on 06/02/14.
 */
public class SearchResultPanel {
    private JTable table1;
    private JPanel root;
    private String query;
    private Vector<Vector<Object>> ret;

    public SearchResultPanel(final String[] columnNames, String _query) {
        query = _query;
        try {
            ret = Utility.getDataWithException(query);
        } catch(SQLException e) {
            JOptionPane.showConfirmDialog(Window.mainFrame,"Błąd zapytania","Error",JOptionPane.ERROR_MESSAGE);
        }

        if(ret == null) ret = new Vector<>();

        TableModel dataModel = new AbstractTableModel() {
            @Override
            public int getColumnCount() {
                if(ret.firstElement() != null)
                    return ret.firstElement().size();
                else
                    return 0;
            }

            @Override
            public int getRowCount() {
                return ret.size();
            }

            @Override
            public Object getValueAt(int row, int col) {
                return ret.elementAt(row).elementAt(col);
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
        };

        table1.setModel(dataModel);

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();

                    Window.mainFrame.setContentPane(new AccountPanel((String)target.getValueAt(row,target.getColumnCount() - 2)).getRoot());
                    Window.mainFrame.setVisible(true);
                }
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }
}
