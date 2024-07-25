/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;


/**
 *
 * @author RCT
 */
/*

*/
public class ProductoDAO {

    ConexionBD conexion = new ConexionBD(); 
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List listar() {
        String sql = "select * from productos";
        List<Producto> lista = new ArrayList<>();
        try {
            con = conexion.ConectarBaseDeDAtos();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setPrecio(rs.getDouble(3));
                producto.setStock(rs.getInt(4));
                lista.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al Listar: " + e);
        }
        return lista;
    }

    public void Agregar(Producto producto) {
        String sql = "INSERT INTO productos (descripcion, precio, stock) VALUES  (?,?,?)";
        try {
            con = conexion.ConectarBaseDeDAtos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getDescripcion());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error en el método Agregar clase ProductoDAO ");            
        }
    }
    /*
    public void Borrar(Producto producto) {
    String sql = "DELETE FROM productos (codigo,descripcion, precio, stock) VALUES  (?,?,?,?)";
    try {
        con = conexion.ConectarBaseDeDAtos();
        ps = con.prepareStatement(sql);
        ps.setInt(1,);
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Registro eliminado exitosamente.");
        } else {
            System.out.println("No se pudo eliminar el registro.");
        }
    } catch (SQLException e) {
        System.err.println("Error en el método Borrar de la clase ProductoDAO.");
        e.printStackTrace();
    }
}*/
}


