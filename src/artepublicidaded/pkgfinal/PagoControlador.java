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
public class PagoControlador {
    // ===== Agregar pago =====
    public static boolean agregar(Pago pago) {
        String sql = "INSERT INTO pago (id_cliente, fecha_pago, monto, metodo_pago, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pago.getIdCliente());
            ps.setDate(2, java.sql.Date.valueOf(pago.getFechaPago()));
            ps.setDouble(3, pago.getMonto());
            ps.setString(4, pago.getMetodoPago());
            ps.setString(5, pago.getEstado());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar pago: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar pagos =====
    public static List<Pago> listar() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pago p = new Pago();
                p.setIdPago(rs.getInt("id_pago"));
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setFechaPago(rs.getDate("fecha_pago").toLocalDate());
                p.setMonto(rs.getDouble("monto"));
                p.setMetodoPago(rs.getString("metodo_pago"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar pagos: " + e.getMessage());
        }
        return lista;
    }
    // ===== Suma total de pagos completados (para el Dashboard del Admin) =====
    public static double sumaTotalCompletado() {
        double total = 0;
        for (Pago p : listar()) {
            if ("COMPLETADO".equalsIgnoreCase(p.getEstado())) {
                total += p.getMonto();
            }
        }
        return total;
    }

    // ===== Actualizar estado pago =====
    public static boolean actualizarEstado(int idPago, String estado) {
        String sql = "UPDATE pago SET estado=? WHERE id_pago=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, idPago);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado de pago: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar pago =====
    public static boolean eliminar(int idPago) {
        String sql = "DELETE FROM pago WHERE id_pago = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPago);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pago: " + e.getMessage());
            return false;
        }
    }
}
