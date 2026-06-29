/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
public class ProductoImpresion extends Producto {
    
    private String tipoImpresion; // ej: "BANNER", "VOLANTE", "AFICHE"

    public ProductoImpresion() {
    }

    @Override
    public double calcularCosto(int cantidad) {
        return this.precioUnitario * cantidad;
    }

    @Override
    public String getCategoria() {
        return "Impresion";
    }

    @Override
    public void verDatos() {
        System.out.println("PRODUCTO IMPRESION"
                + " COD: " + this.codigo
                + " NOMBRE: " + this.nombreProducto
                + " TIPO: " + this.tipoImpresion
                + " PRECIO: " + this.precioUnitario);
    }

    public String getTipoImpresion() { return tipoImpresion; }
    public void setTipoImpresion(String tipoImpresion) { this.tipoImpresion = tipoImpresion; }
}
