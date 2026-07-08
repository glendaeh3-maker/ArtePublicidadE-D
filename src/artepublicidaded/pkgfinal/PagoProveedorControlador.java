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
public class PagoProveedorControlador {
    // ===== Agregar pago a proveedor =====
    public static boolean agregar(PagoProovedor pago) {
        String sql = "INSERT INTO pago_proveedor (id_proveedor, fecha_pago, monto, metodo_pago) VALUES (?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pago.getIdProveedor());
            ps.setDate(2, java.sql.Date.valueOf(pago.getFechaPago()));
            ps.setDouble(3, pago.getMonto());
            ps.setString(4, pago.getMetodoPago());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar pago a proveedor: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar pagos a proveedores =====
    public static List<PagoProovedor> listar() {
        List<PagoProovedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago_proveedor";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PagoProovedor p = new PagoProovedor();
                p.setIdPagoProveedor(rs.getInt("id_pago_proveedor"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setFechaPago(rs.getDate("fecha_pago").toLocalDate());
                p.setMonto(rs.getDouble("monto"));
                p.setMetodoPago(rs.getString("metodo_pago"));
                lista.add(p);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar pagos a proveedores: " + e.getMessage());
        }
        return lista;
    }

    // ===== Eliminar pago a proveedor =====
    public static boolean eliminar(int idPagoProveedor) {
        String sql = "DELETE FROM pago_proveedor WHERE id_pago_proveedor = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPagoProveedor);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pago a proveedor: " + e.getMessage());
            return false;
        }
    }
}
