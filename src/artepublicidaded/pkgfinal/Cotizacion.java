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
public class Cotizacion {
    private int idCotizacion;
    private int idCliente;
    private int idEmpleado;
    private LocalDate fechaCotizacion;
    private String estado; // PENDIENTE, ACEPTADA, RECHAZADA
    private double totalEstimado;

    public Cotizacion() {}

    public Cotizacion(int idCliente, int idEmpleado, LocalDate fechaCotizacion, 
                      String estado, double totalEstimado) {
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fechaCotizacion = fechaCotizacion;
        this.estado = estado;
        this.totalEstimado = totalEstimado;
    }

    public int getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(int idCotizacion) { this.idCotizacion = idCotizacion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public LocalDate getFechaCotizacion() { return fechaCotizacion; }
    public void setFechaCotizacion(LocalDate fechaCotizacion) { this.fechaCotizacion = fechaCotizacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotalEstimado() { return totalEstimado; }
    public void setTotalEstimado(double totalEstimado) { this.totalEstimado = totalEstimado; }
}
