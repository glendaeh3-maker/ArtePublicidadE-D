package com.mycompany.artepublicidaded;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// Panel CRUD de Empleados (tabla EMPLEADO)
public class PanelEmpleados extends JPanel {

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField campoNombres = EstiloUI.crearCampoTexto();
    private final JTextField campoApellidos = EstiloUI.crearCampoTexto();
    private final JTextField campoTelefono = EstiloUI.crearCampoTexto();
    private final JTextField campoCorreo = EstiloUI.crearCampoTexto();
    private final JComboBox<String> comboCargo = new JComboBox<>(
            new String[]{"Disenador", "Vendedor", "Coordinador", "Impresor"});

    private Integer idSeleccionado = null;

    public PanelEmpleados() {
        setLayout(new BorderLayout(0, 14));
        setBackground(EstiloUI.AZUL_CLARO);
        setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

        add(EstiloUI.crearTitulo("Gestion de Empleados"), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombres", "Apellidos", "Cargo", "Telefono", "Correo"}, 0) {
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

        c.gridx = 0; c.gridy = 0; formulario.add(EstiloUI.crearEtiqueta("Nombres"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoNombres, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Apellidos"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoApellidos, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Telefono"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoTelefono, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Correo"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoCorreo, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Cargo"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(comboCargo, c);

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

    private void agregar() {
        if (!validarCampos()) {
            return;
        }
        Empleado nuevo = new Empleado();
        aplicarCampos(nuevo);
        Datos.empleados.agregar_empleado(nuevo);
        refrescarTabla();
        limpiarFormulario();
    }

    private void actualizar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }
        Empleado empleado = Datos.empleados.buscar_por_id(idSeleccionado);
        if (empleado == null) {
            return;
        }
        aplicarCampos(empleado);
        refrescarTabla();
        limpiarFormulario();
    }

    private void eliminar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirma = JOptionPane.showConfirmDialog(this, "Eliminar al empleado seleccionado?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Datos.empleados.eliminar_empleado(idSeleccionado);
            refrescarTabla();
            limpiarFormulario();
        }
    }

    private void aplicarCampos(Empleado empleado) {
        empleado.setNombres(campoNombres.getText().trim());
        empleado.setApellidos(campoApellidos.getText().trim());
        empleado.setTelefono(campoTelefono.getText().trim());
        empleado.setCorreo(campoCorreo.getText().trim());
        empleado.setCargo((String) comboCargo.getSelectedItem());
    }

    private boolean validarCampos() {
        if (campoNombres.getText().trim().isEmpty() || campoApellidos.getText().trim().isEmpty()
                || campoTelefono.getText().trim().isEmpty() || campoCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombres, apellidos, telefono y correo son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        idSeleccionado = (Integer) modeloTabla.getValueAt(fila, 0);
        Empleado emp = Datos.empleados.buscar_por_id(idSeleccionado);
        if (emp == null) {
            return;
        }
        campoNombres.setText(emp.getNombres());
        campoApellidos.setText(emp.getApellidos());
        campoTelefono.setText(emp.getTelefono());
        campoCorreo.setText(emp.getCorreo());
        comboCargo.setSelectedItem(emp.getCargo());
    }

    private void limpiarFormulario() {
        idSeleccionado = null;
        campoNombres.setText("");
        campoApellidos.setText("");
        campoTelefono.setText("");
        campoCorreo.setText("");
        comboCargo.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Empleado e : Datos.empleados.getLista()) {
            modeloTabla.addRow(new Object[]{
                e.getId(), e.getNombres(), e.getApellidos(), e.getCargo(), e.getTelefono(), e.getCorreo()
            });
        }
    }
}
