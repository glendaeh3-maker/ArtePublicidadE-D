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
    protected String nombre;
    protected String Apellido_paterno;
    protected String Apellido_materno;
    protected String Dni;
    protected String cargo;
    protected String telefono;
    protected String correo;

    private static int contador = 1;

    public Empleado() {
        this.id = contador;
        contador++;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.Apellido_paterno + " "+ this.Apellido_materno;
    }

    public void verDatos() {
        System.out.println("EMPLEADO ID: " + this.id
                + " NOMBRES: " + this.nombre
                + " APELLIDO PATERNO: " + this.Apellido_paterno
                + " APELLIDO MATERNO: " + this.Apellido_materno
                + "DNI" + this.Dni
                + " CARGO: " + this.cargo
                + " TEL: " + this.telefono
                + " CORREO: " + this.correo);
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getNombre() { return nombre; }
    public void setNombre(String nombres) { this.nombre = nombres; }

    public String getApellido_paterno() {
        return Apellido_paterno;
    }

    public void setApellido_paterno(String Apellido_paterno) {
        this.Apellido_paterno = Apellido_paterno;
    }

    public String getApellido_materno() {
        return Apellido_materno;
    }

    public void setApellido_materno(String Apellido_materno) {
        this.Apellido_materno = Apellido_materno;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String DNI) {
        this.Dni = DNI;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
