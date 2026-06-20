/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.artepublicidaded;

/**
 *
 * @author Genes
 */
public class ProductoSeñaletica extends Producto{
    
    private boolean requiereInstalacion;
    private static final double COSTO_INSTALACION = 50.00;

    public ProductoSeñaletica() {
    }

    @Override
    public double calcularCosto(int cantidad) {
        double costo = this.precioUnitario * cantidad;
        if (this.requiereInstalacion) {
            costo += COSTO_INSTALACION;
        }
        return costo;
    }

    @Override
    public String getCategoria() {
        return "Senaletica";
    }

    @Override
    public void verDatos() {
        System.out.println("PRODUCTO SENALETICA"
                + " COD: " + this.codigo
                + " NOMBRE: " + this.nombreProducto
                + " INSTALACION: " + (this.requiereInstalacion ? "SI" : "NO")
                + " PRECIO: " + this.precioUnitario);
    }

    public boolean isRequiereInstalacion() { return requiereInstalacion; }
    public void setRequiereInstalacion(boolean requiereInstalacion) {
        this.requiereInstalacion = requiereInstalacion;
    }
}
