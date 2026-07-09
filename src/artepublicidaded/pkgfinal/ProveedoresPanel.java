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



public class ProveedoresPanel {
    private Usuario usuarioActual;
    
    public ProveedoresPanel(Usuario usuario) {
        this.usuarioActual = usuario;
    }
    
    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Proveedores y Compras");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        // ===== FILTRO =====
        ComboBox<String> cmbFiltro = new ComboBox<>();
        cmbFiltro.getItems().addAll("Proveedores", "Órdenes de Compra");
        cmbFiltro.setValue("Proveedores");
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

        TableColumn<Object, String> col1 = new TableColumn<>("ID/RUC");
        col1.setPrefWidth(100);
        TableColumn<Object, String> col2 = new TableColumn<>("Nombre/Razón Social");
        col2.setPrefWidth(200);
        TableColumn<Object, String> col3 = new TableColumn<>("Teléfono/Estado");
        col3.setPrefWidth(120);
        TableColumn<Object, String> col4 = new TableColumn<>("Dirección/Total");
        col4.setPrefWidth(200);

        tabla.getColumns().addAll(col1, col2, col3, col4);
        cargarDatos(tabla, col1, col2, col3, col4, "Proveedores");

        // ===== BOTÓN NUEVO =====
        Button btnNuevo = new Button("+ Nuevo");
        btnNuevo.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnNuevo.setOnAction(e -> {
            if (cmbFiltro.getValue().equals("Proveedores")) {
                mostrarDialogProveedor(tabla, col1, col2, col3, col4, btnNuevo);
            } else {
                mostrarDialogOrdenCompra(tabla, col1, col2, col3, col4, btnNuevo);
            }
        });

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        cmbFiltro.setOnAction(e -> cargarDatos(tabla, col1, col2, col3, col4, cmbFiltro.getValue()));
        btnRefrescar.setOnAction(e -> cargarDatos(tabla, col1, col2, col3, col4, cmbFiltro.getValue()));

        btnEliminar.setOnAction(e -> {
            Object seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selecciona un registro para eliminar");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar");
            confirm.setContentText("¿Eliminar este registro?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (seleccionado instanceof Proovedor) {
                        ProovedorControlador.eliminar(((Proovedor) seleccionado).getIdProveedor());
                    } else if (seleccionado instanceof OrdenCompra) {
                        OrdenCompraControlador.eliminar(((OrdenCompra) seleccionado).getIdOrdenCompra());
                    }
                    cargarDatos(tabla, col1, col2, col3, col4, cmbFiltro.getValue());
                }
            });
        });

        btnBuscar.setOnAction(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            ObservableList<Object> filtrados = FXCollections.observableArrayList();
            if (cmbFiltro.getValue().equals("Proveedores")) {
                for (Proovedor p : ProovedorControlador.listar()) {
                    if (p.getRazonSocial().toLowerCase().contains(texto) ||
                        p.getRuc().contains(texto)) filtrados.add(p);
                }
            } else {
                for (OrdenCompra o : OrdenCompraControlador.listar()) {
                    if (String.valueOf(o.getIdOrdenCompra()).contains(texto)) filtrados.add(o);
                }
            }
            tabla.setItems(filtrados);
        });

        Button btnEditar = new Button("Editar");
        btnEditar.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnEditar.setOnAction(e -> {
        Object seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
        mostrarAlerta("Selecciona un registro para editar");
        return;
        }
        if (seleccionado instanceof Proovedor) {
        editarProveedor((Proovedor) seleccionado, tabla, col1, col2, col3, col4, btnEditar);
        } else if (seleccionado instanceof OrdenCompra) {
        cambiarEstadoOrden((OrdenCompra) seleccionado, tabla, col1, col2, col3, col4);
        }
    });

