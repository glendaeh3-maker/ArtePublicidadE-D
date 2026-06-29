/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
    public class Empleado {

    protected int id;
    protected String nombres;
    protected String apellidos;
    protected String cargo;
    protected String telefono;
    protected String correo;

    private static int contador = 1;

    public Empleado() {
        this.id = contador;
        contador++;
    }

    public String getNombreCompleto() {
        return this.nombres + " " + this.apellidos;
    }

    public void verDatos() {
        System.out.println("EMPLEADO ID: " + this.id
                + " NOMBRES: " + this.nombres
                + " APELLIDOS: " + this.apellidos
                + " CARGO: " + this.cargo
                + " TEL: " + this.telefono
                + " CORREO: " + this.correo);
    }

    // Getters y Setters
    public int getId() { return id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
