import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Random;

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
                    JOptionPane.showMessageDialog(null, "Completa todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))) {

                    // Base de datos
                    MongoDatabase database = mongoClient.getDatabase("greenShop");
                    // Coleccion
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    // Crear la consulta para compararla con lo de la database
                    Document query = new Document("username", usuarioLogin).append("password", passwordLogin);
                    // Buscar el usuario
                    Document user = collection.find(query).first();

                    // Mensaje de confirmaciòn en consola
                    System.out.println("Conexión exitosa a la base de datos.");

                    if (user != null) {

                        //Consultar rol del usuario
                        String rol = user.getString("rol");

                        if ("admin".equalsIgnoreCase(rol)) {

                            // Abrir la ventana de administrador
                            JFrame ventanaAdmin = new JFrame("Admin Ventana");
                            ventanaAdmin.setContentPane(new adminVentana().adminPanel);
                            ventanaAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            ventanaAdmin.setSize(1360,768);
                            ventanaAdmin.setPreferredSize(new Dimension(1360,768));
                            ventanaAdmin.pack();
                            ventanaAdmin.setVisible(true);

                        } else if ("cliente".equalsIgnoreCase(rol)) {

                            //Abrir ventana cliente
                            String clienteId = generarClienteId();
                            JFrame ventanaCliente = new JFrame("Cliente Ventana");
                            ventanaCliente.setContentPane(new clienteVentana(clienteId).clientePanel);
                            ventanaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            ventanaCliente.setSize(1360,768);
                            ventanaCliente.setPreferredSize(new Dimension(1360,768));
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

    private String generarClienteId() {
        Random random = new Random();
        int numeroAleatorio = 1000 + random.nextInt(9000); // Para un número entre 1000 y 9000
        return "cl" + numeroAleatorio;
    }
}
