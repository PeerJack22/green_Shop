import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton consultarProductoButton;
    private JLabel lbNombre;
    private JLabel lbDescripcion;
    private JLabel lbPrecio;
    private JLabel lbStock;
    private JLabel lbID;





    public adminVentana() {
        mc = new metodosCrud();

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
    }
}

