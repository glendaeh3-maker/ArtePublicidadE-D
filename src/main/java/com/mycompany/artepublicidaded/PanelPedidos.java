package com.mycompany.artepublicidaded;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// Panel CRUD de Pedidos: cabecera (cliente + empleado + estado) + lineas de
// detalle (productos). Corresponde a las tablas PEDIDO y DETALLE_PEDIDO.
public class PanelPedidos extends JPanel {

    private final DefaultTableModel modeloPedidos;
    private final JTable tablaPedidos;

    private final DefaultTableModel modeloDetalle;
    private final JTable tablaDetalle;
    private final java.util.List<DetallePedido> detallesActuales = new java.util.ArrayList<>();

    private final JComboBox<Cliente> comboCliente = new JComboBox<>();
    private final JComboBox<Empleado> comboEmpleado = new JComboBox<>();
    private final JComboBox<Producto> comboProducto = new JComboBox<>();
    private final JComboBox<String> comboEstado = new JComboBox<>(Pedido.ESTADOS_VALIDOS);
    private final JTextField campoCantidad = EstiloUI.crearCampoTexto();
    private final JTextField campoFechaEntrega = EstiloUI.crearCampoTexto();

    public PanelPedidos() {
        setLayout(new BorderLayout(0, 14));
        setBackground(EstiloUI.AZUL_CLARO);
        setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

        add(EstiloUI.crearTitulo("Gestion de Pedidos"), BorderLayout.NORTH);

        modeloPedidos = new DefaultTableModel(
                new Object[]{"ID", "Fecha", "Cliente", "Empleado", "Estado", "Entrega", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tablaPedidos = new JTable(modeloPedidos);
        tablaPedidos.setFont(EstiloUI.FUENTE_NORMAL);
        tablaPedidos.setRowHeight(26);
        tablaPedidos.getTableHeader().setFont(EstiloUI.FUENTE_SUBTITULO);

        modeloDetalle = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setFont(EstiloUI.FUENTE_NORMAL);
        tablaDetalle.setRowHeight(24);

        JSplitPane splitSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                envolverConTitulo(new JScrollPane(tablaPedidos), "Pedidos registrados"),
                envolverConTitulo(new JScrollPane(tablaDetalle), "Detalle del nuevo pedido"));
        splitSuperior.setResizeWeight(0.55);
        splitSuperior.setBackground(EstiloUI.AZUL_CLARO);

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setBackground(EstiloUI.AZUL_CLARO);
        centro.add(splitSuperior, BorderLayout.CENTER);
        centro.add(crearFormulario(), BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        recargarCombos();
        refrescarTablaPedidos();

        // Cuando CardLayout vuelve a mostrar este panel se vuelve a llamar
        // setVisible(true) internamente, lo que dispara componentShown.
        // Asi los combos de cliente/empleado siempre reflejan lo mas reciente.
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                recargarCombos();
            }
        });
    }

    private JPanel envolverConTitulo(java.awt.Component contenido, String titulo) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(EstiloUI.AZUL_CLARO);
        panel.add(EstiloUI.crearEtiqueta(titulo), BorderLayout.NORTH);
        panel.add(contenido, BorderLayout.CENTER);
        return panel;
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

        c.gridx = 0; c.gridy = 0; formulario.add(EstiloUI.crearEtiqueta("Cliente"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(comboCliente, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Empleado"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(comboEmpleado, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Estado"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(comboEstado, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Entrega (dd/mm/aaaa)"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoFechaEntrega, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Producto"), c);
        c.gridx = 1; c.weightx = 1; formulario.add(comboProducto, c);
        c.gridx = 2; c.weightx = 0; formulario.add(EstiloUI.crearEtiqueta("Cantidad"), c);
        c.gridx = 3; c.weightx = 1; formulario.add(campoCantidad, c);

        JPanel botonesDetalle = new JPanel(new GridLayout(1, 2, 10, 0));
        botonesDetalle.setBackground(EstiloUI.BLANCO);
        botonesDetalle.add(EstiloUI.crearBotonSecundario("Agregar linea"));
        botonesDetalle.add(EstiloUI.crearBotonPeligro("Quitar lineas"));
        ((javax.swing.JButton) botonesDetalle.getComponent(0)).addActionListener(e -> agregarLineaDetalle());
        ((javax.swing.JButton) botonesDetalle.getComponent(1)).addActionListener(e -> limpiarDetalle());

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2; formulario.add(botonesDetalle, c);
        c.gridwidth = 1;

        JPanel botonesPedido = new JPanel(new GridLayout(1, 2, 10, 0));
        botonesPedido.setBackground(EstiloUI.BLANCO);
        botonesPedido.add(EstiloUI.crearBotonPrimario("Guardar pedido"));
        botonesPedido.add(EstiloUI.crearBotonPeligro("Eliminar pedido seleccionado"));
        ((javax.swing.JButton) botonesPedido.getComponent(0)).addActionListener(e -> guardarPedido());
        ((javax.swing.JButton) botonesPedido.getComponent(1)).addActionListener(e -> eliminarPedido());

        c.gridx = 0; c.gridy = 4; c.gridwidth = 4; c.insets = new Insets(14, 5, 0, 5);
        formulario.add(botonesPedido, c);

        return formulario;
    }

    private void agregarLineaDetalle() {
        Producto producto = (Producto) comboProducto.getSelectedItem();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "No hay productos disponibles.", "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(campoCantidad.getText().trim());
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un numero entero mayor a 0.",
                    "Dato invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.calcularSubtotal();
        detallesActuales.add(detalle);

        modeloDetalle.addRow(new Object[]{producto.getNombreProducto(), cantidad, String.format("S/ %.2f", detalle.getSubtotal())});
        campoCantidad.setText("");
    }

    private void limpiarDetalle() {
        detallesActuales.clear();
        modeloDetalle.setRowCount(0);
    }

    private void guardarPedido() {
        if (detallesActuales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agrega al menos una linea de producto antes de guardar.",
                    "Pedido vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente clienteElegido = (Cliente) comboCliente.getSelectedItem();
        Empleado empleadoElegido = (Empleado) comboEmpleado.getSelectedItem();
        if (clienteElegido == null || empleadoElegido == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente y un empleado (campos obligatorios en la tabla PEDIDO).",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (campoFechaEntrega.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha de entrega es obligatoria.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        pedido.setFechaEntrega(campoFechaEntrega.getText().trim());
        pedido.setCliente(clienteElegido);
        pedido.setEmpleado(empleadoElegido);
        pedido.setEstado((String) comboEstado.getSelectedItem());
        for (DetallePedido detalle : detallesActuales) {
            pedido.agregarDetalle(detalle);
        }
        Datos.pedidos.agregar_pedido(pedido);

        limpiarDetalle();
        campoFechaEntrega.setText("");
        refrescarTablaPedidos();
        JOptionPane.showMessageDialog(this, "Pedido #" + pedido.getId() + " guardado correctamente.\nTotal: S/ "
                + String.format("%.2f", pedido.getTotal()), "Pedido guardado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarPedido() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla primero.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) modeloPedidos.getValueAt(fila, 0);
        int confirma = JOptionPane.showConfirmDialog(this, "Eliminar el pedido #" + id + "?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Datos.pedidos.eliminar_pedido(id);
            refrescarTablaPedidos();
        }
    }

    private void recargarCombos() {
        comboCliente.removeAllItems();
        for (Cliente c : Datos.clientes.getLista()) {
            comboCliente.addItem(c);
        }
        comboCliente.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            javax.swing.JLabel label = new javax.swing.JLabel(value == null ? "" : value.getNombre());
            label.setOpaque(true);
            label.setBackground(isSelected ? EstiloUI.AZUL_CLARO : EstiloUI.BLANCO);
            return label;
        });

        comboEmpleado.removeAllItems();
        for (Empleado e : Datos.empleados.getLista()) {
            comboEmpleado.addItem(e);
        }
        comboEmpleado.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            javax.swing.JLabel label = new javax.swing.JLabel(value == null ? "" : value.getNombreCompleto());
            label.setOpaque(true);
            label.setBackground(isSelected ? EstiloUI.AZUL_CLARO : EstiloUI.BLANCO);
            return label;
        });

        comboProducto.removeAllItems();
        for (Producto p : Datos.productos.getLista()) {
            comboProducto.addItem(p);
        }
        comboProducto.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            javax.swing.JLabel label = new javax.swing.JLabel(value == null ? "" : value.getNombreProducto());
            label.setOpaque(true);
            label.setBackground(isSelected ? EstiloUI.AZUL_CLARO : EstiloUI.BLANCO);
            return label;
        });
    }

    private void refrescarTablaPedidos() {
        recargarCombos();
        modeloPedidos.setRowCount(0);
        for (Pedido p : Datos.pedidos.getLista()) {
            String nombreCliente = (p.getCliente() != null) ? p.getCliente().getNombre() : "-";
            String nombreEmpleado = (p.getEmpleado() != null) ? p.getEmpleado().getNombreCompleto() : "-";
            modeloPedidos.addRow(new Object[]{
                p.getId(), p.getFechaPedido(), nombreCliente, nombreEmpleado, p.getEstado(),
                p.getFechaEntrega(), String.format("S/ %.2f", p.getTotal())
            });
        }
    }
}
