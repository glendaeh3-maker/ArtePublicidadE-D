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
public class EmpleadosClientesPanel {
    public VBox getPanel() {
        return getPanel("Empleados");
    }

     public VBox getPanel(String filtroInicial) {
        return getPanel(filtroInicial, false);
    }

    // Vista simplificada: solo Clientes, sin opción de ver Empleados
    public VBox getPanelSoloClientes() {
        return getPanel("Clientes", true);
    }

    public VBox getPanel(String filtroInicial, boolean soloClientes) {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label(soloClientes ? "Clientes" : "Empleados y Clientes");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        ComboBox<String> cmbFiltro = new ComboBox<>();
        if (soloClientes) {
            cmbFiltro.getItems().add("Clientes");
        } else {
            cmbFiltro.getItems().addAll("Empleados", "Clientes");
        }
        cmbFiltro.setValue(filtroInicial);
        cmbFiltro.setVisible(!soloClientes);
        cmbFiltro.setManaged(!soloClientes);
        cmbFiltro.setStyle("-fx-pref-height: 35px; -fx-background-radius: 6;");

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por nombre o DNI...");
        txtBuscar.setStyle("-fx-pref-width: 280px; -fx-pref-height: 35px; -fx-background-radius: 6;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        HBox barraBusqueda = new HBox(10, cmbFiltro, txtBuscar, btnBuscar);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);

        TableView<Object> tabla = new TableView<>();
        tabla.setPrefHeight(300);

        TableColumn<Object, String> colDni = new TableColumn<>("DNI");
        colDni.setPrefWidth(90);
        TableColumn<Object, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setPrefWidth(150);
        TableColumn<Object, String> colApellidoP = new TableColumn<>("Ap. Paterno");
        colApellidoP.setPrefWidth(120);
        TableColumn<Object, String> colApellidoM = new TableColumn<>("Ap. Materno");
        colApellidoM.setPrefWidth(120);
        TableColumn<Object, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setPrefWidth(90);
        TableColumn<Object, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setPrefWidth(180);
        TableColumn<Object, String> colExtra = new TableColumn<>("Cargo/Dirección");
        colExtra.setPrefWidth(150);

        tabla.getColumns().addAll(colDni, colNombre, colApellidoP, colApellidoM,
                                   colTelefono, colCorreo, colExtra);

        // ===== BOTÓN NUEVO EMPLEADO =====
        Button btnNuevoEmpleado = new Button("+ Nuevo Empleado");
        btnNuevoEmpleado.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnNuevoEmpleado.setOnAction(e -> {
            Dialog<Empleado> dialog = new Dialog<>();
            dialog.setTitle("Nuevo Empleado");
            dialog.setHeaderText("Ingresa los datos del empleado");
            dialog.initOwner(btnNuevoEmpleado.getScene().getWindow());

            ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);
            

            TextField fDni = new TextField(); fDni.setPromptText("DNI (8 dígitos)");
            TextField fNombre = new TextField(); fNombre.setPromptText("Nombre");
            TextField fApellidoP = new TextField(); fApellidoP.setPromptText("Apellido Paterno");
            TextField fApellidoM = new TextField(); fApellidoM.setPromptText("Apellido Materno");
            TextField fTelefono = new TextField(); fTelefono.setPromptText("Teléfono (9 dígitos)");
            TextField fCorreo = new TextField(); fCorreo.setPromptText("Correo");
            TextField fCargo = new TextField(); fCargo.setPromptText("Cargo");
            TextField fUsuario = new TextField(); fUsuario.setPromptText("Nombre de usuario");
            PasswordField fClave = new PasswordField(); fClave.setPromptText("Contraseña");
            Label lblError = new Label("");
            lblError.setStyle("-fx-text-fill: red;");
            // Restricción DNI - solo números y máximo 8
            fDni.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) fDni.setText(newText.replaceAll("[^\\d]", ""));
            if (fDni.getText().length() > 8) fDni.setText(fDni.getText().substring(0, 8));
            });

