/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author Genes
 */
public class DetalleOrdenCompraControlador {
    public static boolean agregar(DetalleOrdenCompra detalle) {
        String sql = "INSERT INTO detalle_orden_compra (id_orden_compra, id_material, " +
                     "cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, detalle.getIdOrdenCompra());
            ps.setInt(2, detalle.getIdMaterial());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar detalle orden: " + e.getMessage());
            return false;
        }
    }

    public static List<DetalleOrdenCompra> listarPorOrden(int idOrdenCompra) {
        List<DetalleOrdenCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_orden_compra WHERE id_orden_compra = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idOrdenCompra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleOrdenCompra d = new DetalleOrdenCompra();
                d.setIdDetalleOrden(rs.getInt("id_detalle_orden"));
                d.setIdOrdenCompra(rs.getInt("id_orden_compra"));
                d.setIdMaterial(rs.getInt("id_material"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                d.setSubtotal(rs.getDouble("subtotal"));
                lista.add(d);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }
}
