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
public class Pago {
    private int idPago;
    private int idCliente;
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA
    private String estado; // PENDIENTE, COMPLETADO, CANCELADO

    public Pago() {}

    public Pago(int idCliente, LocalDate fechaPago, double monto,
                String metodoPago, String estado) {
        this.idCliente = idCliente;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}