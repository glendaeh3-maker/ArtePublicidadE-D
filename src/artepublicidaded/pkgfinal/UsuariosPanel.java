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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 *
 * @author Genes
 */
public class UsuariosPanel {
    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        // ===== TÍTULO =====
        Label lblTitulo = new Label("Usuarios y Accesos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        // ===== BARRA DE BÚSQUEDA =====
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por nombre de usuario...");
        txtBuscar.setStyle("-fx-pref-width: 300px; -fx-pref-height: 35px; -fx-background-radius: 6;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle(
            "-fx-background-color: #1a237e; -fx-text-fill: white; " +
            "-fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        HBox barraBusqueda = new HBox(10, txtBuscar, btnBuscar);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);

        // ===== TABLA =====
        TableView<Usuario> tabla = new TableView<>();
        tabla.setStyle("-fx-background-radius: 10;");
        tabla.setPrefHeight(400);

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(150);

        TableColumn<Usuario, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colUsuario.setPrefWidth(120);

        TableColumn<Usuario, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDni.setPrefWidth(90);

        TableColumn<Usuario, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colRol.setPrefWidth(90);

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colCorreo.setPrefWidth(200);

        tabla.getColumns().addAll(colId, colNombre, colUsuario, colDni, colRol, colCorreo);

        // ===== CARGAR DATOS =====
        cargarDatos(tabla);

        // ===== BOTONES =====
        Button btnEliminar = new Button("Eliminar Usuario");
        btnEliminar.setStyle(
            "-fx-background-color: #c62828; -fx-text-fill: white; " +
            "-fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.setStyle(
            "-fx-background-color: #388e3c; -fx-text-fill: white; " +
            "-fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;"
        );

        btnRefrescar.setOnAction(e -> cargarDatos(tabla));

        btnEliminar.setOnAction(e -> {
            Usuario seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selecciona un usuario para eliminar");
                return;
            }
            if (seleccionado.getRol().equals("ADMIN")) {
                mostrarAlerta("No puedes eliminar al administrador");
                return;
            }
            // Confirmar eliminación
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar eliminación");
            confirm.setContentText("¿Eliminar al usuario " + seleccionado.getNombreUsuario() + "?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Por ahora solo refresca, luego conectamos con MySQL
                    cargarDatos(tabla);
                }
            });
        });

        // Búsqueda
        btnBuscar.setOnAction(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            ObservableList<Usuario> todos = FXCollections.observableArrayList(
                UsuarioControlador.listarTodos()
            );
            ObservableList<Usuario> filtrados = FXCollections.observableArrayList();
            for (Usuario u : todos) {
                if (u.getNombreUsuario().toLowerCase().contains(texto) ||
                    u.getNombre().toLowerCase().contains(texto)) {
                    filtrados.add(u);
                }
            }
            tabla.setItems(filtrados);
        });

        HBox botones = new HBox(10, btnEliminar, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void cargarDatos(TableView<Usuario> tabla) {
        ObservableList<Usuario> lista = FXCollections.observableArrayList(
            UsuarioControlador.listarTodos()
        );
        tabla.setItems(lista);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
