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
public class PagoProovedor {
     private int idPagoProveedor;
    private int idProveedor;
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA

    public PagoProovedor() {}

    public PagoProovedor(int idProveedor, LocalDate fechaPago, 
                         double monto, String metodoPago) {
        this.idProveedor = idProveedor;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
    }

    public int getIdPagoProveedor() { return idPagoProveedor; }
    public void setIdPagoProveedor(int idPagoProveedor) { this.idPagoProveedor = idPagoProveedor; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
