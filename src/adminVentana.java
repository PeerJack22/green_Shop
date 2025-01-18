import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import java.util.List;

public class adminVentana extends loginVentana{

    public metodosCrud mc;

    public JPanel adminPanel;
    private JTextField textNombre;
    private JTextField textDes;
    private JTextField textPrecio;
    private JTextField textStock;
    private JTextField textID;
    private JButton agregarProductoButton;
    private JButton actualizarProductoButton;
    private JButton eliminarProductoButton;
    private JButton actualizarTablaButton;
    private JLabel lbNombre;
    private JLabel lbDescripcion;
    private JLabel lbPrecio;
    private JLabel lbStock;
    private JLabel lbID;
    private JTable table1;
    private JButton limpiarCamposButton;


    public adminVentana() {
        mc = new metodosCrud();

        // Configurar modelo para la tabla
        table1.setModel(new DefaultTableModel(
                new Object[][]{}, // Datos iniciales vacíos
                new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock"} // Nombres de las columnas
        ));

        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textNombre.getText();
                String des = textDes.getText();
                double precio = Double.parseDouble(textPrecio.getText());
                int stock = Integer.parseInt(textStock.getText());

                mc.crearProducto(nombre, des, precio, stock);
            }
        });
        actualizarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = textID.getText();
                String nombre = textNombre.getText();
                String des = textDes.getText();
                double precio = Double.parseDouble(textPrecio.getText());
                int stock = Integer.parseInt(textStock.getText());

                mc.actualizarProducto(id, nombre, des, precio, stock);
            }
        });
        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = textID.getText();
                mc.eliminarProducto(id);
            }
        });

        actualizarTablaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductosEnTabla();
            }
        });
        limpiarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textID.setText("");
                textNombre.setText("");
                textDes.setText("");
                textPrecio.setText("");
                textStock.setText("");
            }
        });
    }


        private void cargarProductosEnTabla() {
            // Obtener el modelo de la tabla
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0); // Limpiar las filas actuales de la tabla

            // Obtener productos desde la base de datos
            List<Document> productos = mc.leerProductos();

            // Agregar cada producto como una fila en la tabla
            for (Document producto : productos) {
                model.addRow(new Object[]{
                        producto.getObjectId("_id").toString(),
                        producto.getString("nombre"),
                        producto.getString("descripcion"),
                        producto.getDouble("precio"),
                        producto.getInteger("stock")
                });
            }
        }

}

