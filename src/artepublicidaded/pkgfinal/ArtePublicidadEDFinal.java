/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package artepublicidaded.pkgfinal;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author Genes
 */
public class ArtePublicidadEDFinal extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Logo
        Image imagen = new Image(getClass().getResourceAsStream("/artepublicidaded/pkgfinal/images/logo.png"));
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(300);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(true);

        StackPane panelIzquierdo = new StackPane(imageView);
        panelIzquierdo.setStyle("-fx-background-color: #4CAF50; -fx-min-width: 350px;");
        panelIzquierdo.setAlignment(Pos.CENTER);

        // Formulario
        Label lblTitulo = new Label("Login");
        lblTitulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Label lblUsuario = new Label("Usuario");
        lblUsuario.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingresa tu usuario");
        txtUsuario.setStyle("-fx-pref-width: 250px; -fx-pref-height: 38px; -fx-background-radius: 5;");

        Label lblClave = new Label("Contraseña");
        lblClave.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        PasswordField txtClave = new PasswordField();
        txtClave.setPromptText("Ingresa tu contraseña");
        txtClave.setStyle("-fx-pref-width: 250px; -fx-pref-height: 38px; -fx-background-radius: 5;");

        Label lblMensaje = new Label("");
        lblMensaje.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button btnLogin = new Button("Ingresar");
        btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                          "-fx-font-size: 14px; -fx-font-weight: bold; " +
                          "-fx-pref-width: 250px; -fx-pref-height: 40px; -fx-background-radius: 5;");

        Button btnRegistro = new Button("¿No tienes cuenta? Regístrate");
        btnRegistro.setStyle("-fx-background-color: transparent; -fx-text-fill: #4CAF50; " +
                             "-fx-font-size: 12px; -fx-cursor: hand;");

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String usuario = txtUsuario.getText();
                String clave = txtClave.getText();

                if (usuario.isEmpty() || clave.isEmpty()) {
                    lblMensaje.setText("Ingresa usuario y contraseña");
                    return;
                }

                Usuario usuarioLogueado = UsuarioControlador.validarLogin(usuario, clave);
                if (usuarioLogueado != null) {
                switch (usuarioLogueado.getRol()) {
                case "ADMIN":
                new VentanaPrincipalAdmin(stage, usuarioLogueado);
                break;
                case "EMPLEADO":
                new VentanaPrincipal(stage, usuarioLogueado.getNombre());
                break;
                case "CLIENTE":
                // Por ahora va al mismo dashboard, luego creamos el del cliente
                new VentanaPrincipal(stage, usuarioLogueado.getNombre());
                break;
                default:
                lblMensaje.setStyle("-fx-text-fill: red;");
                lblMensaje.setText("Rol no reconocido");
                break;
    }
} else {
    lblMensaje.setStyle("-fx-text-fill: red;");
    lblMensaje.setText("Usuario o contraseña incorrectos");
}
            }
        });

        btnRegistro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                new RegistroForm(stage);
            }
        });

        VBox panelDerecho = new VBox(15);
        panelDerecho.setAlignment(Pos.CENTER_LEFT);
        panelDerecho.setStyle("-fx-padding: 50; -fx-background-color: white; -fx-min-width: 380px;");
        panelDerecho.getChildren().addAll(
            lblTitulo,
            lblUsuario, txtUsuario,
            lblClave, txtClave,
            lblMensaje,
            btnLogin,
            btnRegistro
        );

        HBox root = new HBox();
        root.getChildren().addAll(panelIzquierdo, panelDerecho);

        Scene scene = new Scene(root, 730, 450);
        stage.setTitle("Arte y Publicidad E&D - Login");
        stage.setScene(scene);
        stage.show();
    }
}