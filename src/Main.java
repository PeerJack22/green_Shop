import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventanaLogin = new JFrame("Login");
            ventanaLogin.setContentPane(new loginVentana().loginPanel);
            ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventanaLogin.setSize(1360, 768);
            ventanaLogin.setLocationRelativeTo(null);
            ventanaLogin.setVisible(true);
        });
    }
}