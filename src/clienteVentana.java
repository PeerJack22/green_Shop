import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class clienteVentana extends loginVentana{
    public JPanel clientePanel;
    private JLabel textDialogo;
    private JLabel textTitulo;
    private JTable table1;
    private JLabel textNombreProducto;
    private JLabel textCantidad;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTable table2;
    private JButton button4;

    private DefaultTableModel productosTabla;

    public clienteVentana() {
        // Configurar modelos de las tablas
        productosTabla = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio", "Stock"}, 0);
        table1.setModel(productosTabla);
        cargarProductos();
    }



    private void cargarProductos() {
        productosTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        try (MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))) {
            MongoDatabase database = mongoClient.getDatabase("greenShop");
            MongoCollection<Document> collection = database.getCollection("productos");

            for (Document producto : collection.find()) {
                String nombre = producto.getString("nombre");
                String descripcion = producto.getString("descripcion");
                double precio = producto.getDouble("precio");
                int stock = producto.getInteger("stock");

                productosTabla.addRow(new Object[]{nombre, descripcion, precio, stock});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
