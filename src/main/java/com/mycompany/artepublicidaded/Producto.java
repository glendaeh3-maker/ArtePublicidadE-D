/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.artepublicidaded;

/**
 *
 * @author Genes
 */
public abstract class Producto {
    protected String codigo;
    protected String nombreProducto;
    protected String descripcion;
    protected double precioUnitario;

    public abstract double calcularCosto(int cantidad);

    public abstract void verDatos();

    // Nombre de la categoria a la que pertenece (CATEGORIA_PRODUCTO.NOMBRE_CATEGORIA)
    public abstract String getCategoria();

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}
