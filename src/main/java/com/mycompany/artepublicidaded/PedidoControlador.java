/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.artepublicidaded;

import java.util.ArrayList;

/**
 *
 * @author Genes
 */
public class PedidoControlador {
    
    ArrayList<Pedido> lista = new ArrayList();

    public void agregar_pedido(Pedido nuevo) {
        lista.add(nuevo);
    }

    public void listar_pedidos() {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).verDatos();
        }
    }

    // Busca pedido por id
    public Pedido buscar_por_id(int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return lista.get(i);
            }
        }
        System.out.println("No se encontro pedido con ID: " + id);
        return null;
    }

    // Filtra pedidos por estado (PENDIENTE, EN PROCESO, COMPLETADO, CANCELADO)
    public ArrayList<Pedido> filtrar_por_estado(String estado) {
        ArrayList<Pedido> resultado = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getEstado().equalsIgnoreCase(estado)) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }

    // Reporte de ventas: total general de todos los pedidos registrados
    public void mostrar_reporte_ventas() {
        System.out.println("====== REPORTE DE VENTAS - ARTE PUBLICIDAD E&D ======");
        double total_general = 0;
        for (int i = 0; i < lista.size(); i++) {
            Pedido p = lista.get(i);
            total_general += p.getTotal();
            String cliente_nombre = (p.getCliente() != null) ? p.getCliente().getNombre() : "Sin asignar";
            System.out.println("Pedido #" + p.getId()
                    + " | CLIENTE: " + cliente_nombre
                    + " | ESTADO: " + p.getEstado()
                    + " | TOTAL: S/ " + p.getTotal());
        }
        System.out.println("TOTAL GENERAL: S/ " + total_general);
        System.out.println("======================================================");
    }

    public int total() {
        return lista.size();
    }

    public ArrayList<Pedido> getLista() {
        return lista;
    }

    public boolean eliminar_pedido(int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }
}
