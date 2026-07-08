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
public class ServicioEspecialControlador {
    // ===== Agregar servicio especial =====
    public static boolean agregar(ServicioEspecial servicio) {
        String sql = "INSERT INTO servicio_especial (id_empleado, nombre_servicio, descripcion, costo) VALUES (?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, servicio.getIdEmpleado());
            ps.setString(2, servicio.getNombreServicio());
            ps.setString(3, servicio.getDescripcion());
            ps.setDouble(4, servicio.getCosto());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar servicio especial: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar servicios especiales =====
    public static List<ServicioEspecial> listar() {
        List<ServicioEspecial> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_especial";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServicioEspecial s = new ServicioEspecial();
                s.setIdServicio(rs.getInt("id_servicio"));
                s.setIdEmpleado(rs.getInt("id_empleado"));
                s.setNombreServicio(rs.getString("nombre_servicio"));
                s.setDescripcion(rs.getString("descripcion"));
                s.setCosto(rs.getDouble("costo"));
                lista.add(s);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar servicios especiales: " + e.getMessage());
        }
        return lista;
    }

    // ===== Actualizar servicio especial =====
    public static boolean actualizar(ServicioEspecial servicio) {
        String sql = "UPDATE servicio_especial SET nombre_servicio=?, descripcion=?, costo=? WHERE id_servicio=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, servicio.getNombreServicio());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getCosto());
            ps.setInt(4, servicio.getIdServicio());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar servicio especial: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar servicio especial =====
    public static boolean eliminar(int idServicio) {
        String sql = "DELETE FROM servicio_especial WHERE id_servicio = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idServicio);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar servicio especial: " + e.getMessage());
            return false;
        }
    }
}
