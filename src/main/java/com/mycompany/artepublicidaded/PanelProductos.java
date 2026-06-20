package com.mycompany.artepublicidaded;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// Panel CRUD del catalogo de Productos (tablas CATEGORIA_PRODUCTO + PRODUCTO).
// Cada categoria se modela como una subclase distinta de Producto:
// ProductoImpresion, ProductoSeñaletica, ProductoPublicidadBTL, ProductoDiseñoGrafico
public class PanelProductos extends JPanel {

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField campoCodigo = EstiloUI.crearCampoTexto();
    private final JTextField campoNombre = EstiloUI.crearCampoTexto();
    private final JTextField campoDescripcion = EstiloUI.crearCampoTexto();
    private final JTextField campoPrecio = EstiloUI.crearCampoTexto();
    private final JComboBox<String> comboCategoria = new JComboBox<>(
            new String[]{"Impresion", "Senaletica", "Publicidad BTL", "Diseno Grafico"});
    private final JCheckBox checkInstalacion = new JCheckBox("Requiere instalacion (Senaletica)");

    private String codigoSeleccionado = null;

    public PanelProductos() {
        setLayout(new BorderLayout(0, 14));
        setBackground(EstiloUI.AZUL_CLARO);
        setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

        add(EstiloUI.crearTitulo("Catalogo de Productos"), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Codigo", "Nombre", "Categoria", "Descripcion", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(EstiloUI.FUENTE_NORMAL);
        tabla.setRowHeight(26);
        tabla.getTableHeader().setFont(EstiloUI.FUENTE_SUBTITULO);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setBackground(EstiloUI.AZUL_CLARO);
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);
        centro.add(crearFormulario(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        refrescarTabla();
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(EstiloUI.BLANCO);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloUI.GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; formulario.add(EstiloUI.crearEtiqueta("Codigo"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoCodigo, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Nombre"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoNombre, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Categoria"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(comboCategoria, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Precio Unitario"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoPrecio, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Descripcion"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoDescripcion, c);
        c.gridx = 2; c.weightx = 0; c.gridwidth = 2; formulario.add(checkInstalacion, c);
        c.gridwidth = 1;

        JPanel botones = new JPanel(new GridLayout(1, 4, 10, 0));
        botones.setBackground(EstiloUI.BLANCO);
        botones.add(EstiloUI.crearBotonPrimario("Agregar"));
        botones.add(EstiloUI.crearBotonSecundario("Actualizar"));
        botones.add(EstiloUI.crearBotonPeligro("Eliminar"));
        botones.add(EstiloUI.crearBotonSecundario("Limpiar"));

        ((javax.swing.JButton) botones.getComponent(0)).addActionListener(e -> agregar());
        ((javax.swing.JButton) botones.getComponent(1)).addActionListener(e -> actualizar());
        ((javax.swing.JButton) botones.getComponent(2)).addActionListener(e -> eliminar());
        ((javax.swing.JButton) botones.getComponent(3)).addActionListener(e -> limpiarFormulario());

        c.gridx = 0; c.gridy = 3; c.gridwidth = 4; c.insets = new Insets(14, 5, 0, 5);
        formulario.add(botones, c);

        return formulario;
    }

    // Construye la subclase correcta segun la categoria elegida (polimorfismo)
    private Producto construirProductoSegunCategoria(double precio) {
        String categoria = (String) comboCategoria.getSelectedItem();
        switch (categoria) {
            case "Impresion": {
                ProductoImpresion p = new ProductoImpresion();
                p.setTipoImpresion("GENERICO");
                return p;
            }
            case "Senaletica": {
                ProductoSeñaletica p = new ProductoSeñaletica();
                p.setRequiereInstalacion(checkInstalacion.isSelected());
                return p;
            }
            case "Publicidad BTL": {
                ProductoPublicidadBTL p = new ProductoPublicidadBTL();
                p.setDiasEvento(1);
                return p;
            }
            default: {
                ProductoDiseñoGrafico p = new ProductoDiseñoGrafico();
                p.setNumeroRevisiones(2);
                return p;
            }
        }
    }

    private void agregar() {
        if (!validarCampos()) {
            return;
        }
        double precio = Double.parseDouble(campoPrecio.getText().trim());
        Producto nuevo = construirProductoSegunCategoria(precio);
        nuevo.setCodigo(campoCodigo.getText().trim());
        nuevo.setNombreProducto(campoNombre.getText().trim());
        nuevo.setDescripcion(campoDescripcion.getText().trim());
        nuevo.setPrecioUnitario(precio);

        Datos.productos.agregar_producto(nuevo);
        refrescarTabla();
        limpiarFormulario();
    }

    private void actualizar() {
        if (codigoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }
        // Para mantener simple la edicion, se elimina el producto anterior y se
        // crea uno nuevo con los datos del formulario (puede incluso cambiar de categoria).
        Datos.productos.eliminar_producto(codigoSeleccionado);
        agregar();
    }

    private void eliminar() {
        if (codigoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirma = JOptionPane.showConfirmDialog(this, "Eliminar el producto seleccionado?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Datos.productos.eliminar_producto(codigoSeleccionado);
            refrescarTabla();
            limpiarFormulario();
        }
    }

    private boolean validarCampos() {
        if (campoCodigo.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty()
                || campoDescripcion.getText().trim().isEmpty() || campoPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Codigo, nombre, descripcion y precio son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            if (precio < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio unitario debe ser un numero valido.",
                    "Dato invalido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        codigoSeleccionado = (String) modeloTabla.getValueAt(fila, 0);
        Producto p = Datos.productos.buscar_por_codigo(codigoSeleccionado);
        if (p == null) {
            return;
        }
        campoCodigo.setText(p.getCodigo());
        campoNombre.setText(p.getNombreProducto());
        campoDescripcion.setText(p.getDescripcion());
        campoPrecio.setText(String.valueOf(p.getPrecioUnitario()));
        comboCategoria.setSelectedItem(p.getCategoria());
        checkInstalacion.setSelected(p instanceof ProductoSeñaletica && ((ProductoSeñaletica) p).isRequiereInstalacion());
    }

    private void limpiarFormulario() {
        codigoSeleccionado = null;
        campoCodigo.setText("");
        campoNombre.setText("");
        campoDescripcion.setText("");
        campoPrecio.setText("");
        comboCategoria.setSelectedIndex(0);
        checkInstalacion.setSelected(false);
        tabla.clearSelection();
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : Datos.productos.getLista()) {
            modeloTabla.addRow(new Object[]{
                p.getCodigo(), p.getNombreProducto(), p.getCategoria(),
                p.getDescripcion(), String.format("S/ %.2f", p.getPrecioUnitario())
            });
        }
    }
}
