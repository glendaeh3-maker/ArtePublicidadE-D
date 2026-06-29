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
public class EmpleadoControlador {

    ArrayList<Empleado> lista = new ArrayList();

    public void agregar_empleado(Empleado nuevo) {
        lista.add(nuevo);
    }

    public void listar_empleados() {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).verDatos();
        }
    }

    // Busca empleado por id
    public Empleado buscar_por_id(int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return lista.get(i);
            }
        }
        System.out.println("No se encontro empleado con ID: " + id);
        return null;
    }

    // Busca empleados por cargo (ej: "Vendedor", "Disenador")
    public ArrayList<Empleado> buscar_por_cargo(String cargo) {
        ArrayList<Empleado> resultado = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCargo().equalsIgnoreCase(cargo)) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }

    public int total() {
        return lista.size();
    }

    public ArrayList<Empleado> getLista() {
        return lista;
    }

    public boolean eliminar_empleado(int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }
}
