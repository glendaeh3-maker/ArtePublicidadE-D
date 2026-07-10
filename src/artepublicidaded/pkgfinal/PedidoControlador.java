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
             if (pedido.getEmpleadoId() == 0) {
            ps.setNull(4, java.sql.Types.INTEGER);
            } else {
            ps.setInt(4, pedido.getEmpleadoId());
            }
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
    // ===== Actualizar estado y asignar empleado que atiende el pedido =====
    public static boolean actualizarEstadoYEmpleado(int id, String nuevoEstado, int empleadoId) {
    String sql = "UPDATE pedido SET estado=?, empleado_id=? WHERE id=?";
    try {
        Connection con = ConexionBD.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nuevoEstado);
        if (empleadoId == 0) {
            ps.setNull(2, java.sql.Types.INTEGER);
        } else {
            ps.setInt(2, empleadoId);
        }
        ps.setInt(3, id);
        ps.executeUpdate();
        con.close();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al actualizar estado/empleado: " + e.getMessage());
        return false;
    }
}
     public static int contarPorEstado(String estado) {
        int contador = 0;
        for (Pedido p : listar()) {
            if (p.getEstado() != null && p.getEstado().equalsIgnoreCase(estado)) {
                contador++;
            }
        }
        return contador;
    }

    // Suma el total de los pedidos registrados hoy (no cuenta los cancelados)
    public static double sumaVentasHoy() {
        double total = 0;
        String hoy = java.time.LocalDate.now().toString();
        for (Pedido p : listar()) {
            if (hoy.equals(p.getFechaPedido()) && !"CANCELADO".equalsIgnoreCase(p.getEstado())) {
                total += p.getTotal();
            }
        }
        return total;
    }

    // Agrupa la cantidad de pedidos por mes y por estado, para el gráfico "Pedidos por Estado".
    // Devuelve un mapa ordenado cronológicamente: mes (ej. "Ene") -> estado -> cantidad
    public static java.util.Map<String, java.util.Map<String, Integer>> conteoPorMesYEstado() {
        String[] nombresMes = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
                                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

        // Primero agrupamos con clave "yyyy-MM" para poder ordenar cronológicamente
        java.util.Map<String, java.util.Map<String, Integer>> agrupadoPorClave = new java.util.TreeMap<>();
        for (Pedido p : listar()) {
            if (p.getFechaPedido() == null) continue;
            java.time.LocalDate fecha = java.time.LocalDate.parse(p.getFechaPedido());
            String clave = String.format("%04d-%02d", fecha.getYear(), fecha.getMonthValue());
            agrupadoPorClave.putIfAbsent(clave, new java.util.LinkedHashMap<>());

            String estado = (p.getEstado() != null) ? p.getEstado().toUpperCase() : "PENDIENTE";
            java.util.Map<String, Integer> porEstado = agrupadoPorClave.get(clave);
            porEstado.put(estado, porEstado.getOrDefault(estado, 0) + 1);
        }

        // Ahora convertimos la clave "yyyy-MM" a etiqueta legible (Ene, Feb, ...) manteniendo el orden
        java.util.Map<String, java.util.Map<String, Integer>> resultado = new java.util.LinkedHashMap<>();
        for (String clave : agrupadoPorClave.keySet()) {
            int mes = Integer.parseInt(clave.substring(5, 7));
            resultado.put(nombresMes[mes - 1], agrupadoPorClave.get(clave));
        }
        return resultado;
    }
// Cuenta cuántos pedidos se registraron hoy (sin importar el estado)
    public static int contarPedidosHoy() {
        int contador = 0;
        String hoy = java.time.LocalDate.now().toString();
        for (Pedido p : listar()) {
            if (hoy.equals(p.getFechaPedido())) {
                contador++;
            }
        }
        return contador;
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

