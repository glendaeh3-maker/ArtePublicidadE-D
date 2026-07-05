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
public class EmpleadoControlador {

     public static boolean agregar(Empleado empleado) {
        String sql = "INSERT INTO empleado (usuario_id, dni, nombre, apellido_paterno, " +
                     "apellido_materno, telefono, correo, cargo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empleado.getId());
            ps.setString(2, empleado.getDni());
            ps.setString(3, empleado.getNombre());
            ps.setString(4, empleado.getApellido_paterno());
            ps.setString(5, empleado.getApellido_materno());
            ps.setString(6, empleado.getTelefono());
            ps.setString(7, empleado.getCorreo());
            ps.setString(8, empleado.getCargo());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }

    // ===== Listar todos los empleados =====
    public static List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setDni(rs.getString("dni"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido_paterno(rs.getString("apellido_paterno"));
                e.setApellido_materno(rs.getString("apellido_materno"));
                e.setTelefono(rs.getString("telefono"));
                e.setCorreo(rs.getString("correo"));
                e.setCargo(rs.getString("cargo"));
                lista.add(e);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    // ===== Buscar empleado por DNI =====
    public static Empleado buscarPorDni(String dni) {
        String sql = "SELECT * FROM empleado WHERE dni = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setDni(rs.getString("dni"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido_paterno(rs.getString("apellido_paterno"));
                e.setApellido_materno(rs.getString("apellido_materno"));
                e.setTelefono(rs.getString("telefono"));
                e.setCorreo(rs.getString("correo"));
                e.setCargo(rs.getString("cargo"));
                con.close();
                return e;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }

    // ===== Actualizar empleado =====
    public static boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleado SET nombre=?, apellido_paterno=?, apellido_materno=?, " +
                     "telefono=?, correo=?, cargo=? WHERE dni=?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido_paterno());
            ps.setString(3, empleado.getApellido_materno());
            ps.setString(4, empleado.getTelefono());
            ps.setString(5, empleado.getCorreo());
            ps.setString(6, empleado.getCargo());
            ps.setString(7, empleado.getDni());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    // ===== Eliminar empleado =====
    public static boolean eliminar(String dni) {
        String sql = "DELETE FROM empleado WHERE dni = ?";
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}