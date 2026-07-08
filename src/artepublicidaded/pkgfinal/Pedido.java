/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;


import java.util.ArrayList;

/**
 *
 * @author Genes
 */
public class Pedido {
    
    public static final String[] ESTADOS_VALIDOS = {
        "PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO"
    };

    private int id;
    private String fechaPedido;
    private String fechaEntrega;
    private Cliente cliente;
    private Empleado empleado;   // empleado que atendio el pedido
    private String estado;
    private double total;

    private ArrayList<DetallePedido> detalles = new ArrayList<>();

    private static int contador = 1;

    public Pedido() {
        this.id = contador;
        contador++;
        this.detalles = new ArrayList<>();
        this.estado = "PENDIENTE";
        this.total = 0;
    }

    // Agrega un detalle al pedido y recalcula el total
    public void agregarDetalle(DetallePedido detalle) {
        detalle.calcularSubtotal();
        this.detalles.add(detalle);
        recalcularTotal();
    }

    // Suma los subtotales de todos los detalles
    public void recalcularTotal() {
        double suma = 0;
        for (int i = 0; i < detalles.size(); i++) {
            suma += detalles.get(i).getSubtotal();
        }
        this.total = suma;
    }

    public void verDatos() {
        System.out.println("PEDIDO ID: " + this.id
                + " FECHA: " + this.fechaPedido
                + " ENTREGA: " + this.fechaEntrega
                + " ESTADO: " + this.estado);
        System.out.println("  CLIENTE: " + (this.cliente != null ? this.cliente.getNombre() : "Sin asignar"));
        System.out.println("  EMPLEADO: " + (this.empleado != null ? this.empleado.getNombreCompleto() : "Sin asignar"));
        System.out.println("  DETALLE:");
        for (int i = 0; i < detalles.size(); i++) {
            detalles.get(i).verDatos();
        }
        System.out.println("  TOTAL: S/ " + this.total);
    }

    // Valida que el estado sea uno de los permitidos por el CHECK del SQL
    public static boolean esEstadoValido(String estado) {
        for (String e : ESTADOS_VALIDOS) {
            if (e.equalsIgnoreCase(estado)) {
                return true;
            }
        }
        return false;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getClienteId() { return cliente != null ? cliente.getId() : 0; }
    public void setClienteId(int clienteId) {
    if (this.cliente == null) this.cliente = new Cliente();
    this.cliente.setId(clienteId);
}

    public int getEmpleadoId() { return empleado != null ? empleado.getId() : 0; }
    public void setEmpleadoId(int empleadoId) {
    if (this.empleado == null) this.empleado = new Empleado();
    this.empleado.setId(empleadoId);
}

    public void setFechaPedido(java.time.LocalDate fecha) {
    this.fechaPedido = fecha != null ? fecha.toString() : null;
}

    public void setFechaEntrega(java.time.LocalDate fecha) {
    this.fechaEntrega = fecha != null ? fecha.toString() : null;
}

    public void setTotal(double total) { this.total = total; }

    public String getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(String fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }

    public ArrayList<DetallePedido> getDetalles() { return detalles; }
}
