/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.artepublicidaded;

/**
 *
 * @author Genes
 */
public class Cliente {
    protected int id;
    protected String dni;
    protected String nombre;
    protected String apellido_paterno;
    protected String apellido_materno;
    protected String telefono;
    protected String correo;
    protected String direccion;
    
    private static int contador = 1;

    public Cliente() {
        this.id = contador;
        contador++;
    }

    public void verDatos() {
        System.out.println("CLIENTE ID: " + this.id
                + " DNI: " + this.dni
                + " NOMBRE: " + this.nombre
                + " TEL: " + this.telefono
                + " CORREO: " + this.correo
                + " DIRECCION: " + this.direccion);
    }

    // Getters y Setters
    public int getId() { return id; }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}