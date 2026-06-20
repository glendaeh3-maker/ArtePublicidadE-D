package com.mycompany.artepublicidaded;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// Panel CRUD de Clientes (tabla CLIENTE)
public class PanelClientes extends JPanel {

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField campoDni = EstiloUI.crearCampoTexto();
    private final JTextField campoNombre = EstiloUI.crearCampoTexto();
    private final JTextField campoTelefono = EstiloUI.crearCampoTexto();
    private final JTextField campoCorreo = EstiloUI.crearCampoTexto();
    private final JTextField campoDireccion = EstiloUI.crearCampoTexto();
    private final JTextField campoApellidoPaterno = EstiloUI.crearCampoTexto();
private final JTextField campoApellidoMaterno = EstiloUI.crearCampoTexto();

    private Integer idSeleccionado = null;

    public PanelClientes() {
        setLayout(new BorderLayout(0, 14));
        setBackground(EstiloUI.AZUL_CLARO);
        setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

        add(EstiloUI.crearTitulo("Gestion de Clientes"), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "DNI", "Nombre", "Telefono", "Correo", "Direccion"}, 0) {
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

        c.gridx = 0; c.gridy = 0; formulario.add(EstiloUI.crearEtiqueta("DNI"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoDni, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Nombre"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoNombre, c);
        c.gridx = 0; c.gridy = 1; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Apellido Paterno"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoApellidoPaterno, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Apellido Materno"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoApellidoMaterno, c);
        

        c.gridx = 0; c.gridy = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Telefono"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(campoTelefono, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Correo"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoCorreo, c);

        c.gridx = 0; c.gridy = 3; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Direccion"), c);
        c.gridx = 1; c.gridwidth = 3; c.weightx = 1; formulario.add(campoDireccion, c);
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

        c.gridx = 0; c.gridy = 4; c.gridwidth = 4; c.insets = new Insets(14, 5, 0, 5);
        formulario.add(botones, c);

        return formulario;
    }

    private void agregar() {
        if (!validarCampos()) {
            return;
        }
        Cliente nuevo = new Cliente();
        nuevo.setDni(campoDni.getText().trim());
        nuevo.setNombre(campoNombre.getText().trim());
        nuevo.setTelefono(campoTelefono.getText().trim());
        nuevo.setCorreo(campoCorreo.getText().trim());
        nuevo.setDireccion(campoDireccion.getText().trim());

        Datos.clientes.agregar_cliente(nuevo);
        refrescarTabla();
        limpiarFormulario();
    }

    private void actualizar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }
        Cliente cliente = Datos.clientes.buscar_por_id(idSeleccionado);
        if (cliente == null) {
            return;
        }
        cliente.setDni(campoDni.getText().trim());
        cliente.setNombre(campoNombre.getText().trim());
        cliente.setTelefono(campoTelefono.getText().trim());
        cliente.setCorreo(campoCorreo.getText().trim());
        cliente.setDireccion(campoDireccion.getText().trim());
        refrescarTabla();
        limpiarFormulario();
    }

    private void eliminar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirma = JOptionPane.showConfirmDialog(this, "Eliminar al cliente seleccionado?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Datos.clientes.eliminar_cliente(idSeleccionado);
            refrescarTabla();
            limpiarFormulario();
        }
    }

    private boolean validarCampos() {
        if (campoDni.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty()
                || campoTelefono.getText().trim().isEmpty() || campoCorreo.getText().trim().isEmpty()
                || campoDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI, nombre, telefono, correo y direccion son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (campoDni.getText().trim().length() != 8) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener 8 digitos.",
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
        idSeleccionado = (Integer) modeloTabla.getValueAt(fila, 0);
        Cliente c = Datos.clientes.buscar_por_id(idSeleccionado);
        if (c == null) {
            return;
        }
        campoDni.setText(c.getDni());
        campoNombre.setText(c.getNombre());
        campoTelefono.setText(c.getTelefono());
        campoCorreo.setText(c.getCorreo());
        campoDireccion.setText(c.getDireccion());
    }

    private void limpiarFormulario() {
        idSeleccionado = null;
        campoDni.setText("");
        campoNombre.setText("");
        campoTelefono.setText("");
        campoCorreo.setText("");
        campoDireccion.setText("");
        tabla.clearSelection();
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Cliente c : Datos.clientes.getLista()) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getDni(), c.getNombre(), c.getTelefono(), c.getCorreo(), c.getDireccion()
            });
        }
    }
}
