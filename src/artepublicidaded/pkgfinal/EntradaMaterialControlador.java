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
public class EntradaMaterialControlador {
    // ===== Agregar entrada de material =====
    public static boolean agregar(EntradaMaterial entrada) {
        String sql = "INSERT INTO entrada_material (id_orden_compra, id_material, id_empleado, fecha_entrada, cantidad_entrada) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, entrada.getIdOrdenCompra());
            ps.setInt(2, entrada.getIdMaterial());
            ps.setInt(3, entrada.getIdEmpleado());
            ps.setDate(4, java.sql.Date.valueOf(entrada.getFechaEntrada()));
            ps.setDouble(5, entrada.getCantidadEntrada());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar entrada de material: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar entradas de material =====
    public static List<EntradaMaterial> listar() {
        List<EntradaMaterial> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrada_material";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EntradaMaterial e = new EntradaMaterial();
                e.setIdEntrada(rs.getInt("id_entrada"));
                e.setIdOrdenCompra(rs.getInt("id_orden_compra"));
                e.setIdMaterial(rs.getInt("id_material"));
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setFechaEntrada(rs.getDate("fecha_entrada").toLocalDate());
                e.setCantidadEntrada(rs.getDouble("cantidad_entrada"));
                lista.add(e);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar entradas de material: " + e.getMessage());
        }
        return lista;
    }

    // ===== Eliminar entrada de material =====
    public static boolean eliminar(int idEntrada) {
        String sql = "DELETE FROM entrada_material WHERE id_entrada = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEntrada);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar entrada de material: " + e.getMessage());
            return false;
        }
    }
}
