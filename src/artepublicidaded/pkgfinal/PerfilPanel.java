/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 *
 * @author Genes
 */
public class PerfilPanel {
    private Usuario usuario;

    public PerfilPanel(Usuario usuario) {
        this.usuario = usuario;
    }

    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(20));
        vista.setStyle("-fx-background-color: #f5f5f5;");

        // ===== AVATAR + NOMBRE =====
        Circle avatar = new Circle(50);
        avatar.setFill(Color.web("#4CAF50"));

        Label lblIniciales = new Label(getIniciales());
        lblIniciales.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane avatarPane = new StackPane(avatar, lblIniciales);

        Label lblNombreCompleto = new Label(
            (usuario.getNombre() + " " +
             usuario.getApellidoPaterno() + " " +
             usuario.getApellidoMaterno()).toUpperCase()
        );
        lblNombreCompleto.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label lblNombreUsuario = new Label(usuario.getNombreUsuario());
        lblNombreUsuario.setStyle("-fx-font-size: 14px; -fx-text-fill: #777;");

        VBox encabezado = new VBox(8, avatarPane, lblNombreCompleto, lblNombreUsuario);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");

        // ===== INFORMACIÓN BÁSICA =====
        Label lblInfoBasica = new Label("Información básica");
        lblInfoBasica.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        VBox tablaInfo = new VBox(
            crearFilaInfo("Nombre completo", usuario.getNombre() + " " +
                usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno()),
            crearFilaInfo("DNI", usuario.getDni()),
            crearFilaInfo("Correo electrónico", usuario.getCorreo()),
            crearFilaInfo("Teléfono", usuario.getTelefono()),
            crearFilaInfo("Rol", usuario.getRol()),
            crearFilaInfo("Contraseña", "••••••••")
        );
        tablaInfo.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");

        VBox seccionInfo = new VBox(10, lblInfoBasica, tablaInfo);

        // ===== LAYOUT PRINCIPAL =====
        vista.getChildren().addAll(encabezado, seccionInfo);
        return vista;
    }

    private HBox crearFilaInfo(String campo, String valor) {
        Label lblCampo = new Label(campo);
        lblCampo.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 13px;");
        lblCampo.setPrefWidth(200);

        Label lblValor = new Label(valor != null ? valor : "—");
        lblValor.setStyle("-fx-text-fill: #333; -fx-font-size: 13px;");

        HBox fila = new HBox(lblCampo, lblValor);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setStyle("-fx-padding: 12 20 12 20; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0;");

        return fila;
    }

    private String getIniciales() {
        String iniciales = "";
        if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
            iniciales += usuario.getNombre().charAt(0);
        }
        if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) {
            iniciales += usuario.getApellidoPaterno().charAt(0);
        }
        return iniciales.toUpperCase();
    }
}
