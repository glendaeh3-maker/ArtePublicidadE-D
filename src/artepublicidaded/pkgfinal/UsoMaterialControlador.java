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
public class UsoMaterialControlador {
   // ===== Agregar uso de material =====
    public static boolean agregar(UsoMaterial uso) {
        String sql = "INSERT INTO uso_material (id_detalle_pedido, id_material, id_empleado, cantidad_usada, fecha_uso) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uso.getIdDetallePedido());
            ps.setInt(2, uso.getIdMaterial());
            ps.setInt(3, uso.getIdEmpleado());
            ps.setDouble(4, uso.getCantidadUsada());
            ps.setDate(5, java.sql.Date.valueOf(uso.getFechaUso()));
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar uso de material: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar usos de material =====
    public static List<UsoMaterial> listar() {
        List<UsoMaterial> lista = new ArrayList<>();
        String sql = "SELECT * FROM uso_material";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsoMaterial u = new UsoMaterial();
                u.setIdUsoMaterial(rs.getInt("id_uso_material"));
                u.setIdDetallePedido(rs.getInt("id_detalle_pedido"));
                u.setIdMaterial(rs.getInt("id_material"));
                u.setIdEmpleado(rs.getInt("id_empleado"));
                u.setCantidadUsada(rs.getDouble("cantidad_usada"));
                u.setFechaUso(rs.getDate("fecha_uso").toLocalDate());
                lista.add(u);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar usos de material: " + e.getMessage());
        }
        return lista;
    }

    // ===== Eliminar uso de material =====
    public static boolean eliminar(int idUsoMaterial) {
        String sql = "DELETE FROM uso_material WHERE id_uso_material = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsoMaterial);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar uso de material: " + e.getMessage());
            return false;
        }
    }
}
