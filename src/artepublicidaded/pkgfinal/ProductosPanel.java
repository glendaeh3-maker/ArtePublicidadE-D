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
public class ProductosPanel {
    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Productos y Categorías");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        // ===== FILTRO POR CATEGORÍA =====
        ComboBox<String> cmbCategoria = new ComboBox<>();
        cmbCategoria.getItems().addAll("TODOS", "IMPRESION", "SEÑALETICA", "BTL", "DISEÑO_GRAFICO");
        cmbCategoria.setValue("TODOS");
        cmbCategoria.setStyle("-fx-pref-height: 35px;");

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por nombre o código...");
        txtBuscar.setStyle("-fx-pref-width: 250px; -fx-pref-height: 35px; -fx-background-radius: 6;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        HBox barraBusqueda = new HBox(10, cmbCategoria, txtBuscar, btnBuscar);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);

        // ===== TABLA =====
        TableView<Producto> tabla = new TableView<>();
        tabla.setPrefHeight(350);

        TableColumn<Producto, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo()));
        colCodigo.setPrefWidth(100);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreProducto()));
        colNombre.setPrefWidth(200);

        TableColumn<Producto, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        colDescripcion.setPrefWidth(200);

        TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            "S/ " + String.format("%.2f", data.getValue().getPrecioUnitario())));
        colPrecio.setPrefWidth(90);

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategoria()));
        colCategoria.setPrefWidth(120);

        tabla.getColumns().addAll(colCodigo, colNombre, colDescripcion, colPrecio, colCategoria);

        cargarDatos(tabla, "TODOS");

        // ===== BOTÓN NUEVO PRODUCTO =====
        Button btnNuevoProducto = new Button("+ Nuevo Producto");
        btnNuevoProducto.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnNuevoProducto.setOnAction(e -> {
            Dialog<Producto> dialog = new Dialog<>();
            dialog.setTitle("Nuevo Producto");
            dialog.setHeaderText("Ingresa los datos del producto");
            dialog.initOwner(btnNuevoProducto.getScene().getWindow());

            ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

            TextField fCodigo = new TextField(); fCodigo.setPromptText("Ej: IMP001");
            TextField fNombre = new TextField(); fNombre.setPromptText("Nombre del producto");
            TextField fDescripcion = new TextField(); fDescripcion.setPromptText("Descripción");
            TextField fPrecio = new TextField(); fPrecio.setPromptText("Precio (ej: 25.50)");

            ComboBox<String> fCategoria = new ComboBox<>();
            fCategoria.getItems().addAll("IMPRESION", "SEÑALETICA", "BTL", "DISEÑO_GRAFICO");
            fCategoria.setPromptText("Selecciona categoría");

            // Solo números y punto decimal en precio
            fPrecio.textProperty().addListener((obs, oldText, newText) -> {
                if (!newText.matches("\\d*\\.?\\d*")) fPrecio.setText(oldText);
            });

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20));

            grid.add(new Label("Código:"), 0, 0); grid.add(fCodigo, 1, 0);
            grid.add(new Label("Nombre:"), 0, 1); grid.add(fNombre, 1, 1);
            grid.add(new Label("Descripción:"), 0, 2); grid.add(fDescripcion, 1, 2);
            grid.add(new Label("Precio:"), 0, 3); grid.add(fPrecio, 1, 3);
            grid.add(new Label("Categoría:"), 0, 4); grid.add(fCategoria, 1, 4);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                    if (fCodigo.getText().isEmpty()) {
                        mostrarAlerta("El código es obligatorio");
                        return null;
                    }
                    if (fNombre.getText().isEmpty()) {
                        mostrarAlerta("El nombre es obligatorio");
                        return null;
                    }
                    if (fPrecio.getText().isEmpty()) {
                        mostrarAlerta("El precio es obligatorio");
                        return null;
                    }
                    if (fCategoria.getValue() == null) {
                        mostrarAlerta("Selecciona una categoría");
                        return null;
                    }

                    // Crear producto según categoría
                    Producto p;
                    switch (fCategoria.getValue()) {
                        case "IMPRESION": p = new ProductoImpresion(); break;
                        case "SEÑALETICA": p = new ProductoSeñaletica(); break;
                        case "BTL": p = new ProductoPublicidadBTL(); break;
                        default: p = new ProductoDiseñoGrafico(); break;
                    }
                    p.setCodigo(fCodigo.getText());
                    p.setNombreProducto(fNombre.getText());
                    p.setDescripcion(fDescripcion.getText());
                    p.setPrecioUnitario(Double.parseDouble(fPrecio.getText()));

                    boolean exito = ProductoControlador.agregar(p);
                    if (!exito) {
                        mostrarAlerta("El código ya existe");
                        return null;
                    }
                    return p;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(p -> {
                cargarDatos(tabla, cmbCategoria.getValue());
            });
        });

        // ===== BOTÓN ELIMINAR =====
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnEliminar.setOnAction(e -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selecciona un producto para eliminar");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar eliminación");
            confirm.setContentText("¿Eliminar el producto " + seleccionado.getNombreProducto() + "?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ProductoControlador.eliminar(seleccionado.getCodigo());
                    cargarDatos(tabla, cmbCategoria.getValue());
                }
            });
        });

        btnRefrescar.setOnAction(e -> cargarDatos(tabla, cmbCategoria.getValue()));

        cmbCategoria.setOnAction(e -> cargarDatos(tabla, cmbCategoria.getValue()));

        btnBuscar.setOnAction(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            ObservableList<Producto> filtrados = FXCollections.observableArrayList();
            for (Producto p : ProductoControlador.listar()) {
                if (p.getNombreProducto().toLowerCase().contains(texto) ||
                    p.getCodigo().toLowerCase().contains(texto)) {
                    filtrados.add(p);
                }
            }
            tabla.setItems(filtrados);
        });

        HBox botones = new HBox(10, btnNuevoProducto, btnEliminar, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void cargarDatos(TableView<Producto> tabla, String categoria) {
        ObservableList<Producto> lista = FXCollections.observableArrayList();
        for (Producto p : ProductoControlador.listar()) {
            if (categoria.equals("TODOS") || p.getCategoria().equals(categoria)) {
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
}
