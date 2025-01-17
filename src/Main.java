import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new loginVentana().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.pack();
        frame.setVisible(true);
    }
}