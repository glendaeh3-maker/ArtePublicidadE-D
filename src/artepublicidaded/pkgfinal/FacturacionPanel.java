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
/**
 *
 * @author Genes
 */
public class FacturacionPanel {
    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Facturación y Pagos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        // ===== FILTRO =====
        ComboBox<String> cmbFiltro = new ComboBox<>();
        cmbFiltro.getItems().addAll("Facturas", "Pagos");
        cmbFiltro.setValue("Facturas");
        cmbFiltro.setStyle("-fx-pref-height: 35px;");

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar...");
        txtBuscar.setStyle("-fx-pref-width: 250px; -fx-pref-height: 35px; -fx-background-radius: 6;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        HBox barraBusqueda = new HBox(10, cmbFiltro, txtBuscar, btnBuscar);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);

        // ===== TABLA =====
        TableView<Object> tabla = new TableView<>();
        tabla.setPrefHeight(350);

        TableColumn<Object, String> col1 = new TableColumn<>("ID");
        col1.setPrefWidth(60);
        TableColumn<Object, String> col2 = new TableColumn<>("Cliente");
        col2.setPrefWidth(150);
        TableColumn<Object, String> col3 = new TableColumn<>("Fecha");
        col3.setPrefWidth(120);
        TableColumn<Object, String> col4 = new TableColumn<>("Total/Monto");
        col4.setPrefWidth(120);
        TableColumn<Object, String> col5 = new TableColumn<>("Estado");
        col5.setPrefWidth(120);

        tabla.getColumns().addAll(col1, col2, col3, col4, col5);
        cargarDatos(tabla, col1, col2, col3, col4, col5, "Facturas");

        // ===== BOTONES =====
        Button btnNuevaFactura = new Button("+ Nueva Factura");
        btnNuevaFactura.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        Button btnNuevoPago = new Button("+ Registrar Pago");
        btnNuevoPago.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        cmbFiltro.setOnAction(e -> {
            cargarDatos(tabla, col1, col2, col3, col4, col5, cmbFiltro.getValue());
            btnNuevaFactura.setVisible(cmbFiltro.getValue().equals("Facturas"));
            btnNuevoPago.setVisible(cmbFiltro.getValue().equals("Pagos"));
        });

        btnRefrescar.setOnAction(e -> cargarDatos(tabla, col1, col2, col3, col4, col5, cmbFiltro.getValue()));

        // ===== DIALOG NUEVA FACTURA =====
        btnNuevaFactura.setOnAction(e -> {
            Dialog<Factura> dialog = new Dialog<>();
            dialog.setTitle("Nueva Factura");
            dialog.setHeaderText("Genera una factura para un pedido completado");
            dialog.initOwner(btnNuevaFactura.getScene().getWindow());
            dialog.getDialogPane().setPrefWidth(500);

            ButtonType btnGuardar = new ButtonType("Generar", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

            // Seleccionar cliente
            ComboBox<String> cmbCliente = new ComboBox<>();
            for (Cliente c : ClienteControlador.listar()) {
                cmbCliente.getItems().add(c.getId() + " - " + c.getNombre() + " " + c.getApellido_paterno());
            }
            cmbCliente.setPromptText("Selecciona cliente");

            TextField fNumero = new TextField();
            fNumero.setPromptText("Ej: F001-0001");

            // Tabla de pedidos del cliente
            TableView<Pedido> tablaPedidos = new TableView<>();
            tablaPedidos.setPrefHeight(150);

            TableColumn<Pedido, String> colPedidoId = new TableColumn<>("ID Pedido");
            colPedidoId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(data.getValue().getId())));
            colPedidoId.setPrefWidth(80);

            TableColumn<Pedido, String> colEstadoPedido = new TableColumn<>("Estado");
            colEstadoPedido.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEstado()));
            colEstadoPedido.setPrefWidth(100);

