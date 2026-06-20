/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.artepublicidaded;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Genes
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());

    private CardLayout cardLayout;
    private JPanel panelContenido;

    public static final String VISTA_INICIO = "INICIO";
    public static final String VISTA_CLIENTES = "CLIENTES";
    public static final String VISTA_EMPLEADOS = "EMPLEADOS";
    public static final String VISTA_PRODUCTOS = "PRODUCTOS";
    public static final String VISTA_PEDIDOS = "PEDIDOS";

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        construirContenido();
    }

    // Construye el menu lateral + el panel central con CardLayout que
    // contiene los 4 modulos del sistema (Clientes, Empleados, Productos, Pedidos)
    private void construirContenido() {
        setTitle("Arte Publicidad E&D - Sistema de Gestion");
        setSize(1050, 650);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(EstiloUI.AZUL_CLARO);

        panelContenido.add(crearPanelInicio(), VISTA_INICIO);
        panelContenido.add(new PanelClientes(), VISTA_CLIENTES);
        panelContenido.add(new PanelEmpleados(), VISTA_EMPLEADOS);
        panelContenido.add(new PanelProductos(), VISTA_PRODUCTOS);
        panelContenido.add(new PanelPedidos(), VISTA_PEDIDOS);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(crearMenuLateral(), BorderLayout.WEST);
        getContentPane().add(panelContenido, BorderLayout.CENTER);
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(EstiloUI.AZUL_OSCURO);
        menu.setPreferredSize(new Dimension(220, 0));

        JLabel marca = new JLabel("ARTE PUBLICIDAD E&D");
        marca.setFont(EstiloUI.FUENTE_TITULO);
        marca.setForeground(EstiloUI.BLANCO);
        marca.setHorizontalAlignment(SwingConstants.CENTER);
        marca.setBorder(BorderFactory.createEmptyBorder(28, 10, 28, 10));

        JPanel opciones = new JPanel();
        opciones.setLayout(new GridLayout(5, 1, 0, 6));
        opciones.setBackground(EstiloUI.AZUL_OSCURO);
        opciones.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        opciones.add(crearBotonMenu("Inicio", VISTA_INICIO));
        opciones.add(crearBotonMenu("Clientes", VISTA_CLIENTES));
        opciones.add(crearBotonMenu("Empleados", VISTA_EMPLEADOS));
        opciones.add(crearBotonMenu("Productos", VISTA_PRODUCTOS));
        opciones.add(crearBotonMenu("Pedidos", VISTA_PEDIDOS));

        menu.add(marca, BorderLayout.NORTH);
        menu.add(opciones, BorderLayout.NORTH);
        return menu;
    }

    private JButton crearBotonMenu(String texto, String vista) {
        JButton boton = new JButton(texto);
        boton.setFont(EstiloUI.FUENTE_MENU);
        boton.setForeground(EstiloUI.BLANCO);
        boton.setBackground(EstiloUI.AZUL_MEDIO);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        boton.addActionListener(e -> cardLayout.show(panelContenido, vista));
        return boton;
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(EstiloUI.AZUL_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titulo = EstiloUI.crearTitulo("Bienvenido a Arte Publicidad E&D");
        JLabel subtitulo = EstiloUI.crearEtiqueta(
                "Selecciona una opcion del menu lateral para gestionar clientes, "
                + "empleados, productos publicitarios y pedidos de la empresa.");
        subtitulo.setFont(EstiloUI.FUENTE_NORMAL);

        JPanel textos = new JPanel();
        textos.setLayout(new GridLayout(2, 1, 0, 12));
        textos.setBackground(EstiloUI.AZUL_CLARO);
        textos.add(titulo);
        textos.add(subtitulo);

        panel.add(textos, BorderLayout.NORTH);
        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
