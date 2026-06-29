package artepublicidaded.pkgfinal;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Genes
 */
public class ClienteControlador {

    ArrayList<Cliente> lista = new ArrayList();

    public void agregar_cliente(Cliente nuevo) {
        lista.add(nuevo);
    }

    public void listar_clientes() {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).verDatos();
        }
    }

    // Busca cliente por id
    public Cliente buscar_por_id(int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return lista.get(i);
            }
        }
        System.out.println("No se encontro cliente con ID: " + id);
        return null;
    }

    // Busca cliente por nombre (coincidencia parcial)
    public Cliente buscar_por_nombre(String nombre) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                return lista.get(i);
            }
        }
        System.out.println("No se encontro cliente con nombre: " + nombre);
        return null;
    }

    // Busca cliente por DNI (es UNIQUE en la tabla CLIENTE)
    public Cliente buscar_por_dni(String dni) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDni().equals(dni)) {
                return lista.get(i);
            }
        }
        System.out.println("No se encontro cliente con DNI: " + dni);
        return null;
    }

    // Elimina cliente por id
    public boolean eliminar_cliente(int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }

    public int total() {
        return lista.size();
    }

    public ArrayList<Cliente> getLista() {
        return lista;
    }
}
