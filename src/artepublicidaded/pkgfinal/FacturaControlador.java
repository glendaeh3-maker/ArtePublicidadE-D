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
public class FacturaControlador {
     // ===== Agregar factura =====
    public static boolean agregar(Factura factura) {
        String sql = "INSERT INTO factura (id_cliente, numero_factura, fecha_emision) VALUES (?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, factura.getIdCliente());
            ps.setString(2, factura.getNumeroFactura());
            ps.setDate(3, java.sql.Date.valueOf(factura.getFechaEmision()));
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar factura: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar facturas =====
    public static List<Factura> listar() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM factura";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setIdCliente(rs.getInt("id_cliente"));
                f.setNumeroFactura(rs.getString("numero_factura"));
                f.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                lista.add(f);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar factura por número =====
    public static Factura buscarPorNumero(String numeroFactura) {
        String sql = "SELECT * FROM factura WHERE numero_factura = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setIdCliente(rs.getInt("id_cliente"));
                f.setNumeroFactura(rs.getString("numero_factura"));
                f.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                con.close();
                return f;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar factura: " + e.getMessage());
        }
        return null;
    }

    // ===== Eliminar factura =====
    public static boolean eliminar(int idFactura) {
        String sql = "DELETE FROM factura WHERE id_factura = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idFactura);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }
}