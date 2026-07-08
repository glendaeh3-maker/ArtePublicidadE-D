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
public class ProovedorControlador {
     // ===== Agregar proveedor =====
    public static boolean agregar(Proovedor proveedor) {
        String sql = "INSERT INTO proveedor (ruc, razon_social, telefono, direccion) VALUES (?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getRuc());
            ps.setString(2, proveedor.getRazonSocial());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getDireccion());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar proveedor: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar proveedores =====
    public static List<Proovedor> listar() {
        List<Proovedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proovedor p = new Proovedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setRuc(rs.getString("ruc"));
                p.setRazonSocial(rs.getString("razon_social"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                lista.add(p);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar proveedor por RUC =====
    public static Proovedor buscarPorRuc(String ruc) {
        String sql = "SELECT * FROM proveedor WHERE ruc = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ruc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Proovedor p = new Proovedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setRuc(rs.getString("ruc"));
                p.setRazonSocial(rs.getString("razon_social"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                con.close();
                return p;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar proveedor: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar proveedor =====
    public static boolean actualizar(Proovedor proveedor) {
        String sql = "UPDATE proveedor SET razon_social=?, telefono=?, direccion=? WHERE ruc=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getRazonSocial());
            ps.setString(2, proveedor.getTelefono());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getRuc());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar proveedor =====
    public static boolean eliminar(int idProveedor) {
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
}
