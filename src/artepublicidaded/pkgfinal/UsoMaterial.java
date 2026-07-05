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
public class UsoMaterial {
    private int idUsoMaterial;
    private int idDetallePedido;
    private int idMaterial;
    private int idEmpleado;
    private double cantidadUsada;
    private LocalDate fechaUso;

    public UsoMaterial() {}

    public UsoMaterial(int idDetallePedido, int idMaterial, int idEmpleado,
                       double cantidadUsada, LocalDate fechaUso) {
        this.idDetallePedido = idDetallePedido;
        this.idMaterial = idMaterial;
        this.idEmpleado = idEmpleado;
        this.cantidadUsada = cantidadUsada;
        this.fechaUso = fechaUso;
    }

    public int getIdUsoMaterial() { return idUsoMaterial; }
    public void setIdUsoMaterial(int idUsoMaterial) { this.idUsoMaterial = idUsoMaterial; }

    public int getIdDetallePedido() { return idDetallePedido; }
    public void setIdDetallePedido(int idDetallePedido) { this.idDetallePedido = idDetallePedido; }

    public int getIdMaterial() { return idMaterial; }
    public void setIdMaterial(int idMaterial) { this.idMaterial = idMaterial; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public double getCantidadUsada() { return cantidadUsada; }
    public void setCantidadUsada(double cantidadUsada) { this.cantidadUsada = cantidadUsada; }

    public LocalDate getFechaUso() { return fechaUso; }
    public void setFechaUso(LocalDate fechaUso) { this.fechaUso = fechaUso; }
}
