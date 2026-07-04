/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 *
 * @author Genes
 */
public class RegistroForm {
     private Scene scene;

    public RegistroForm(Stage stage) {

        // ===== LADO IZQUIERDO - Formulario =====
        Label lblTitulo = new Label("Crear Cuenta");
        lblTitulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Label lblSubtitulo = new Label("Completa tus datos para registrarte:");
        lblSubtitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        final TextField txtDni = new TextField();
        txtDni.setPromptText("DNI");
        txtDni.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");
        txtDni.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.matches("\\d*")) {
        txtDni.setText(newText.replaceAll("[^\\d]", ""));
        }
        if (txtDni.getText().length() > 8) {
        txtDni.setText(txtDni.getText().substring(0, 8));
         }
       });
        
        final TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final TextField txtApellidoP = new TextField();
        txtApellidoP.setPromptText("Apellido Paterno");
        txtApellidoP.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final TextField txtApellidoM = new TextField();
        txtApellidoM.setPromptText("Apellido Materno");
        txtApellidoM.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");
        txtTelefono.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.matches("\\d*")) {
        txtTelefono.setText(newText.replaceAll("[^\\d]", ""));
        }
        if (txtTelefono.getText().length() > 9) {
        txtTelefono.setText(txtTelefono.getText().substring(0, 9));
        }
        });
        
        final TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo");
        txtCorreo.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        txtDireccion.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final PasswordField txtClave = new PasswordField();
        txtClave.setPromptText("Contraseña");
        txtClave.setStyle("-fx-pref-width: 280px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-background-color: #f0f0f0; -fx-border-color: transparent;");

        final Label lblMensaje = new Label("");
        lblMensaje.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button btnRegistrar = new Button("Registrarse");
        btnRegistrar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                              "-fx-font-size: 14px; -fx-font-weight: bold; " +
                              "-fx-pref-width: 135px; -fx-pref-height: 40px; -fx-background-radius: 8;");

        Button btnVolver = new Button("Iniciar Sesión");
        btnVolver.setStyle("-fx-background-color: transparent; -fx-text-fill: #4CAF50; " +
                           "-fx-font-size: 14px; -fx-font-weight: bold; " +
                           "-fx-pref-width: 135px; -fx-pref-height: 40px; " +
                           "-fx-border-color: #4CAF50; -fx-border-radius: 8; -fx-background-radius: 8;");

        btnVolver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // Volver al Login
                ArtePublicidadEDFinal app = new ArtePublicidadEDFinal();
                try {
                    app.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnRegistrar.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent t) {
        String dni = txtDni.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        if (dni.isEmpty() || txtNombre.getText().isEmpty() ||
            txtClave.getText().isEmpty() || txtUsuario.getText().isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Completa los campos obligatorios");
            return;
        }
        if (dni.length() != 8) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("El DNI debe tener 8 dígitos");
            return;
        }
        if (telefono.length() != 9) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("El teléfono debe tener 9 dígitos");
            return;
        }
        if (!correo.matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Correo inválido");
            return;
        }

        boolean exito = UsuarioControlador.registrarCliente(
            dni, txtNombre.getText(), txtApellidoP.getText(),
            txtApellidoM.getText(), telefono, correo,
            txtDireccion.getText(), txtUsuario.getText(), txtClave.getText()
        );

        if (exito) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText("Registro exitoso. Ya puedes iniciar sesión.");
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("El usuario o DNI ya están registrados");
        }
    }
});

// Y esto separado, para btnVolver (volver al login):
btnVolver.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent t) {
        ArtePublicidadEDFinal app = new ArtePublicidadEDFinal();
        try {
            app.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
});

        HBox botones = new HBox(15, btnRegistrar, btnVolver);
        botones.setAlignment(Pos.CENTER_LEFT);

        VBox panelIzquierdo = new VBox(12);
        panelIzquierdo.setAlignment(Pos.CENTER_LEFT);
        panelIzquierdo.setStyle("-fx-padding: 45; -fx-background-color: white; -fx-min-width: 400px;");
        panelIzquierdo.getChildren().addAll(
            lblTitulo, lblSubtitulo,
            txtDni, txtNombre,
            txtApellidoP, txtApellidoM,
            txtTelefono, txtCorreo,
            txtDireccion, txtUsuario,
            txtClave, lblMensaje,
            botones
        );

        // ===== LADO DERECHO - Fondo verde =====
        VBox panelDerecho = new VBox();
        panelDerecho.setStyle("-fx-background-color: #4CAF50; -fx-min-width: 330px;");

        // ===== LAYOUT PRINCIPAL =====
        HBox root = new HBox();
        root.getChildren().addAll(panelIzquierdo, panelDerecho);

        scene = new Scene(root, 730, 600);
        stage.setTitle("Arte y Publicidad E&D - Registro");
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }
}
