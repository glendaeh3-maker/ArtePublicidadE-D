/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
public class ProductoPublicidadBTL extends Producto {
     private int diasEvento;

    public ProductoPublicidadBTL() {
        this.diasEvento = 1;
    }

    @Override
    public double calcularCosto(int cantidad) {
        return this.precioUnitario * cantidad * this.diasEvento;
    }

    @Override
    public String getCategoria() {
        return "Publicidad BTL";
    }

    @Override
    public void verDatos() {
        System.out.println("PRODUCTO PUBLICIDAD BTL"
                + " COD: " + this.codigo
                + " NOMBRE: " + this.nombreProducto
                + " DIAS EVENTO: " + this.diasEvento
                + " PRECIO: " + this.precioUnitario);
    }

    public int getDiasEvento() { return diasEvento; }
    public void setDiasEvento(int diasEvento) { this.diasEvento = diasEvento; }
}
