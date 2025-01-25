import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class clienteVentana extends loginVentana{
    public JPanel clientePanel;
    private JLabel textDialogo;
    private JLabel textTitulo;
    private JTable table1;
    private JLabel textNombreProducto;
    private JLabel textCantidad;
    private JTextField textNombre;
    private JTextField textCant;
    private JButton limpiarCamposButton;
    private JTable table2;
    private JButton agregarAlCarritoButton;
    private JButton finalizarCompraButton;


    private DefaultTableModel productosTabla;

    private DefaultTableModel carritoTablaModel;

    private List<Document> carrito; // Lista para almacenar los productos del carrito
    private String clienteId;


    public clienteVentana(String clienteId) {
        this.clienteId = clienteId;
        carrito = new ArrayList<>();

        // Configurar modelos de las tablas
        productosTabla = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio", "Stock"}, 0);
        carritoTablaModel = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Precio Unitario", "Subtotal"}, 0);

        table1.setModel(productosTabla);
        table2.setModel(carritoTablaModel);

        cargarProductos();



        limpiarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textNombre.setText("");
                textCant.setText("");
            }
        });
        agregarAlCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoAlCarrito();
            }
        });
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarCompra();
            }
        });
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

    private void agregarProductoAlCarrito() {
        try {
            String nombreProducto = textNombre.getText();
            int cantidad = Integer.parseInt(textCant.getText());

            boolean productoEncontrado = false;
            for (int i = 0; i < productosTabla.getRowCount(); i++) {
                if (productosTabla.getValueAt(i, 0).equals(nombreProducto)) {
                    double precioUnitario = (double) productosTabla.getValueAt(i, 2);
                    int stockDisponible = (int) productosTabla.getValueAt(i, 3);

                    if (cantidad > stockDisponible) {
                        JOptionPane.showMessageDialog(null, "Cantidad excede el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double subtotal = precioUnitario * cantidad;
                    carrito.add(new Document("producto_id", nombreProducto)
                            .append("cantidad", cantidad)
                            .append("precio_unitario", precioUnitario)
                            .append("subtotal", subtotal));

                    carritoTablaModel.addRow(new Object[]{nombreProducto, cantidad, precioUnitario, subtotal});
                    productoEncontrado = true;
                    break;
                }
            }

            if (!productoEncontrado) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar al carrito: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCarrito() {
        carrito.clear();
        carritoTablaModel.setRowCount(0);
    }

    private void finalizarCompra() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El carrito está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = carrito.stream().mapToDouble(producto -> producto.getDouble("subtotal")).sum();
        Document transaccion = new Document("_id", "t" + System.currentTimeMillis())
                .append("cliente_id", clienteId)
                .append("productos", carrito)
                .append("total", total)
                .append("fecha", LocalDateTime.now().toString());

        try (MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))) {
            MongoDatabase database = mongoClient.getDatabase("greenShop");
            MongoCollection<Document> transaccionesCollection = database.getCollection("transacciones");
            MongoCollection<Document> productosCollection = database.getCollection("productos");

            // Guardar la transacción en la colección "transacciones"
            transaccionesCollection.insertOne(transaccion);

            // Actualizar el stock en la colección "productos"
            for (Document producto : carrito) {
                String productoId = producto.getString("producto_id");
                int cantidadComprada = producto.getInteger("cantidad");

                // Actualizar el stock del producto
                productosCollection.updateOne(
                        new Document("nombre", productoId), // Buscar por nombre
                        new Document("$inc", new Document("stock", -cantidadComprada)) // Reducir el stock
                );
            }

            JOptionPane.showMessageDialog(null, "Compra finalizada con éxito. El stock ha sido actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCarrito();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al finalizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
