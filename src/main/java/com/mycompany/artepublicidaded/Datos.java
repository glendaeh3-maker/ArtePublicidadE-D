/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.artepublicidaded;

/**
 *
 * @author Genes
 */
public class Datos {
    
    public static final ClienteControlador clientes = new ClienteControlador();
    public static final EmpleadoControlador empleados = new EmpleadoControlador();
    public static final ProductoControlador productos = new ProductoControlador();
    public static final PedidoControlador pedidos = new PedidoControlador();

    static {
        ProductoImpresion p1 = new ProductoImpresion();
        p1.setCodigo("PRD001");
        p1.setNombreProducto("Banner 2x1m");
        p1.setDescripcion("Banner de lona retroiluminada 2x1 metros");
        p1.setPrecioUnitario(120.00);
        p1.setTipoImpresion("BANNER");

        ProductoImpresion p2 = new ProductoImpresion();
        p2.setCodigo("PRD002");
        p2.setNombreProducto("Volante A5");
        p2.setDescripcion("Impresion full color en papel couche 150gr");
        p2.setPrecioUnitario(0.30);
        p2.setTipoImpresion("VOLANTE");

        ProductoSeñaletica p3 = new ProductoSeñaletica();
        p3.setCodigo("PRD003");
        p3.setNombreProducto("Letrero Luminoso");
        p3.setDescripcion("Letrero con iluminacion LED y marco aluminio");
        p3.setPrecioUnitario(350.00);
        p3.setRequiereInstalacion(true);

        ProductoSeñaletica p4 = new ProductoSeñaletica();
        p4.setCodigo("PRD004");
        p4.setNombreProducto("Senal de Seguridad");
        p4.setDescripcion("Senal adhesiva 30x30cm segun norma INDECOPI");
        p4.setPrecioUnitario(8.50);
        p4.setRequiereInstalacion(false);

        ProductoPublicidadBTL p5 = new ProductoPublicidadBTL();
        p5.setCodigo("PRD005");
        p5.setNombreProducto("Roll Up 80x200cm");
        p5.setDescripcion("Display retractil para eventos y ferias");
        p5.setPrecioUnitario(180.00);
        p5.setDiasEvento(1);

        ProductoDiseñoGrafico p6 = new ProductoDiseñoGrafico();
        p6.setCodigo("PRD006");
        p6.setNombreProducto("Diseno de Logo");
        p6.setDescripcion("Diseno vectorial de logotipo con manual");
        p6.setPrecioUnitario(500.00);
        p6.setNumeroRevisiones(2);

        productos.agregar_producto(p1);
        productos.agregar_producto(p2);
        productos.agregar_producto(p3);
        productos.agregar_producto(p4);
        productos.agregar_producto(p5);
        productos.agregar_producto(p6);
    }

    public static Producto buscarProductoPorCodigo(String codigo) {
        return productos.buscar_por_codigo(codigo);
    }
}
