package controlador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.ProductoDAO;
import gui.productosGUI;

public class ControladorProducto implements ActionListener {

    //Variables globales    
    private int codigo;
    private String descripcion;
    private double precio;
    private int stock;

    //Instancias
    Producto producto = new Producto();
    ProductoDAO productoDAO = new ProductoDAO();
    productosGUI vista = new productosGUI();
    DefaultTableModel modeloTabla = new DefaultTableModel();

    public ControladorProducto(productosGUI _vista) {
        this.vista = _vista;
        vista.setVisible(true);
        AgregarEventos();
        listarTabla();       

    }

    private void AgregarEventos() {
        vista.getBtnAgregar().addActionListener(this);
       
        vista.getTblTablaProductos().addMouseListener(new MouseAdapter() {
            //Creamos el método
            @Override
            public void mouseClicked(MouseEvent e) {
                llenarCampos(e);
            }
        });

    }

    private void listarTabla() {
        String[] titulos = new String[]{"Codigo", "Nombre", "Precio", "Inventario"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = productoDAO.listar();
        for (Producto producto : listaProductos) {
            modeloTabla.addRow(new Object[]{
                producto.getCodigo(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock()
            });
        }
        vista.getTblTablaProductos().setModel(modeloTabla);
        vista.getTblTablaProductos().setPreferredSize(new Dimension(350, modeloTabla.getRowCount() * 16));
    }

    private void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        vista.getTxtNombre()
                .setText(vista.getTblTablaProductos().getModel()
                        .getValueAt(target.getSelectedRow(), 1)
                        .toString());  // para el nombre
        vista.getTxtPrecio()
                .setText(vista.getTblTablaProductos().getModel()
                        .getValueAt(target.getSelectedRow(), 2)
                        .toString());  // para el precio
        vista.getTxtInventario()
                .setText(vista.getTblTablaProductos().getModel()
                        .getValueAt(target.getSelectedRow(), 3)
                        .toString());  // para el inventario
    }

    private boolean validarDatos() {
        if ("".equals(vista.getTxtNombre().getText())
                || "".equals(vista.getTxtPrecio().getText())
                || "".equals(vista.getTxtInventario().getText())) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean cargarDatos() {
        try {
            descripcion = vista.getTxtNombre().getText();
            precio = Double.parseDouble(vista.getTxtPrecio().getText());
            stock = Integer.parseInt(vista.getTxtInventario().getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Los campos precio e inventario debe ser númericos",
                     "error", JOptionPane.ERROR);
            System.out.println("Error al cargar los Datos " + e);
            return false;
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtInventario().setText("");
        codigo = 0;
        descripcion = "";
        precio = 0;
        stock = 0;
    }
   
private void agregarProducto(){
    try{
        if(validarDatos()){
            if(cargarDatos()){
                Producto producto = new Producto(descripcion,precio,stock);
                productoDAO.Agregar(producto);
                 JOptionPane.showMessageDialog(null, "Registro agregado con éxito");
            }
        }
    }catch(Exception e){
        System.out.println("Error al Agregar (ControladorProducto)");
    }finally{
        listarTabla();
    }
}

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()== vista.getBtnAgregar()){
            agregarProducto();
        }
    }
}
