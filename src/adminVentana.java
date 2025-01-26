import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import java.util.List;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class adminVentana extends loginVentana {

    public metodosCrud mc;  // Utilizar los metodos crud
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
    private JButton generarInformeButton;


    public adminVentana() {
        mc = new metodosCrud(); // para utilizar los metodos crud (objeto)

        // Configurar como se va a ver la tabla con los encabezados que va a tener
        table1.setModel(new DefaultTableModel(
                //Inicializa vacios
                new Object[][]{},

                //Nombres para las columnas de la tablasç
                new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock"}
        ));

        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener datos
                String nombre = textNombre.getText();
                String des = textDes.getText();
                double precio = Double.parseDouble(textPrecio.getText());
                int stock = Integer.parseInt(textStock.getText());

                //Poner los datos en la database
                mc.crearProducto(nombre, des, precio, stock);
            }
        });

        actualizarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener datos
                String id = textID.getText();
                String nombre = textNombre.getText();
                String des = textDes.getText();
                double precio = Double.parseDouble(textPrecio.getText());
                int stock = Integer.parseInt(textStock.getText());

                //Metodo de actualizar tabla
                mc.actualizarProducto(id, nombre, des, precio, stock);
            }
        });

        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la id para eliminar el registro
                String id = textID.getText();
                mc.eliminarProducto(id);
            }
        });

        actualizarTablaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductosEnTabla(); // Actualizar la tabla para mostrar nuevos productos
            }
        });

        limpiarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Campos limpios para agregar otro producto
                textID.setText("");
                textNombre.setText("");
                textDes.setText("");
                textPrecio.setText("");
                textStock.setText("");
            }
        });

        generarInformeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se crea el documento PDF
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                try {
                    // Se crea un escritor para el PDF
                    PdfWriter.getInstance(document, new FileOutputStream("informe.pdf"));

                    // Abrir el documento
                    document.open();

                    // Encabezado para el informe
                    Paragraph parrafo1 = new Paragraph("Informe de productos en la GreenShop\n\n");
                    parrafo1.setAlignment(com.lowagie.text.Element.ALIGN_CENTER); // Para centrar el texto
                    document.add(parrafo1); // se agrega al PDF

                    // Obtener la fecha y hora actual
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fechaHora = dateFormat.format(new Date());

                    // Agregar fecha al informe
                    Paragraph fechaParrafo = new Paragraph("Fecha y hora: " + fechaHora + "\n\n");
                    fechaParrafo.setAlignment(com.lowagie.text.Element.ALIGN_LEFT); // Para poner la fecha en la izquierda
                    document.add(fechaParrafo);

                    // Crear la tabla
                    PdfPTable pdfTable = new PdfPTable(table1.getColumnCount()); // Obtenemos el nùmero de columnas

                    // Se añade los encabezados de la tabla
                    for (int i = 0; i < table1.getColumnCount(); i++) {
                        pdfTable.addCell(table1.getColumnName(i)); //Se ponen los nombres de cada columna en la tabla
                    }

                    // Añadir las filas de la tabla al documento
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            pdfTable.addCell(model.getValueAt(i, j).toString());
                        }
                    }

                    // Agregar la tabla al documento
                    document.add(pdfTable);

                    document.close();

                    // Mensaje de confirmacion
                    JOptionPane.showMessageDialog(null, "Informe generado correctamente :)", "Mensaje de confirmación", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el informe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void cargarProductosEnTabla() {
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Limpiar las filas de ese momento

        // Leer lols productos de la database
        List<Document> productos = mc.leerProductos();

        // Agregar cada producto
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