            // Restricción Teléfono - solo números y máximo 9
            fTelefono.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) fTelefono.setText(newText.replaceAll("[^\\d]", ""));
            if (fTelefono.getText().length() > 9) fTelefono.setText(fTelefono.getText().substring(0, 9));
            });

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20));

            grid.add(new Label("DNI:"), 0, 0); grid.add(fDni, 1, 0);
            grid.add(new Label("Nombre:"), 0, 1); grid.add(fNombre, 1, 1);
            grid.add(new Label("Ap. Paterno:"), 0, 2); grid.add(fApellidoP, 1, 2);
            grid.add(new Label("Ap. Materno:"), 0, 3); grid.add(fApellidoM, 1, 3);
            grid.add(new Label("Teléfono:"), 0, 4); grid.add(fTelefono, 1, 4);
            grid.add(new Label("Correo:"), 0, 5); grid.add(fCorreo, 1, 5);
            grid.add(new Label("Cargo:"), 0, 6); grid.add(fCargo, 1, 6);
            grid.add(new Label("Usuario:"), 0, 7); grid.add(fUsuario, 1, 7);
            grid.add(new Label("Contraseña:"), 0, 8); grid.add(fClave, 1, 8);
            grid.add(lblError, 1, 9);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                if (fDni.getText().length() != 8) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setContentText("El DNI debe tener exactamente 8 dígitos");
                alerta.showAndWait();
                return null;
                }
                if (fTelefono.getText().length() != 9) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setContentText("El teléfono debe tener exactamente 9 dígitos");
                alerta.showAndWait();
                return null;
                }
                if (!fCorreo.getText().matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setContentText("El correo no es válido");
                alerta.showAndWait();
                return null;
                }
                if (fNombre.getText().isEmpty() || fUsuario.getText().isEmpty() || fClave.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setContentText("Completa los campos obligatorios");
                alerta.showAndWait();
                return null;
                }
                    
                    boolean usuarioCreado = UsuarioControlador.registrarEmpleado(
                        fDni.getText(), fNombre.getText(), fApellidoP.getText(),
                        fApellidoM.getText(), fTelefono.getText(), fCorreo.getText(),
                        "", fUsuario.getText(), fClave.getText(), fCargo.getText()
                    );
                    if (!usuarioCreado) {
                        Alert alerta = new Alert(Alert.AlertType.WARNING);
                        alerta.setTitle("Error");
                        alerta.setContentText("El DNI o usuario ya existe en el sistema");
                        alerta.showAndWait();
                        return null;
                    }
                    Empleado emp = new Empleado();
                    emp.setDni(fDni.getText());
                    emp.setNombre(fNombre.getText());
                    emp.setApellido_paterno(fApellidoP.getText());
                    emp.setApellido_materno(fApellidoM.getText());
                    emp.setTelefono(fTelefono.getText());
                    emp.setCorreo(fCorreo.getText());
                    emp.setCargo(fCargo.getText());
                    return emp;
            }       
                return null;
        });
                        dialog.showAndWait().ifPresent(emp -> {
                cargarDatos(tabla, colDni, colNombre, colApellidoP, colApellidoM,
                            colTelefono, colCorreo, colExtra, "Empleados");
                cmbFiltro.setValue("Empleados");
            });
         });
