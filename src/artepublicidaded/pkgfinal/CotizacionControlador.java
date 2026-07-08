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
public class CotizacionControlador {
    // ===== Agregar cotización =====
    public static boolean agregar(Cotizacion cotizacion) {
        String sql = "INSERT INTO cotizacion (id_cliente, id_empleado, fecha_cotizacion, estado, total_estimado) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cotizacion.getIdCliente());
            ps.setInt(2, cotizacion.getIdEmpleado());
            ps.setDate(3, java.sql.Date.valueOf(cotizacion.getFechaCotizacion()));
            ps.setString(4, cotizacion.getEstado());
            ps.setDouble(5, cotizacion.getTotalEstimado());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar cotización: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar cotizaciones =====
    public static List<Cotizacion> listar() {
        List<Cotizacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM cotizacion";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cotizacion c = new Cotizacion();
                c.setIdCotizacion(rs.getInt("id_cotizacion"));
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setIdEmpleado(rs.getInt("id_empleado"));
                c.setFechaCotizacion(rs.getDate("fecha_cotizacion").toLocalDate());
                c.setEstado(rs.getString("estado"));
                c.setTotalEstimado(rs.getDouble("total_estimado"));
                lista.add(c);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar cotizaciones: " + e.getMessage());
        }
        return lista;
    }

    // ===== Actualizar estado cotización =====
    public static boolean actualizarEstado(int idCotizacion, String estado) {
        String sql = "UPDATE cotizacion SET estado=? WHERE id_cotizacion=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, idCotizacion);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cotización: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar cotización =====
    public static boolean eliminar(int idCotizacion) {
        String sql = "DELETE FROM cotizacion WHERE id_cotizacion = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCotizacion);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cotización: " + e.getMessage());
            return false;
        }
    }
}
