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
public class OrdenCompraControlador {
     // ===== Agregar orden de compra =====
    public static boolean agregar(OrdenCompra orden) {
        String sql = "INSERT INTO orden_compra (id_proveedor, id_empleado, fecha_orden, estado, total) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, orden.getIdProveedor());
            ps.setInt(2, orden.getIdEmpleado());
            ps.setDate(3, java.sql.Date.valueOf(orden.getFechaOrden()));
            ps.setString(4, orden.getEstado());
            ps.setDouble(5, orden.getTotal());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar orden de compra: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar órdenes de compra =====
    public static List<OrdenCompra> listar() {
        List<OrdenCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_compra";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrdenCompra o = new OrdenCompra();
                o.setIdOrdenCompra(rs.getInt("id_orden_compra"));
                o.setIdProveedor(rs.getInt("id_proveedor"));
                o.setIdEmpleado(rs.getInt("id_empleado"));
                o.setFechaOrden(rs.getDate("fecha_orden").toLocalDate());
                o.setEstado(rs.getString("estado"));
                o.setTotal(rs.getDouble("total"));
                lista.add(o);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar órdenes de compra: " + e.getMessage());
        }
        return lista;
    }

    // ===== Actualizar estado orden de compra =====
    public static boolean actualizarEstado(int idOrdenCompra, String estado) {
        String sql = "UPDATE orden_compra SET estado=? WHERE id_orden_compra=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, idOrdenCompra);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar orden de compra: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar orden de compra =====
    public static boolean eliminar(int idOrdenCompra) {
        String sql = "DELETE FROM orden_compra WHERE id_orden_compra = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idOrdenCompra);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar orden de compra: " + e.getMessage());
            return false;
        }
    }
}