// ===== BOTÓN NUEVO CLIENTE =====
        Button btnNuevoCliente = new Button("+ Nuevo Cliente");
        btnNuevoCliente.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        btnNuevoCliente.setOnAction(e -> {
            abrirNuevoCliente(btnNuevoCliente.getScene().getWindow());
            cargarDatos(tabla, colDni, colNombre, colApellidoP, colApellidoM,
                        colTelefono, colCorreo, colExtra, cmbFiltro.getValue());
        });

        // ===== BOTONES =====
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        Button btnRefrescar = new Button("Refrescar");
        btnRefrescar.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-pref-height: 35px; -fx-background-radius: 6; -fx-cursor: hand;");

        HBox botones = soloClientes
            ? new HBox(10, btnNuevoCliente, btnEliminar, btnRefrescar)
            : new HBox(10, btnNuevoEmpleado, btnNuevoCliente, btnEliminar, btnRefrescar);
        botones.setAlignment(Pos.CENTER_LEFT);

        // ===== CARGAR DATOS =====
        cargarDatos(tabla, colDni, colNombre, colApellidoP, colApellidoM,
                    colTelefono, colCorreo, colExtra, filtroInicial);

        cmbFiltro.setOnAction(e -> {
            txtBuscar.clear();
            cargarDatos(tabla, colDni, colNombre, colApellidoP, colApellidoM,
                        colTelefono, colCorreo, colExtra, cmbFiltro.getValue());
        });

        btnRefrescar.setOnAction(e -> {
            txtBuscar.clear();
            cargarDatos(tabla, colDni, colNombre, colApellidoP, colApellidoM,
                        colTelefono, colCorreo, colExtra, cmbFiltro.getValue());
        });

        btnBuscar.setOnAction(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            String tipo = cmbFiltro.getValue();
            if (tipo.equals("Empleados")) {
                ObservableList<Object> filtrados = FXCollections.observableArrayList();
                for (Empleado emp : EmpleadoControlador.listar()) {
                    if (emp.getNombre().toLowerCase().contains(texto) || emp.getDni().contains(texto)) {
                        filtrados.add(emp);
                    }
                }
                tabla.setItems(filtrados);
            } else {
                ObservableList<Object> filtrados = FXCollections.observableArrayList();
                for (Cliente cli : ClienteControlador.listar()) {
                    if (cli.getNombre().toLowerCase().contains(texto) || cli.getDni().contains(texto)) {
                        filtrados.add(cli);
                    }
                }
                tabla.setItems(filtrados);
            }
        });

        btnEliminar.setOnAction(e -> {
            Object seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selecciona un registro para eliminar");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar eliminación");
            confirm.setContentText("¿Estás segura de eliminar este registro?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (seleccionado instanceof Empleado) {
                        EmpleadoControlador.eliminar(((Empleado) seleccionado).getDni());
                    } else if (seleccionado instanceof Cliente) {
                        ClienteControlador.eliminar(((Cliente) seleccionado).getDni());
                    }
                    cargarDatos(tabla, colDni, colNombre, colApellidoP, colApellidoM,
                                colTelefono, colCorreo, colExtra, cmbFiltro.getValue());
                }
            });
        });

        vista.getChildren().addAll(lblTitulo, barraBusqueda, tabla, botones);
        return vista;
    }

    private void cargarDatos(TableView<Object> tabla,
                              TableColumn<Object, String> colDni,
                              TableColumn<Object, String> colNombre,
                              TableColumn<Object, String> colApellidoP,
                              TableColumn<Object, String> colApellidoM,
                              TableColumn<Object, String> colTelefono,
                              TableColumn<Object, String> colCorreo,
                              TableColumn<Object, String> colExtra,
                              String tipo) {
        if (tipo.equals("Empleados")) {
            colExtra.setText("Cargo");
            colDni.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getDni()));
            colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getNombre()));
            colApellidoP.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getApellido_paterno()));
            colApellidoM.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getApellido_materno()));
            colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getTelefono()));
            colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getCorreo()));
            colExtra.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Empleado) data.getValue()).getCargo()));
            tabla.setItems(FXCollections.observableArrayList(EmpleadoControlador.listar()));
        } else {
            colExtra.setText("Dirección");
            colDni.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getDni()));
            colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getNombre()));
            colApellidoP.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getApellido_paterno()));
            colApellidoM.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getApellido_materno()));
            colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getTelefono()));
            colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getCorreo()));
            colExtra.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(((Cliente) data.getValue()).getDireccion()));
            tabla.setItems(FXCollections.observableArrayList(ClienteControlador.listar()));
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // ===== Método público: permite abrir el formulario "Nuevo Cliente" desde otras pantallas =====
    public void abrirNuevoCliente(javafx.stage.Window owner) {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Cliente");
        dialog.setHeaderText("Ingresa los datos del cliente");
        dialog.initOwner(owner);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        TextField fDni = new TextField(); fDni.setPromptText("DNI (8 dígitos)");
        TextField fNombre = new TextField(); fNombre.setPromptText("Nombre");
        TextField fApellidoP = new TextField(); fApellidoP.setPromptText("Apellido Paterno");
        TextField fApellidoM = new TextField(); fApellidoM.setPromptText("Apellido Materno");
        TextField fTelefono = new TextField(); fTelefono.setPromptText("Teléfono (9 dígitos)");
        TextField fCorreo = new TextField(); fCorreo.setPromptText("Correo");
        TextField fDireccion = new TextField(); fDireccion.setPromptText("Dirección");
        TextField fUsuario = new TextField(); fUsuario.setPromptText("Nombre de usuario");
        PasswordField fClave = new PasswordField(); fClave.setPromptText("Contraseña");

        fDni.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) fDni.setText(newText.replaceAll("[^\\d]", ""));
            if (fDni.getText().length() > 8) fDni.setText(fDni.getText().substring(0, 8));
        });

        fTelefono.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) fTelefono.setText(newText.replaceAll("[^\\d]", ""));
            if (fTelefono.getText().length() > 9) fTelefono.setText(fTelefono.getText().substring(0, 9));
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("DNI:"), 0, 0); grid.add(fDni, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1); grid.add(fNombre, 1, 1);
        grid.add(new Label("Ap. Paterno:"), 0, 2); grid.add(fApellidoP, 1, 2);
        grid.add(new Label("Ap. Materno:"), 0, 3); grid.add(fApellidoM, 1, 3);
        grid.add(new Label("Teléfono:"), 0, 4); grid.add(fTelefono, 1, 4);
        grid.add(new Label("Correo:"), 0, 5); grid.add(fCorreo, 1, 5);
        grid.add(new Label("Dirección:"), 0, 6); grid.add(fDireccion, 1, 6);
        grid.add(new Label("Usuario:"), 0, 7); grid.add(fUsuario, 1, 7);
        grid.add(new Label("Contraseña:"), 0, 8); grid.add(fClave, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                if (fDni.getText().length() != 8) {
                    mostrarAlerta("El DNI debe tener exactamente 8 dígitos");
                    return null;
                }
                if (fTelefono.getText().length() != 9) {
                    mostrarAlerta("El teléfono debe tener exactamente 9 dígitos");
                    return null;
                }
                if (!fCorreo.getText().matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    mostrarAlerta("El correo no es válido");
                    return null;
                }
                if (fNombre.getText().isEmpty() || fUsuario.getText().isEmpty() || fClave.getText().isEmpty()) {
                    mostrarAlerta("Completa los campos obligatorios");
                    return null;
                }

                boolean creado = UsuarioControlador.registrarCliente(
                    fDni.getText(), fNombre.getText(), fApellidoP.getText(),
                    fApellidoM.getText(), fTelefono.getText(), fCorreo.getText(),
                    fDireccion.getText(), fUsuario.getText(), fClave.getText()
                );

                if (!creado) {
                    mostrarAlerta("El DNI o usuario ya existe en el sistema");
                    return null;
                }

                Cliente cli = new Cliente();
                cli.setDni(fDni.getText());
                cli.setNombre(fNombre.getText());
                cli.setApellido_paterno(fApellidoP.getText());
                cli.setApellido_materno(fApellidoM.getText());
                cli.setTelefono(fTelefono.getText());
                cli.setCorreo(fCorreo.getText());
                cli.setDireccion(fDireccion.getText());
                return cli;
            }
            return null;
        });

        dialog.showAndWait();
    }
}

