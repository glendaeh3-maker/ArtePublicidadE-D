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
public class AlmacenControlador {
      // ===== Agregar almacén =====
    public static boolean agregar(Almacen almacen) {
        String sql = "INSERT INTO almacen (nombre, direccion) VALUES (?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, almacen.getNombre());
            ps.setString(2, almacen.getDireccion());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar almacén: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar almacenes =====
    public static List<Almacen> listar() {
        List<Almacen> lista = new ArrayList<>();
        String sql = "SELECT * FROM almacen";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Almacen a = new Almacen();
                a.setIdAlmacen(rs.getInt("id_almacen"));
                a.setNombre(rs.getString("nombre"));
                a.setDireccion(rs.getString("direccion"));
                lista.add(a);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar almacenes: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar almacén por ID =====
    public static Almacen buscarPorId(int idAlmacen) {
        String sql = "SELECT * FROM almacen WHERE id_almacen = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlmacen);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Almacen a = new Almacen();
                a.setIdAlmacen(rs.getInt("id_almacen"));
                a.setNombre(rs.getString("nombre"));
                a.setDireccion(rs.getString("direccion"));
                con.close();
                return a;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar almacén: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar almacén =====
    public static boolean actualizar(Almacen almacen) {
        String sql = "UPDATE almacen SET nombre=?, direccion=? WHERE id_almacen=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, almacen.getNombre());
            ps.setString(2, almacen.getDireccion());
            ps.setInt(3, almacen.getIdAlmacen());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar almacén: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar almacén =====
    public static boolean eliminar(int idAlmacen) {
        String sql = "DELETE FROM almacen WHERE id_almacen = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlmacen);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar almacén: " + e.getMessage());
            return false;
        }
    }
}
