/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Genes
 */
public class ProductoControlador {
    
        // ===== Agregar producto =====
    public static boolean agregar(Producto producto) {
        String sql = "INSERT INTO producto (codigo, nombre_producto, descripcion, " +
                     "precio_unitario, categoria) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecioUnitario());
            ps.setString(5, producto.getCategoria());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar todos los productos =====
    public static List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            String categoria = rs.getString("categoria");
            Producto p;
            switch (categoria) {
                case "IMPRESION":
                p = new ProductoImpresion();
            break;
                case "SEÑALETICA":
                p = new ProductoSeñaletica();
            break;
                case "BTL":
                p = new ProductoPublicidadBTL();
            break;
            default:
                p = new ProductoDiseñoGrafico();
            break;
    }
        p.setCodigo(rs.getString("codigo"));
        p.setNombreProducto(rs.getString("nombre_producto"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecioUnitario(rs.getDouble("precio_unitario"));
        lista.add(p);
}
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar producto por codigo =====
    public static Producto buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM producto WHERE codigo = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        String categoria = rs.getString("categoria");
        Producto p;
        switch (categoria) {
        case "IMPRESION":
            p = new ProductoImpresion();
            break;
        case "SEÑALETICA":
            p = new ProductoSeñaletica();
            break;
        case "BTL":
            p = new ProductoPublicidadBTL();
            break;
        default:
            p = new ProductoDiseñoGrafico();
            break;
    }
        p.setCodigo(rs.getString("codigo"));
        p.setNombreProducto(rs.getString("nombre_producto"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecioUnitario(rs.getDouble("precio_unitario"));
        con.close();
        return p;
}
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar producto =====
    public static boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre_producto=?, descripcion=?, " +
                     "precio_unitario=?, categoria=? WHERE codigo=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioUnitario());
            ps.setString(4, producto.getCategoria());
            ps.setString(5, producto.getCodigo());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar producto =====
    public static boolean eliminar(String codigo) {
        String sql = "DELETE FROM producto WHERE codigo = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
