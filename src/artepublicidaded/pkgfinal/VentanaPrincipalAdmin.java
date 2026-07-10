/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author Genes
 */
public class VentanaPrincipalAdmin {
    private Scene scene;
    private BorderPane root;
    private StackPane contenido;
    private Usuario usuarioActual;

    public VentanaPrincipalAdmin(Stage stage, Usuario usuario) {
        this.usuarioActual = usuario;
        root = new BorderPane();

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(5);
        sidebar.setStyle("-fx-background-color: #1a237e; -fx-padding: 20;");
        sidebar.setPrefWidth(230);

        Label lblLogo = new Label("ARTE Y PUBLICIDAD");
        lblLogo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblRol = new Label("Administrador");
        lblRol.setStyle("-fx-font-size: 12px; -fx-text-fill: #c5cae9;");

        Label lblNombre = new Label(usuario.getNombre() + " " + usuario.getApellidoPaterno());
        lblNombre.setStyle("-fx-font-size: 11px; -fx-text-fill: #c5cae9;");

        VBox encabezado = new VBox(2, lblLogo, lblRol, lblNombre);
        encabezado.setStyle("-fx-padding: 0 0 25 0;");

        Button btnUsuarios = crearBotonMenu("Usuarios y Accesos");
        Button btnEmpleadosClientes = crearBotonMenu("Empleados y Clientes");
        Button btnProductos = crearBotonMenu("Productos y Categorías");
        Button btnAlmacenes = crearBotonMenu("Almacenes y Materiales");
        Button btnProveedores = crearBotonMenu("Proveedores y Compras");
        Button btnFacturacion = crearBotonMenu("Facturación y Pagos");
        Button btnReportes = crearBotonMenu("Reportes");

        Region espacio = new Region();
        VBox.setVgrow(espacio, Priority.ALWAYS);

        Button btnSalir = crearBotonMenu("Cerrar Sesión");
        btnSalir.setStyle(btnSalir.getStyle() + "-fx-text-fill: #ffcdd2;");

        sidebar.getChildren().addAll(
            encabezado,
            btnUsuarios,
            btnEmpleadosClientes,
            btnProductos,
            btnAlmacenes,
            btnProveedores,
            btnFacturacion,
            btnReportes,
            espacio,
            btnSalir
        );

        // ===== CONTENIDO CENTRAL =====
        contenido = new StackPane();
        contenido.setStyle("-fx-background-color: #f5f5f5;");
        contenido.setPadding(new Insets(20));

        mostrarPanelPrincipal(usuario);

        // ===== EVENTOS DE NAVEGACIÓN =====
        btnUsuarios.setOnAction(e -> {
        UsuariosPanel panel = new UsuariosPanel();
        contenido.getChildren().setAll(panel.getPanel());
        });
        btnEmpleadosClientes.setOnAction(e -> {
        EmpleadosClientesPanel panel = new EmpleadosClientesPanel();
        VBox panelVista = panel.getPanel();
        ScrollPane scroll = new ScrollPane(panelVista);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        contenido.getChildren().setAll(scroll);
        });
        btnProductos.setOnAction(e -> {
        ProductosPanel panel = new ProductosPanel();
        contenido.getChildren().setAll(panel.getPanel());
        });
        btnAlmacenes.setOnAction(e -> {
        AlmacenesPanel panel = new AlmacenesPanel();
        contenido.getChildren().setAll(panel.getPanel());
        });
        btnProveedores.setOnAction(e -> {
        ProveedoresPanel panel = new ProveedoresPanel(usuarioActual);
        contenido.getChildren().setAll(panel.getPanel());
        });
        btnFacturacion.setOnAction(e -> {
        FacturacionPanel panel = new FacturacionPanel();
        contenido.getChildren().setAll(panel.getPanel());
        });
        btnReportes.setOnAction(e -> {
        ReportesPanel panel = new ReportesPanel();
        contenido.getChildren().setAll(panel.getPanel());
        });

        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
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

        root.setLeft(sidebar);
        root.setCenter(contenido);

        scene = new Scene(root, 1100, 650);
        stage.setTitle("Arte y Publicidad E&D - Panel Administrador");
        stage.setScene(scene);
        stage.show();
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 13px; " +
            "-fx-padding: 10 10 10 10; " +
            "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #283593; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 13px; " +
            "-fx-padding: 10 10 10 10; " +
            "-fx-background-radius: 6; " +
            "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 13px; " +
            "-fx-padding: 10 10 10 10; " +
            "-fx-cursor: hand;"
        ));
        return btn;
    }

    private void mostrarPanelPrincipal(Usuario usuario) {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblBienvenida = new Label("Bienvenido, " + usuario.getNombre());
        lblBienvenida.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        Label lblSubtitulo = new Label("Panel de Administración — Arte y Publicidad E&D");
        lblSubtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");

        // Tarjetas de resumen
        HBox tarjetas = new HBox(15,
            crearTarjeta("Clientes", "0"),
            crearTarjeta("Empleados", "0"),
            crearTarjeta("Pedidos Hoy", "0"),
            crearTarjeta("Facturación", "S/ 0.00")
        );

        vista.getChildren().addAll(lblBienvenida, lblSubtitulo, tarjetas);
        contenido.getChildren().setAll(vista);
    }

    private void mostrarSeccion(String nombreSeccion) {
        Label lbl = new Label(nombreSeccion);
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");
        contenido.getChildren().setAll(lbl);
    }

    private VBox crearTarjeta(String titulo, String valor) {
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");

        VBox tarjeta = new VBox(5, lblValor, lblTitulo);
        tarjeta.setStyle(
            "-fx-background-color: white; -fx-padding: 18; -fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);"
        );
        tarjeta.setPrefWidth(180);
        return tarjeta;
    }

    public Scene getScene() {
        return scene;
    }
}
