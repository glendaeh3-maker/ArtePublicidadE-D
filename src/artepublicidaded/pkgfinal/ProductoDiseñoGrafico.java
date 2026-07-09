/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
public class ProductoDiseñoGrafico extends Producto{
    private int numeroRevisiones;

    public ProductoDiseñoGrafico() {
        this.numeroRevisiones = 2; // revisiones incluidas por defecto
    }

    @Override
    public double calcularCosto(int cantidad) {
        // El diseño se cobra por proyecto, no escala linealmente con "cantidad"
        return this.precioUnitario * cantidad;
    }

    @Override
    public String getCategoria() {
        return "DISEÑO_GRAFICO";
    }

    @Override
    public void verDatos() {
        System.out.println("PRODUCTO DISENO GRAFICO"
                + " COD: " + this.codigo
                + " NOMBRE: " + this.nombreProducto
                + " REVISIONES INCLUIDAS: " + this.numeroRevisiones
                + " PRECIO: " + this.precioUnitario);
    }

    public int getNumeroRevisiones() { return numeroRevisiones; }
    public void setNumeroRevisiones(int numeroRevisiones) { this.numeroRevisiones = numeroRevisiones; }
}