            TableColumn<Pedido, String> colTotalPedido = new TableColumn<>("Total");
            colTotalPedido.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                "S/ " + String.format("%.2f", data.getValue().getTotal())));
            colTotalPedido.setPrefWidth(100);

            tablaPedidos.getColumns().addAll(colPedidoId, colEstadoPedido, colTotalPedido);

            cmbCliente.setOnAction(ev -> {
                if (cmbCliente.getValue() != null) {
                    int idCliente = Integer.parseInt(cmbCliente.getValue().split(" - ")[0]);
                    ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
                    for (Pedido p : PedidoControlador.listar()) {
                        if (p.getClienteId() == idCliente && p.getEstado().equals("COMPLETADO")) {
                            pedidos.add(p);
                        }
                    }
                    tablaPedidos.setItems(pedidos);
                }
            });

            GridPane grid = new GridPane();
            grid.setHgap(10); grid.setVgap(10);
            grid.setPadding(new Insets(20));
            grid.add(new Label("Cliente:"), 0, 0); grid.add(cmbCliente, 1, 0);
            grid.add(new Label("N° Factura:"), 0, 1); grid.add(fNumero, 1, 1);
            grid.add(new Label("Pedidos completados:"), 0, 2);
            grid.add(tablaPedidos, 0, 3, 2, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                    if (cmbCliente.getValue() == null) {
                        mostrarAlerta("Selecciona un cliente");
                        return null;
                    }
                    if (fNumero.getText().isEmpty()) {
                        mostrarAlerta("Ingresa el número de factura");
                        return null;
                    }
                    int idCliente = Integer.parseInt(cmbCliente.getValue().split(" - ")[0]);
                    Factura f = new Factura(idCliente, fNumero.getText(), java.time.LocalDate.now());
                    boolean exito = FacturaControlador.agregar(f);
                    if (!exito) {
                        mostrarAlerta("El número de factura ya existe");
                        return null;
                    }
                    return f;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(f -> cargarDatos(tabla, col1, col2, col3, col4, col5, "Facturas"));
        });

        // ===== DIALOG NUEVO PAGO =====
        btnNuevoPago.setVisible(false);
        btnNuevoPago.setOnAction(e -> {
            Dialog<Pago> dialog = new Dialog<>();
            dialog.setTitle("Registrar Pago");
            dialog.setHeaderText("Registra el pago de un cliente");
            dialog.initOwner(btnNuevoPago.getScene().getWindow());

            ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

            ComboBox<String> cmbCliente = new ComboBox<>();
            for (Cliente c : ClienteControlador.listar()) {
                cmbCliente.getItems().add(c.getId() + " - " + c.getNombre() + " " + c.getApellido_paterno());
            }
            cmbCliente.setPromptText("Selecciona cliente");

            TextField fMonto = new TextField(); fMonto.setPromptText("Monto");
            fMonto.textProperty().addListener((obs, oldText, newText) -> {
                if (!newText.matches("\\d*\\.?\\d*")) fMonto.setText(oldText);
            });

            ComboBox<String> cmbMetodo = new ComboBox<>();
            cmbMetodo.getItems().addAll("EFECTIVO", "TARJETA", "TRANSFERENCIA");
            cmbMetodo.setValue("EFECTIVO");

            ComboBox<String> cmbEstado = new ComboBox<>();
            cmbEstado.getItems().addAll("PENDIENTE", "COMPLETADO");
            cmbEstado.setValue("PENDIENTE");

            GridPane grid = new GridPane();
            grid.setHgap(10); grid.setVgap(10);
            grid.setPadding(new Insets(20));
            grid.add(new Label("Cliente:"), 0, 0); grid.add(cmbCliente, 1, 0);
            grid.add(new Label("Monto:"), 0, 1); grid.add(fMonto, 1, 1);
            grid.add(new Label("Método de pago:"), 0, 2); grid.add(cmbMetodo, 1, 2);
            grid.add(new Label("Estado:"), 0, 3); grid.add(cmbEstado, 1, 3);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                    if (cmbCliente.getValue() == null) {
                        mostrarAlerta("Selecciona un cliente");
                        return null;
                    }
                    if (fMonto.getText().isEmpty()) {
                        mostrarAlerta("Ingresa el monto");
                        return null;
                    }
                    int idCliente = Integer.parseInt(cmbCliente.getValue().split(" - ")[0]);
                    Pago p = new Pago(idCliente, java.time.LocalDate.now(),
                        Double.parseDouble(fMonto.getText()),
                        cmbMetodo.getValue(), cmbEstado.getValue());
                    boolean exito = PagoControlador.agregar(p);
                    if (!exito) {
                        mostrarAlerta("Error al registrar el pago");
                        return null;
                    }
                    return p;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(p -> cargarDatos(tabla, col1, col2, col3, col4, col5, "Pagos"));
        });

        HBox botones = new HBox(10, btnNuevaFactura, btnNuevoPago, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void cargarDatos(TableView<Object> tabla,
            TableColumn<Object, String> col1, TableColumn<Object, String> col2,
            TableColumn<Object, String> col3, TableColumn<Object, String> col4,
            TableColumn<Object, String> col5, String tipo) {

        if (tipo.equals("Facturas")) {
            col1.setText("ID"); col2.setText("Cliente ID");
            col3.setText("N° Factura"); col4.setText("Fecha"); col5.setText("");
            col1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Factura) data.getValue()).getIdFactura())));
            col2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Factura) data.getValue()).getIdCliente())));
            col3.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Factura) data.getValue()).getNumeroFactura()));
            col4.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Factura) data.getValue()).getFechaEmision().toString()));
            col5.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(""));
            tabla.setItems(FXCollections.observableArrayList(FacturaControlador.listar()));
        } else {
            col1.setText("ID"); col2.setText("Cliente ID");
            col3.setText("Fecha"); col4.setText("Monto"); col5.setText("Estado");
            col1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Pago) data.getValue()).getIdPago())));
            col2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Pago) data.getValue()).getIdCliente())));
            col3.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Pago) data.getValue()).getFechaPago().toString()));
            col4.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                "S/ " + String.format("%.2f", ((Pago) data.getValue()).getMonto())));
            col5.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Pago) data.getValue()).getEstado()));
            tabla.setItems(FXCollections.observableArrayList(PagoControlador.listar()));
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