HBox botones = new HBox(10, btnNuevo, btnEditar, btnEliminar, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void mostrarDialogProveedor(TableView<Object> tabla,
            TableColumn<Object, String> col1, TableColumn<Object, String> col2,
            TableColumn<Object, String> col3, TableColumn<Object, String> col4,
            Button owner) {

        Dialog<Proovedor> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Proveedor");
        dialog.setHeaderText("Ingresa los datos del proveedor");
        dialog.initOwner(owner.getScene().getWindow());

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        TextField fRuc = new TextField(); fRuc.setPromptText("RUC (11 dígitos)");
        TextField fRazonSocial = new TextField(); fRazonSocial.setPromptText("Razón Social");
        TextField fTelefono = new TextField(); fTelefono.setPromptText("Teléfono (9 dígitos)");
        TextField fDireccion = new TextField(); fDireccion.setPromptText("Dirección");

        fRuc.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) fRuc.setText(newText.replaceAll("[^\\d]", ""));
            if (fRuc.getText().length() > 11) fRuc.setText(fRuc.getText().substring(0, 11));
        });

        fTelefono.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) fTelefono.setText(newText.replaceAll("[^\\d]", ""));
            if (fTelefono.getText().length() > 9) fTelefono.setText(fTelefono.getText().substring(0, 9));
        });

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(new Label("RUC:"), 0, 0); grid.add(fRuc, 1, 0);
        grid.add(new Label("Razón Social:"), 0, 1); grid.add(fRazonSocial, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2); grid.add(fTelefono, 1, 2);
        grid.add(new Label("Dirección:"), 0, 3); grid.add(fDireccion, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                if (fRuc.getText().length() != 11) {
                    mostrarAlerta("El RUC debe tener 11 dígitos");
                    return null;
                }
                if (fRazonSocial.getText().isEmpty()) {
                    mostrarAlerta("La razón social es obligatoria");
                    return null;
                }
                if (fTelefono.getText().length() != 9) {
                    mostrarAlerta("El teléfono debe tener 9 dígitos");
                    return null;
                }
                Proovedor p = new Proovedor(fRuc.getText(), fRazonSocial.getText(),
                                            fTelefono.getText(), fDireccion.getText());
                boolean exito = ProovedorControlador.agregar(p);
                if (!exito) {
                    mostrarAlerta("El RUC ya existe");
                    return null;
                }
                return p;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(p -> cargarDatos(tabla, col1, col2, col3, col4, "Proveedores"));
    }

    private void mostrarDialogOrdenCompra(TableView<Object> tabla,
        TableColumn<Object, String> col1, TableColumn<Object, String> col2,
        TableColumn<Object, String> col3, TableColumn<Object, String> col4,
        Button owner) {

        Dialog<OrdenCompra> dialog = new Dialog<>();
        dialog.setTitle("Nueva Orden de Compra");
        dialog.setHeaderText("Ingresa los datos de la orden");
        dialog.initOwner(owner.getScene().getWindow());
        dialog.getDialogPane().setPrefWidth(600);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        // ===== CABECERA =====
        ComboBox<String> cmbProveedor = new ComboBox<>();
        for (Proovedor p : ProovedorControlador.listar()) {
        cmbProveedor.getItems().add(p.getIdProveedor() + " - " + p.getRazonSocial());
        }
        cmbProveedor.setPromptText("Selecciona proveedor");

        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO");
        cmbEstado.setValue("PENDIENTE");

        // ===== DETALLE DE MATERIALES =====
        Label lblDetalle = new Label("Materiales a comprar:");
        lblDetalle.setStyle("-fx-font-weight: bold;");

        ComboBox<String> cmbMaterial = new ComboBox<>();
        for (Material m : MaterialControlador.listar()) {
        cmbMaterial.getItems().add(m.getIdMaterial() + " - " + m.getNombreMaterial());
        }
        cmbMaterial.setPromptText("Selecciona material");

        TextField fCantidad = new TextField(); fCantidad.setPromptText("Cantidad");
        TextField fPrecioUnit = new TextField(); fPrecioUnit.setPromptText("Precio unitario");

        fCantidad.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.matches("\\d*")) fCantidad.setText(newText.replaceAll("[^\\d]", ""));
        });
        fPrecioUnit.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.matches("\\d*\\.?\\d*")) fPrecioUnit.setText(oldText);
        });

        Button btnAgregar = new Button("+ Agregar");
        btnAgregar.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-background-radius: 5;");

        // Tabla de detalles
        TableView<DetalleOrdenCompra> tablaDetalle = new TableView<>();
        tablaDetalle.setPrefHeight(150);

        TableColumn<DetalleOrdenCompra, String> colMat = new TableColumn<>("Material");
        colMat.setPrefWidth(180);
        colMat.setCellValueFactory(data -> {
        int idMat = data.getValue().getIdMaterial();
        Material m = MaterialControlador.buscarPorId(idMat);
        return new javafx.beans.property.SimpleStringProperty(m != null ? m.getNombreMaterial() : String.valueOf(idMat));
    });

        TableColumn<DetalleOrdenCompra, String> colCant = new TableColumn<>("Cantidad");
        colCant.setPrefWidth(80);
        colCant.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        String.valueOf(data.getValue().getCantidad())));

        TableColumn<DetalleOrdenCompra, String> colPrecio = new TableColumn<>("Precio Unit.");
        colPrecio.setPrefWidth(100);
        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        "S/ " + String.format("%.2f", data.getValue().getPrecioUnitario())));

        TableColumn<DetalleOrdenCompra, String> colSub = new TableColumn<>("Subtotal");
        colSub.setPrefWidth(100);
        colSub.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        "S/ " + String.format("%.2f", data.getValue().getSubtotal())));

        tablaDetalle.getColumns().addAll(colMat, colCant, colPrecio, colSub);
        ObservableList<DetalleOrdenCompra> detalles = FXCollections.observableArrayList();
        tablaDetalle.setItems(detalles);

        Label lblTotal = new Label("Total: S/ 0.00");
        lblTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        btnAgregar.setOnAction(e -> {
        if (cmbMaterial.getValue() == null || fCantidad.getText().isEmpty() || fPrecioUnit.getText().isEmpty()) {
            mostrarAlerta("Completa todos los campos del material");
            return;
        }
        int idMat = Integer.parseInt(cmbMaterial.getValue().split(" - ")[0]);
        int cantidad = Integer.parseInt(fCantidad.getText());
        double precio = Double.parseDouble(fPrecioUnit.getText());
        double subtotal = cantidad * precio;

        DetalleOrdenCompra d = new DetalleOrdenCompra(0, idMat, cantidad, precio, subtotal);
        detalles.add(d);

        // Actualizar total
        double total = detalles.stream().mapToDouble(DetalleOrdenCompra::getSubtotal).sum();
        lblTotal.setText("Total: S/ " + String.format("%.2f", total));

        cmbMaterial.setValue(null);
        fCantidad.clear();
        fPrecioUnit.clear();
    });

    HBox filaMaterial = new HBox(10, cmbMaterial, fCantidad, fPrecioUnit, btnAgregar);
    filaMaterial.setAlignment(Pos.CENTER_LEFT);

    GridPane grid = new GridPane();
    grid.setHgap(10); grid.setVgap(10);
    grid.setPadding(new Insets(20));
    grid.add(new Label("Proveedor:"), 0, 0); grid.add(cmbProveedor, 1, 0);
    grid.add(new Label("Estado:"), 0, 1); grid.add(cmbEstado, 1, 1);
    grid.add(new Label(""), 0, 2);
    grid.add(lblDetalle, 0, 3, 2, 1);
    grid.add(filaMaterial, 0, 4, 2, 1);
    grid.add(tablaDetalle, 0, 5, 2, 1);
    grid.add(lblTotal, 1, 6);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == btnGuardar) {
            if (cmbProveedor.getValue() == null) {
                mostrarAlerta("Selecciona un proveedor");
                return null;
            }
            if (detalles.isEmpty()) {
                mostrarAlerta("Agrega al menos un material");
                return null;
            }

            double total = detalles.stream().mapToDouble(DetalleOrdenCompra::getSubtotal).sum();
            int idProveedor = Integer.parseInt(cmbProveedor.getValue().split(" - ")[0]);

            OrdenCompra o = new OrdenCompra(idProveedor, 0,
                java.time.LocalDate.now(), cmbEstado.getValue(), total);

            boolean exito = OrdenCompraControlador.agregar(o);
            if (!exito) {
                mostrarAlerta("Error al guardar la orden");
                return null;
            }

            // Obtener el id de la orden recién creada y guardar detalles
            List<OrdenCompra> ordenes = OrdenCompraControlador.listar();
            int idOrden = ordenes.get(ordenes.size() - 1).getIdOrdenCompra();

            for (DetalleOrdenCompra d : detalles) {
                d.setIdOrdenCompra(idOrden);
                DetalleOrdenCompraControlador.agregar(d);
            }

            return o;
        }
        return null;
    });

    dialog.showAndWait().ifPresent(o -> cargarDatos(tabla, col1, col2, col3, col4, "Órdenes de Compra"));
}

    private void cargarDatos(TableView<Object> tabla,
            TableColumn<Object, String> col1, TableColumn<Object, String> col2,
            TableColumn<Object, String> col3, TableColumn<Object, String> col4,
            String tipo) {
        if (tipo.equals("Proveedores")) {
            col1.setText("RUC"); col2.setText("Razón Social");
            col3.setText("Teléfono"); col4.setText("Dirección");
            col1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Proovedor) data.getValue()).getRuc()));
            col2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Proovedor) data.getValue()).getRazonSocial()));
            col3.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Proovedor) data.getValue()).getTelefono()));
            col4.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Proovedor) data.getValue()).getDireccion()));
            tabla.setItems(FXCollections.observableArrayList(ProovedorControlador.listar()));
        } else {
            col1.setText("ID"); col2.setText("Proveedor ID");
            col3.setText("Estado"); col4.setText("Total");
            col1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(((OrdenCompra) data.getValue()).getIdOrdenCompra())));
            col2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(((OrdenCompra) data.getValue()).getIdProveedor())));
            col3.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((OrdenCompra) data.getValue()).getEstado()));
            col4.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("S/ " + String.format("%.2f", ((OrdenCompra) data.getValue()).getTotal())));
            tabla.setItems(FXCollections.observableArrayList(OrdenCompraControlador.listar()));
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void editarProveedor(Proovedor proveedor, TableView<Object> tabla,
        TableColumn<Object, String> col1, TableColumn<Object, String> col2,
        TableColumn<Object, String> col3, TableColumn<Object, String> col4,
        Button owner) {

    Dialog<Proovedor> dialog = new Dialog<>();
    dialog.setTitle("Editar Proveedor");
    dialog.setHeaderText("Modifica los datos del proveedor");
    dialog.initOwner(owner.getScene().getWindow());

    ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
    ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

    TextField fRazonSocial = new TextField(proveedor.getRazonSocial());
    TextField fTelefono = new TextField(proveedor.getTelefono());
    TextField fDireccion = new TextField(proveedor.getDireccion());
    Label fRuc = new Label(proveedor.getRuc()); // RUC no se puede cambiar

    fTelefono.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.matches("\\d*")) fTelefono.setText(newText.replaceAll("[^\\d]", ""));
        if (fTelefono.getText().length() > 9) fTelefono.setText(fTelefono.getText().substring(0, 9));
    });

    GridPane grid = new GridPane();
    grid.setHgap(10); grid.setVgap(10);
    grid.setPadding(new Insets(20));
    grid.add(new Label("RUC:"), 0, 0); grid.add(fRuc, 1, 0);
    grid.add(new Label("Razón Social:"), 0, 1); grid.add(fRazonSocial, 1, 1);
    grid.add(new Label("Teléfono:"), 0, 2); grid.add(fTelefono, 1, 2);
    grid.add(new Label("Dirección:"), 0, 3); grid.add(fDireccion, 1, 3);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == btnGuardar) {
            if (fRazonSocial.getText().isEmpty()) {
                mostrarAlerta("La razón social es obligatoria");
                return null;
            }
            if (fTelefono.getText().length() != 9) {
                mostrarAlerta("El teléfono debe tener 9 dígitos");
                return null;
            }
            proveedor.setRazonSocial(fRazonSocial.getText());
            proveedor.setTelefono(fTelefono.getText());
            proveedor.setDireccion(fDireccion.getText());
            ProovedorControlador.actualizar(proveedor);
            return proveedor;
        }
        return null;
    });

    dialog.showAndWait().ifPresent(p -> cargarDatos(tabla, col1, col2, col3, col4, "Proveedores"));
}

private void cambiarEstadoOrden(OrdenCompra orden, TableView<Object> tabla,
        TableColumn<Object, String> col1, TableColumn<Object, String> col2,
        TableColumn<Object, String> col3, TableColumn<Object, String> col4) {

    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Cambiar Estado");
    dialog.setHeaderText("Orden #" + orden.getIdOrdenCompra());

    ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
    ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

    ComboBox<String> cmbEstado = new ComboBox<>();
    cmbEstado.getItems().addAll("PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO");
    cmbEstado.setValue(orden.getEstado());

    GridPane grid = new GridPane();
    grid.setHgap(10); grid.setVgap(10);
    grid.setPadding(new Insets(20));
    grid.add(new Label("Estado:"), 0, 0); grid.add(cmbEstado, 1, 0);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == btnGuardar) {
            OrdenCompraControlador.actualizarEstado(orden.getIdOrdenCompra(), cmbEstado.getValue());
            return cmbEstado.getValue();
        }
        return null;
    });

    dialog.showAndWait().ifPresent(estado -> cargarDatos(tabla, col1, col2, col3, col4, "Órdenes de Compra"));
}
}
