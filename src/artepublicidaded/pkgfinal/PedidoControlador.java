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
public class PedidoControlador {
    
        // ===== Agregar pedido =====
    public static boolean agregar(Pedido pedido) {
        String sql = "INSERT INTO pedido (fecha_pedido, fecha_entrega, cliente_id, " +
                     "empleado_id, estado, total) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(pedido.getFechaPedido()));
            ps.setDate(2, pedido.getFechaEntrega() != null ? 
                       java.sql.Date.valueOf(pedido.getFechaEntrega()) : null);
            ps.setInt(3, pedido.getClienteId());
            ps.setInt(4, pedido.getEmpleadoId());
            ps.setString(5, pedido.getEstado());
            ps.setDouble(6, pedido.getTotal());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar pedido: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar todos los pedidos =====
    public static List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setFechaPedido(rs.getDate("fecha_pedido").toLocalDate());
                p.setFechaEntrega(rs.getDate("fecha_entrega") != null ?
                                  rs.getDate("fecha_entrega").toLocalDate() : null);
                p.setClienteId(rs.getInt("cliente_id"));
                p.setEmpleadoId(rs.getInt("empleado_id"));
                p.setEstado(rs.getString("estado"));
                p.setTotal(rs.getDouble("total"));
                lista.add(p);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar pedido por ID =====
    public static Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setFechaPedido(rs.getDate("fecha_pedido").toLocalDate());
                p.setFechaEntrega(rs.getDate("fecha_entrega") != null ?
                                  rs.getDate("fecha_entrega").toLocalDate() : null);
                p.setClienteId(rs.getInt("cliente_id"));
                p.setEmpleadoId(rs.getInt("empleado_id"));
                p.setEstado(rs.getString("estado"));
                p.setTotal(rs.getDouble("total"));
                con.close();
                return p;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar estado del pedido =====
    public static boolean actualizarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE pedido SET estado=? WHERE id=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar pedido =====
    public static boolean eliminar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }
}

