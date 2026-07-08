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
public class MaterialControlador {
     // ===== Agregar material =====
    public static boolean agregar(Material material) {
        String sql = "INSERT INTO material (id_almacen, nombre_material, unidad_medida, stock_actual) VALUES (?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, material.getIdAlmacen());
            ps.setString(2, material.getNombreMaterial());
            ps.setString(3, material.getUnidadMedida());
            ps.setDouble(4, material.getStockActual());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar material: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar materiales =====
    public static List<Material> listar() {
        List<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM material";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Material m = new Material();
                m.setIdMaterial(rs.getInt("id_material"));
                m.setIdAlmacen(rs.getInt("id_almacen"));
                m.setNombreMaterial(rs.getString("nombre_material"));
                m.setUnidadMedida(rs.getString("unidad_medida"));
                m.setStockActual(rs.getDouble("stock_actual"));
                lista.add(m);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar materiales: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar material por ID =====
    public static Material buscarPorId(int idMaterial) {
        String sql = "SELECT * FROM material WHERE id_material = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMaterial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Material m = new Material();
                m.setIdMaterial(rs.getInt("id_material"));
                m.setIdAlmacen(rs.getInt("id_almacen"));
                m.setNombreMaterial(rs.getString("nombre_material"));
                m.setUnidadMedida(rs.getString("unidad_medida"));
                m.setStockActual(rs.getDouble("stock_actual"));
                con.close();
                return m;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar material: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar material =====
    public static boolean actualizar(Material material) {
        String sql = "UPDATE material SET nombre_material=?, unidad_medida=?, stock_actual=? WHERE id_material=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, material.getNombreMaterial());
            ps.setString(2, material.getUnidadMedida());
            ps.setDouble(3, material.getStockActual());
            ps.setInt(4, material.getIdMaterial());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar material: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar material =====
    public static boolean eliminar(int idMaterial) {
        String sql = "DELETE FROM material WHERE id_material = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMaterial);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar material: " + e.getMessage());
            return false;
        }
    }
}
