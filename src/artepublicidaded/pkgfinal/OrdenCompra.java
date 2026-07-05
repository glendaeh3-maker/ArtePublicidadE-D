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
public class OrdenCompra {
    private int idOrdenCompra;
    private int idProveedor;
    private int idEmpleado;
    private LocalDate fechaOrden;
    private String estado; // PENDIENTE, EN PROCESO, COMPLETADO, CANCELADO
    private double total;

    public OrdenCompra() {}

    public OrdenCompra(int idProveedor, int idEmpleado, LocalDate fechaOrden,
                       String estado, double total) {
        this.idProveedor = idProveedor;
        this.idEmpleado = idEmpleado;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.total = total;
    }

    public int getIdOrdenCompra() { return idOrdenCompra; }
    public void setIdOrdenCompra(int idOrdenCompra) { this.idOrdenCompra = idOrdenCompra; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public LocalDate getFechaOrden() { return fechaOrden; }
    public void setFechaOrden(LocalDate fechaOrden) { this.fechaOrden = fechaOrden; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}

