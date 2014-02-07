package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by krzysztof on 05/02/14.
 */
class MainPanel {
    private JPanel root;

    public MainPanel() { slider1.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            labelek.setFont(new Font(labelek.getFont().getName(),Font.PLAIN,slider1.getValue()));
        }
    });}


    private JPanel menu;
    private JPanel rightPane;
    private JSlider slider1;
    private JLabel labelek;

    public JPanel getRightPane() {
        return rightPane;
    }

    public JPanel getRoot() {
        return root;
    }
}
