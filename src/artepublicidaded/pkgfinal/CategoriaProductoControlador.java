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
public class CategoriaProductoControlador {
    // ===== Agregar categoría =====
    public static boolean agregar(CategoriaProducto categoria) {
        String sql = "INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES (?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, categoria.getNombreCategoria());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar categoría: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar categorías =====
    public static List<CategoriaProducto> listar() {
        List<CategoriaProducto> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria_producto";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoriaProducto c = new CategoriaProducto();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNombreCategoria(rs.getString("nombre_categoria"));
                c.setDescripcion(rs.getString("descripcion"));
                lista.add(c);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }
        return lista;
    }

    // ===== Eliminar categoría =====
    public static boolean eliminar(int idCategoria) {
        String sql = "DELETE FROM categoria_producto WHERE id_categoria = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCategoria);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }
}
