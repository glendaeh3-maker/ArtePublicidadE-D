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
public class ProductoControlador {
    
    ArrayList<Producto> lista = new ArrayList();

    public void agregar_producto(Producto nuevo) {
        lista.add(nuevo);
    }

    public void listar_productos() {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).verDatos();
        }
    }

    // Busca producto por codigo
    public Producto buscar_por_codigo(String codigo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(codigo)) {
                return lista.get(i);
            }
        }
        System.out.println("No se encontro producto con codigo: " + codigo);
        return null;
    }

    // Filtra productos por categoria (Impresion, Senaletica, Publicidad BTL, Diseno Grafico)
    public ArrayList<Producto> filtrar_por_categoria(String categoria) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }

    public boolean eliminar_producto(String codigo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(codigo)) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }

    public int total() {
        return lista.size();
    }

    public ArrayList<Producto> getLista() {
        return lista;
    }
}
