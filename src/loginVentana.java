import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class loginVentana {
    private JTextField textLogin;
    private JButton buttonIngresar;
    private JLabel lbLogin;
    private JLabel lbUser;
    private JLabel lbPassword;
    public JPanel loginPanel;
    private JPasswordField passwordField1;
    private JLabel lbtitulo;

    public loginVentana() {
        buttonIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioLogin = textLogin.getText();
                String passwordLogin = new String(passwordField1.getPassword());

                if (usuarioLogin.isEmpty() || passwordLogin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))) {
                    // Obtener la base de datos
                    MongoDatabase database = mongoClient.getDatabase("greenShop");

                    // Obtener la colección
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    // Crear la consulta
                    Document query = new Document("username", usuarioLogin).append("password", passwordLogin);

                    // Buscar el usuario
                    Document user = collection.find(query).first();

                    System.out.println("Conexión exitosa a la base de datos.");

                    if (user != null) {
                        // Abrir la ventana de administrador
                        JFrame ventana = new JFrame("Admin Ventana");
                        ventana.setContentPane(new adminVentana().adminPanel);
                        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        ventana.setSize(600, 480);
                        ventana.setPreferredSize(new Dimension(600, 480));
                        ventana.pack();
                        ventana.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Acceso denegado. Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
