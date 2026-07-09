/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
public class DetalleOrdenCompra {
    private int idDetalleOrden;
    private int idOrdenCompra;
    private int idMaterial;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    
    public DetalleOrdenCompra() {}

    public DetalleOrdenCompra(int idOrdenCompra, int idMaterial, int cantidad,
                               double precioUnitario, double subtotal) {
        this.idOrdenCompra = idOrdenCompra;
        this.idMaterial = idMaterial;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getIdDetalleOrden() { return idDetalleOrden; }
    public void setIdDetalleOrden(int idDetalleOrden) { this.idDetalleOrden = idDetalleOrden; }

    public int getIdOrdenCompra() { return idOrdenCompra; }
    public void setIdOrdenCompra(int idOrdenCompra) { this.idOrdenCompra = idOrdenCompra; }

    public int getIdMaterial() { return idMaterial; }
    public void setIdMaterial(int idMaterial) { this.idMaterial = idMaterial; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
