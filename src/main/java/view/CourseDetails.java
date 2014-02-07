package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Użytkownik on 07.02.14.
 */
public class CourseDetails {
    private JTextPane nazwaTextPane;
    private JPanel root;

    public CourseDetails(long courseId) {
        nazwaTextPane.setText(courseId + "");
    }


    public JPanel getRoot() {

        return root;
    }

}
