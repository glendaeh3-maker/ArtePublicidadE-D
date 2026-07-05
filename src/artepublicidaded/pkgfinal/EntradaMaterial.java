/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import java.time.LocalDate;

/**
 *
 * @author Genes
 */
public class EntradaMaterial {
    private int idEntrada;
    private int idOrdenCompra;
    private int idMaterial;
    private int idEmpleado;
    private LocalDate fechaEntrada;
    private double cantidadEntrada;

    public EntradaMaterial() {}

    public EntradaMaterial(int idOrdenCompra, int idMaterial, int idEmpleado,
                           LocalDate fechaEntrada, double cantidadEntrada) {
        this.idOrdenCompra = idOrdenCompra;
        this.idMaterial = idMaterial;
        this.idEmpleado = idEmpleado;
        this.fechaEntrada = fechaEntrada;
        this.cantidadEntrada = cantidadEntrada;
    }

    public int getIdEntrada() { return idEntrada; }
    public void setIdEntrada(int idEntrada) { this.idEntrada = idEntrada; }

    public int getIdOrdenCompra() { return idOrdenCompra; }
    public void setIdOrdenCompra(int idOrdenCompra) { this.idOrdenCompra = idOrdenCompra; }

    public int getIdMaterial() { return idMaterial; }
    public void setIdMaterial(int idMaterial) { this.idMaterial = idMaterial; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public double getCantidadEntrada() { return cantidadEntrada; }
    public void setCantidadEntrada(double cantidadEntrada) { this.cantidadEntrada = cantidadEntrada; }
}
