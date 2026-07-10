/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;
/**
 *
 * @author Genes
 */
public class PedidosPanel {
    private Usuario usuarioActual;

    public PedidosPanel(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Pedidos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        // ===== FILTRO POR ESTADO =====
        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("TODOS", "PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO");
        cmbEstado.setValue("TODOS");
        cmbEstado.setStyle("-fx-pref-height: 35px;");

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por ID o cliente...");
        txtBuscar.setStyle("-fx-pref-width: 250px; -fx-pref-height: 35px; -fx-background-radius: 6;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        HBox barraBusqueda = new HBox(10, cmbEstado, txtBuscar, btnBuscar);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);

        // ===== TABLA =====
        TableView<Pedido> tabla = new TableView<>();
        tabla.setPrefHeight(350);

        TableColumn<Pedido, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            String.valueOf(data.getValue().getId())));
        colId.setPrefWidth(60);

        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente ID");
        colCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            String.valueOf(data.getValue().getClienteId())));
        colCliente.setPrefWidth(100);

        TableColumn<Pedido, String> colFecha = new TableColumn<>("Fecha Pedido");
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getFechaPedido()));
        colFecha.setPrefWidth(120);

        TableColumn<Pedido, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getEstado()));
        colEstado.setPrefWidth(120);

        TableColumn<Pedido, String> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            "S/ " + String.format("%.2f", data.getValue().getTotal())));
        colTotal.setPrefWidth(100);

        tabla.getColumns().addAll(colId, colCliente, colFecha, colEstado, colTotal);
        cargarDatos(tabla, "TODOS");

        // ===== BOTÓN NUEVO PEDIDO =====
        Button btnNuevo = new Button("+ Nuevo Pedido");
        btnNuevo.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnNuevo.setOnAction(e -> abrirNuevoPedido(btnNuevo.getScene().getWindow(), tabla, cmbEstado));

        // ===== BOTÓN CAMBIAR ESTADO =====
        Button btnCambiarEstado = new Button("Cambiar Estado");
        btnCambiarEstado.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnCambiarEstado.setOnAction(e -> {
            Pedido seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selecciona un pedido");
                return;
            }
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Cambiar Estado");
            dialog.setHeaderText("Pedido #" + seleccionado.getId());
            dialog.initOwner(btnCambiarEstado.getScene().getWindow());

            ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

            ComboBox<String> cmbNuevoEstado = new ComboBox<>();
            cmbNuevoEstado.getItems().addAll("PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO");
            cmbNuevoEstado.setValue(seleccionado.getEstado());

            GridPane grid = new GridPane();
            grid.setHgap(10); grid.setVgap(10);
            grid.setPadding(new Insets(20));
            grid.add(new Label("Estado:"), 0, 0); grid.add(cmbNuevoEstado, 1, 0);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                    PedidoControlador.actualizarEstadoYEmpleado(seleccionado.getId(), cmbNuevoEstado.getValue(), usuarioActual.getId());
                    return cmbNuevoEstado.getValue();
                }
                return null;
            });

            dialog.showAndWait().ifPresent(estado -> cargarDatos(tabla, cmbEstado.getValue()));
        });

        // ===== BOTÓN REFRESCAR =====
        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");
        btnRefrescar.setOnAction(e -> cargarDatos(tabla, cmbEstado.getValue()));

        cmbEstado.setOnAction(e -> cargarDatos(tabla, cmbEstado.getValue()));

        btnBuscar.setOnAction(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            ObservableList<Pedido> filtrados = FXCollections.observableArrayList();
            for (Pedido p : PedidoControlador.listar()) {
                if (String.valueOf(p.getId()).contains(texto) ||
                    String.valueOf(p.getClienteId()).contains(texto)) {
                    filtrados.add(p);
                }
            }
            tabla.setItems(filtrados);
        });

        HBox botones = new HBox(10, btnNuevo, btnCambiarEstado, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void cargarDatos(TableView<Pedido> tabla, String estado) {
        ObservableList<Pedido> lista = FXCollections.observableArrayList();
        for (Pedido p : PedidoControlador.listar()) {
            if (estado.equals("TODOS") || p.getEstado().equals(estado)) {
                lista.add(p);
            }
        }
        tabla.setItems(lista);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // ===== Método público: permite abrir el formulario "Nuevo Pedido" desde otras pantallas =====
public void abrirNuevoPedido(javafx.stage.Window owner) {
    abrirNuevoPedido(owner, null, null);
}

private void abrirNuevoPedido(javafx.stage.Window owner, TableView<Pedido> tabla, ComboBox<String> cmbEstado) {
    Dialog<Pedido> dialog = new Dialog<>();
    dialog.setTitle("Nuevo Pedido");
    dialog.setHeaderText("Crea un nuevo pedido");
    dialog.initOwner(owner);
    dialog.getDialogPane().setPrefWidth(550);

    ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
    ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

    ComboBox<String> cmbCliente = new ComboBox<>();
    for (Cliente c : ClienteControlador.listar()) {
        cmbCliente.getItems().add(c.getId() + " - " + c.getNombre() + " " + c.getApellido_paterno());
    }
    cmbCliente.setPromptText("Selecciona cliente");

    DatePicker fFechaEntrega = new DatePicker();
    fFechaEntrega.setPromptText("Selecciona fecha de entrega");

    ComboBox<String> cmbEstadoPedido = new ComboBox<>();
    cmbEstadoPedido.getItems().addAll("PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO");
    cmbEstadoPedido.setValue("PENDIENTE");

    // ===== SELECCIÓN DE PRODUCTOS =====
    Label lblDetalle = new Label("Productos del pedido:");
    lblDetalle.setStyle("-fx-font-weight: bold;");

    ComboBox<String> cmbProducto = new ComboBox<>();
    List<Producto> productosDisponibles = ProductoControlador.listar();
    for (Producto p : productosDisponibles) {
        cmbProducto.getItems().add(p.getCodigo() + " - " + p.getNombreProducto());
    }
    cmbProducto.setPromptText("Selecciona producto");

    TextField fCantidadProd = new TextField();
    fCantidadProd.setPromptText("Cantidad");
    fCantidadProd.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.matches("\\d*")) fCantidadProd.setText(newText.replaceAll("[^\\d]", ""));
    });

    Button btnAgregarProducto = new Button("+ Agregar");
    btnAgregarProducto.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-background-radius: 5;");

    TableView<DetallePedido> tablaDetalle = new TableView<>();
    tablaDetalle.setPrefHeight(140);

    TableColumn<DetallePedido, String> colProd = new TableColumn<>("Producto");
    colProd.setPrefWidth(220);
    colProd.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        data.getValue().getProducto().getNombreProducto()));

    TableColumn<DetallePedido, String> colCant = new TableColumn<>("Cantidad");
    colCant.setPrefWidth(80);
    colCant.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        String.valueOf(data.getValue().getCantidad())));

    TableColumn<DetallePedido, String> colSub = new TableColumn<>("Subtotal");
    colSub.setPrefWidth(100);
    colSub.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        "S/ " + String.format("%.2f", data.getValue().getSubtotal())));

    tablaDetalle.getColumns().addAll(colProd, colCant, colSub);
    ObservableList<DetallePedido> detallesTemp = FXCollections.observableArrayList();
    tablaDetalle.setItems(detallesTemp);

    Label lblTotal = new Label("Total: S/ 0.00");
    lblTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

    btnAgregarProducto.setOnAction(ev -> {
        if (cmbProducto.getValue() == null || fCantidadProd.getText().isEmpty()) {
            mostrarAlerta("Selecciona un producto y una cantidad");
            return;
        }
        String codigo = cmbProducto.getValue().split(" - ")[0];
        Producto prod = productosDisponibles.stream()
            .filter(p -> p.getCodigo().equals(codigo))
            .findFirst().orElse(null);
        if (prod == null) return;

        int cantidad = Integer.parseInt(fCantidadProd.getText());

        DetallePedido d = new DetallePedido();
        d.setProducto(prod);
        d.setCantidad(cantidad);
        d.calcularSubtotal();
        detallesTemp.add(d);

        double total = detallesTemp.stream().mapToDouble(DetallePedido::getSubtotal).sum();
        lblTotal.setText("Total: S/ " + String.format("%.2f", total));

        cmbProducto.setValue(null);
        fCantidadProd.clear();
    });

    HBox filaProducto = new HBox(10, cmbProducto, fCantidadProd, btnAgregarProducto);
    filaProducto.setAlignment(Pos.CENTER_LEFT);

    GridPane grid = new GridPane();
    grid.setHgap(10); grid.setVgap(10);
    grid.setPadding(new Insets(20));
    grid.add(new Label("Cliente:"), 0, 0); grid.add(cmbCliente, 1, 0);
    grid.add(new Label("Fecha entrega:"), 0, 1); grid.add(fFechaEntrega, 1, 1);
    grid.add(new Label("Estado:"), 0, 2); grid.add(cmbEstadoPedido, 1, 2);
    grid.add(lblDetalle, 0, 3, 2, 1);
    grid.add(filaProducto, 0, 4, 2, 1);
    grid.add(tablaDetalle, 0, 5, 2, 1);
    grid.add(lblTotal, 0, 6, 2, 1);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == btnGuardar) {
            if (cmbCliente.getValue() == null) {
                mostrarAlerta("Selecciona un cliente");
                return null;
            }
            if (detallesTemp.isEmpty()) {
                mostrarAlerta("Agrega al menos un producto");
                return null;
            }
            int idCliente = Integer.parseInt(cmbCliente.getValue().split(" - ")[0]);
            double total = detallesTemp.stream().mapToDouble(DetallePedido::getSubtotal).sum();

            Pedido p = new Pedido();
            p.setClienteId(idCliente);
            p.setEmpleadoId(usuarioActual.getId());
            p.setFechaPedido(java.time.LocalDate.now());
            if (fFechaEntrega.getValue() != null) {
                p.setFechaEntrega(fFechaEntrega.getValue());
            }
            p.setEstado(cmbEstadoPedido.getValue());
            p.setTotal(total);

            boolean exito = PedidoControlador.agregar(p);
            if (!exito) {
                mostrarAlerta("Error al guardar el pedido");
                return null;
            }

            List<Pedido> pedidos = PedidoControlador.listar();
            int idPedido = pedidos.get(pedidos.size() - 1).getId();

            for (DetallePedido d : detallesTemp) {
                DetallePedidoControlador.agregar(idPedido, d);
            }

            return p;
        }
        return null;
    });

    dialog.showAndWait().ifPresent(p -> {
        if (tabla != null && cmbEstado != null) {
            cargarDatos(tabla, cmbEstado.getValue());
        }
    });
}
}
