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
                        //Ver el rol del usuario
                        String rol = user.getString("rol");

                        if ("admin".equalsIgnoreCase(rol)) {
                            // Abrir la ventana de administrador
                            JFrame ventanaAdmin = new JFrame("Admin Ventana");
                            ventanaAdmin.setContentPane(new adminVentana().adminPanel);
                            ventanaAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            ventanaAdmin.setSize(1280, 720);
                            ventanaAdmin.setPreferredSize(new Dimension(1280, 720));
                            ventanaAdmin.pack();
                            ventanaAdmin.setVisible(true);
                        } else if ("cliente".equalsIgnoreCase(rol)) {
                            //Abrir ventana cliente
                            JFrame ventanaCliente = new JFrame("Cliente Ventana");
                            ventanaCliente.setContentPane(new clienteVentana().clientePanel);
                            ventanaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            ventanaCliente.setSize(1280, 720);
                            ventanaCliente.setPreferredSize(new Dimension(1280, 720));
                            ventanaCliente.pack();
                            ventanaCliente.setVisible(true);
                        } else {
                            //Si no hay un rol
                            JOptionPane.showMessageDialog(null, "Su rol no se reconoció o no lo tiene. Contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Acceso denegado el usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
