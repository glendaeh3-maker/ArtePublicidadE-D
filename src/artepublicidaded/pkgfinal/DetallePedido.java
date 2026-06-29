/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
public class DetallePedido {
    
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetallePedido() {
    }

    public void calcularSubtotal() {
        this.subtotal = this.producto.calcularCosto(this.cantidad);
    }

    public void verDatos() {
        System.out.println("    PRODUCTO: " + this.producto.getNombreProducto()
                + " CANTIDAD: " + this.cantidad
                + " SUBTOTAL: S/ " + this.subtotal);
    }

    // Getters y Setters
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
