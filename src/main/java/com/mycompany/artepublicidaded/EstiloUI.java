package com.mycompany.artepublicidaded;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

// Paleta de colores y tipografia comun para toda la interfaz
public class EstiloUI {

    public static final Color AZUL_OSCURO   = new Color(28, 41, 64);
    public static final Color AZUL_MEDIO    = new Color(41, 78, 120);
    public static final Color AZUL_CLARO    = new Color(232, 240, 248);
    public static final Color BLANCO        = new Color(255, 255, 255);
    public static final Color GRIS_TEXTO    = new Color(60, 64, 70);
    public static final Color GRIS_BORDE    = new Color(206, 212, 220);
    public static final Color VERDE_OK      = new Color(46, 125, 80);
    public static final Color ROJO_ALERTA   = new Color(176, 48, 48);
    public static final Color AMARILLO_ALERTA = new Color(196, 140, 20);

    public static final Font FUENTE_TITULO   = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_MENU     = new Font("Segoe UI", Font.PLAIN, 14);

    public static JButton crearBotonPrimario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(AZUL_MEDIO);
        boton.setForeground(BLANCO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return boton;
    }

    public static JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(BLANCO);
        boton.setForeground(AZUL_MEDIO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_MEDIO, 1),
                BorderFactory.createEmptyBorder(7, 17, 7, 17)));
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return boton;
    }

    public static JButton crearBotonPeligro(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(ROJO_ALERTA);
        boton.setForeground(BLANCO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return boton;
    }

    public static JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_TITULO);
        label.setForeground(AZUL_OSCURO);
        return label;
    }

    public static JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_NORMAL);
        label.setForeground(GRIS_TEXTO);
        return label;
    }

    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_NORMAL);
        Border lineBorder = BorderFactory.createLineBorder(GRIS_BORDE, 1);
        Border padding = BorderFactory.createEmptyBorder(5, 8, 5, 8);
        campo.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));
        return campo;
    }
}
