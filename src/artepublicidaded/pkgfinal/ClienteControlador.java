package artepublicidaded.pkgfinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Genes
 */
public class ClienteControlador {
// ===== Agregar cliente =====
    public static boolean agregar(Cliente cliente) {
        String sql = "INSERT INTO cliente (usuario_id, dni, nombre, apellido_paterno, " +
                     "apellido_materno, telefono, correo, direccion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getDni());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getApellido_paterno());
            ps.setString(5, cliente.getApellido_materno());
            ps.setString(6, cliente.getTelefono());
            ps.setString(7, cliente.getCorreo());
            ps.setString(8, cliente.getDireccion());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar todos los clientes =====
    public static List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setDni(rs.getString("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido_paterno(rs.getString("apellido_paterno"));
                c.setApellido_materno(rs.getString("apellido_materno"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                lista.add(c);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar cliente por DNI =====
    public static Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM cliente WHERE dni = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setDni(rs.getString("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido_paterno(rs.getString("apellido_paterno"));
                c.setApellido_materno(rs.getString("apellido_materno"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                con.close();
                return c;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar cliente =====
    public static boolean actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre=?, apellido_paterno=?, apellido_materno=?, " +
                     "telefono=?, correo=?, direccion=? WHERE dni=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido_paterno());
            ps.setString(3, cliente.getApellido_materno());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getDireccion());
            ps.setString(7, cliente.getDni());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar cliente =====
    public static boolean eliminar(String dni) {
        String sql = "DELETE FROM cliente WHERE dni = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
