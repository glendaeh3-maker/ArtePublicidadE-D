/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Genes
 */
public class UsuarioControlador {
        public static Usuario validarLogin(String nombreUsuario, String contrasena) {
            String sql = "SELECT * FROM usuario WHERE nombre_usuario = ? AND contrasena = ?";
    try {
        Connection con = ConexionBD.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreUsuario);
        ps.setString(2, contrasena);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setNombreUsuario(rs.getString("nombre_usuario"));
            u.setRol(rs.getString("rol"));
            con.close();
            return u; // Devuelve el objeto completo
        }
        con.close();
    } catch (SQLException e) {
        System.out.println("Error en validarLogin: " + e.getMessage());
    }
    return null;
}

    public static boolean registrarCliente(String dni, String nombre, String apellidoP,
            String apellidoM, String telefono, String correo, String direccion,
            String nombreUsuario, String contrasena) {

        String sqlVerificar = "SELECT id FROM usuario WHERE nombre_usuario = ? OR dni = ?";
        String sqlUsuario = "INSERT INTO usuario (dni, nombre, apellido_paterno, apellido_materno, " +
                           "telefono, correo, direccion, nombre_usuario, contrasena, rol) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'CLIENTE')";
        String sqlCliente = "INSERT INTO cliente (usuario_id, dni, nombre, apellido_paterno, " +
                           "apellido_materno, telefono, correo, direccion) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = ConexionBD.conectar();

            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);
            psVerificar.setString(1, nombreUsuario);
            psVerificar.setString(2, dni);
            ResultSet rs = psVerificar.executeQuery();
            if (rs.next()) {
                con.close();
                return false;
            }

            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, dni);
            psUsuario.setString(2, nombre);
            psUsuario.setString(3, apellidoP);
            psUsuario.setString(4, apellidoM);
            psUsuario.setString(5, telefono);
            psUsuario.setString(6, correo);
            psUsuario.setString(7, direccion);
            psUsuario.setString(8, nombreUsuario);
            psUsuario.setString(9, contrasena);
            psUsuario.executeUpdate();

            ResultSet keys = psUsuario.getGeneratedKeys();
            int usuarioId = 0;
            if (keys.next()) {
                usuarioId = keys.getInt(1);
            }

            PreparedStatement psCliente = con.prepareStatement(sqlCliente);
            psCliente.setInt(1, usuarioId);
            psCliente.setString(2, dni);
            psCliente.setString(3, nombre);
            psCliente.setString(4, apellidoP);
            psCliente.setString(5, apellidoM);
            psCliente.setString(6, telefono);
            psCliente.setString(7, correo);
            psCliente.setString(8, direccion);
            psCliente.executeUpdate();

            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error en registrarCliente: " + e.getMessage());
            return false;
        }
    }
}
