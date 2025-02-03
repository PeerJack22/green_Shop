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
    private JButton generarInformeButton;
    private JButton regresarButton;


    public adminVentana() {
        mc = new metodosCrud(); // para utilizar los metodos crud (objeto)

        // Configurar como se va a ver la tabla con los encabezados que va a tener
        table1.setModel(new DefaultTableModel(
                //Inicializa vacios
                new Object[][]{},

                //Nombres para las columnas de la tablasç
                new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock"}
        ));

        table1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                //Tener los dstos de la fila seleccionada
                int selectedRow = table1.getSelectedRow();

                //Poner los valores en los en los textfields
                textID.setText(table1.getValueAt(selectedRow, 0).toString());
                textNombre.setText(table1.getValueAt(selectedRow, 1).toString());
                textDes.setText(table1.getValueAt(selectedRow, 2).toString());
                textPrecio.setText(table1.getValueAt(selectedRow, 3).toString());
                textStock.setText(table1.getValueAt(selectedRow, 4).toString());
            }
        });

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
                // Limpiar para agregar otro producto
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
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar informe como...");

                // Filtro para archivos pdf
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));

                // Ventana para el guardado
                int userSelection = fileChooser.showSaveDialog(null);

                // Verificscion de seleccion de ubicacion
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    // Asegurarnos que el archivo sea pdf
                    if (!filePath.toLowerCase().endsWith(".pdf")) {
                        filePath += ".pdf";
                    }

                    // Crear el documento
                    com.lowagie.text.Document document = new com.lowagie.text.Document();
                    try {
                        // Crear un escritor
                        PdfWriter.getInstance(document, new FileOutputStream(filePath));

                        // Abrir el documento
                        document.open();

                        // Pomer el encabezado
                        Paragraph parrafo1 = new Paragraph("Informe de productos  Green Root\n\n");
                        parrafo1.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                        document.add(parrafo1);

                        // Fecha y hora
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String fechaHora = dateFormat.format(new Date());
                        Paragraph fechaParrafo = new Paragraph("Fecha y hora: " + fechaHora + "\n\n");
                        fechaParrafo.setAlignment(com.lowagie.text.Element.ALIGN_LEFT);
                        document.add(fechaParrafo);

                        // Creacion de la tabla
                        PdfPTable pdfTable = new PdfPTable(table1.getColumnCount());

                        // Se añade los encabezados para la tabla
                        for (int i = 0; i < table1.getColumnCount(); i++) {
                            pdfTable.addCell(table1.getColumnName(i));
                        }

                        // Se añade las filas para la tabla
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            for (int j = 0; j < model.getColumnCount(); j++) {
                                pdfTable.addCell(model.getValueAt(i, j).toString());
                            }
                        }

                        // Agregamos la tabla para el documento
                        document.add(pdfTable);
                        document.close();

                        // Mensaje de confirmación
                        JOptionPane.showMessageDialog(null, "Informe guardado correctamente en:\n" + filePath, "Mensaje de confirmación", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al generar el informe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ventanaLogin = new JFrame("Login");
                ventanaLogin.setContentPane(new loginVentana().loginPanel);
                ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ventanaLogin.setSize(1360, 768);
                ventanaLogin.setLocationRelativeTo(null);
                ventanaLogin.setVisible(true);

                ((JFrame) SwingUtilities.getWindowAncestor(adminPanel)).dispose();

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

