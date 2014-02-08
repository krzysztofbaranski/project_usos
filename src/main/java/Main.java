import app.Window;
import view.LoginPage;

import javax.swing.*;

/**
 * Created by krzysztof on 05/02/14.
 */
class Main { 
    public static void main(String[] args) {
        Window.mainFrame = new JFrame("LoginPage");
        Window.mainFrame.setContentPane(new LoginPage().getRoot());
        Window.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Window.mainFrame.pack();
        Window.mainFrame.setSize(800,500);
        Window.mainFrame.setVisible(true);
    }
}
