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
public class AlmacenesPanel {
    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Almacenes y Materiales");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        // ===== FILTRO =====
        ComboBox<String> cmbFiltro = new ComboBox<>();
        cmbFiltro.getItems().addAll("Almacenes", "Materiales");
        cmbFiltro.setValue("Almacenes");
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
        TableColumn<Object, String> col2 = new TableColumn<>("Nombre");
        col2.setPrefWidth(200);
        TableColumn<Object, String> col3 = new TableColumn<>("Detalle");
        col3.setPrefWidth(250);
        TableColumn<Object, String> col4 = new TableColumn<>("Extra");
        col4.setPrefWidth(150);

        tabla.getColumns().addAll(col1, col2, col3, col4);
        cargarDatos(tabla, col1, col2, col3, col4, "Almacenes");

        // ===== BOTÓN NUEVO =====
        Button btnNuevo = new Button("+ Nuevo");
        btnNuevo.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnNuevo.setOnAction(e -> {
            String tipo = cmbFiltro.getValue();
            if (tipo.equals("Almacenes")) {
                mostrarDialogAlmacen(tabla, col1, col2, col3, col4, btnNuevo);
            } else {
                mostrarDialogMaterial(tabla, col1, col2, col3, col4, btnNuevo);
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
                    if (seleccionado instanceof Almacen) {
                        AlmacenControlador.eliminar(((Almacen) seleccionado).getIdAlmacen());
                    } else if (seleccionado instanceof Material) {
                        MaterialControlador.eliminar(((Material) seleccionado).getIdMaterial());
                    }
                    cargarDatos(tabla, col1, col2, col3, col4, cmbFiltro.getValue());
                }
            });
        });

        btnBuscar.setOnAction(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            String tipo = cmbFiltro.getValue();
            ObservableList<Object> filtrados = FXCollections.observableArrayList();
            if (tipo.equals("Almacenes")) {
                for (Almacen a : AlmacenControlador.listar()) {
                    if (a.getNombre().toLowerCase().contains(texto)) filtrados.add(a);
                }
            } else {
                for (Material m : MaterialControlador.listar()) {
                    if (m.getNombreMaterial().toLowerCase().contains(texto)) filtrados.add(m);
                }
            }
            tabla.setItems(filtrados);
        });

        HBox botones = new HBox(10, btnNuevo, btnEliminar, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void mostrarDialogAlmacen(TableView<Object> tabla,
            TableColumn<Object, String> col1, TableColumn<Object, String> col2,
            TableColumn<Object, String> col3, TableColumn<Object, String> col4,
            Button owner) {

        Dialog<Almacen> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Almacén");
        dialog.setHeaderText("Ingresa los datos del almacén");
        dialog.initOwner(owner.getScene().getWindow());

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        TextField fNombre = new TextField(); fNombre.setPromptText("Nombre del almacén");
        TextField fDireccion = new TextField(); fDireccion.setPromptText("Dirección");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(new Label("Nombre:"), 0, 0); grid.add(fNombre, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1); grid.add(fDireccion, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                if (fNombre.getText().isEmpty()) {
                    mostrarAlerta("El nombre es obligatorio");
                    return null;
                }
                Almacen a = new Almacen(fNombre.getText(), fDireccion.getText());
                boolean exito = AlmacenControlador.agregar(a);
                if (!exito) {
                    mostrarAlerta("Error al guardar el almacén");
                    return null;
                }
                return a;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(a -> cargarDatos(tabla, col1, col2, col3, col4, "Almacenes"));
    }

    private void mostrarDialogMaterial(TableView<Object> tabla,
            TableColumn<Object, String> col1, TableColumn<Object, String> col2,
            TableColumn<Object, String> col3, TableColumn<Object, String> col4,
            Button owner) {

        Dialog<Material> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Material");
        dialog.setHeaderText("Ingresa los datos del material");
        dialog.initOwner(owner.getScene().getWindow());

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        TextField fNombre = new TextField(); fNombre.setPromptText("Nombre del material");
        TextField fUnidad = new TextField(); fUnidad.setPromptText("Ej: kg, m2, unidad");
        TextField fStock = new TextField(); fStock.setPromptText("Stock inicial");

        // Solo números en stock
        fStock.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*\\.?\\d*")) fStock.setText(oldText);
        });

        ComboBox<String> cmbAlmacen = new ComboBox<>();
        for (Almacen a : AlmacenControlador.listar()) {
            cmbAlmacen.getItems().add(a.getIdAlmacen() + " - " + a.getNombre());
        }
        cmbAlmacen.setPromptText("Selecciona almacén");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(new Label("Almacén:"), 0, 0); grid.add(cmbAlmacen, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1); grid.add(fNombre, 1, 1);
        grid.add(new Label("Unidad:"), 0, 2); grid.add(fUnidad, 1, 2);
        grid.add(new Label("Stock:"), 0, 3); grid.add(fStock, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                if (cmbAlmacen.getValue() == null) {
                    mostrarAlerta("Selecciona un almacén");
                    return null;
                }
                if (fNombre.getText().isEmpty()) {
                    mostrarAlerta("El nombre es obligatorio");
                    return null;
                }
                int idAlmacen = Integer.parseInt(cmbAlmacen.getValue().split(" - ")[0]);
                double stock = fStock.getText().isEmpty() ? 0 : Double.parseDouble(fStock.getText());
                Material m = new Material(idAlmacen, fNombre.getText(), fUnidad.getText(), stock);
                boolean exito = MaterialControlador.agregar(m);
                if (!exito) {
                    mostrarAlerta("Error al guardar el material");
                    return null;
                }
                return m;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(m -> cargarDatos(tabla, col1, col2, col3, col4, "Materiales"));
    }

    private void cargarDatos(TableView<Object> tabla,
            TableColumn<Object, String> col1, TableColumn<Object, String> col2,
            TableColumn<Object, String> col3, TableColumn<Object, String> col4,
            String tipo) {
        if (tipo.equals("Almacenes")) {
            col1.setText("ID"); col2.setText("Nombre");
            col3.setText("Dirección"); col4.setText("");
            col1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Almacen) data.getValue()).getIdAlmacen())));
            col2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Almacen) data.getValue()).getNombre()));
            col3.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Almacen) data.getValue()).getDireccion()));
            col4.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(""));
            tabla.setItems(FXCollections.observableArrayList(AlmacenControlador.listar()));
        } else {
            col1.setText("ID"); col2.setText("Material");
            col3.setText("Unidad"); col4.setText("Stock");
            col1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Material) data.getValue()).getIdMaterial())));
            col2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Material) data.getValue()).getNombreMaterial()));
            col3.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                ((Material) data.getValue()).getUnidadMedida()));
            col4.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(((Material) data.getValue()).getStockActual())));
            tabla.setItems(FXCollections.observableArrayList(MaterialControlador.listar()));
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
