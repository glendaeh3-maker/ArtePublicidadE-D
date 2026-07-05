/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;

/**
 *
 * @author Genes
 */
public class ServicioEspecial {
     private int idServicio;
    private int idEmpleado;
    private String nombreServicio;
    private String descripcion;
    private double costo;

    public ServicioEspecial() {}

    public ServicioEspecial(int idEmpleado, String nombreServicio, 
                            String descripcion, double costo) {
        this.idEmpleado = idEmpleado;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
}
