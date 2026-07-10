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
public class VentanaPrincipalCliente {

    private Scene scene;
    private BorderPane root;
    private StackPane contenido;

    public VentanaPrincipalCliente(Stage stage, Usuario usuario) {

        root = new BorderPane();

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(5);
        sidebar.setStyle("-fx-background-color: #4CAF50; -fx-padding: 20;");
        sidebar.setPrefWidth(230);

        Label lblLogo = new Label("ARTE Y PUBLICIDAD");
        lblLogo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblRol = new Label("Cliente");
        lblRol.setStyle("-fx-font-size: 12px; -fx-text-fill: #c8e6c9;");

        Label lblNombre = new Label(usuario.getNombre() + " " + usuario.getApellidoPaterno());
        lblNombre.setStyle("-fx-font-size: 11px; -fx-text-fill: #c8e6c9;");

        VBox encabezado = new VBox(2, lblLogo, lblRol, lblNombre);
        encabezado.setStyle("-fx-padding: 0 0 25 0;");

        Button btnMisPedidos = crearBotonMenu("📦 Mis Pedidos");
        Button btnNuevoPedido = crearBotonMenu("➕ Nuevo Pedido");
        Button btnMiPerfil = crearBotonMenu("👤 Mi Perfil");

        Region espacio = new Region();
        VBox.setVgrow(espacio, Priority.ALWAYS);

        Button btnSalir = crearBotonMenu("🚪 Cerrar Sesión");
        btnSalir.setStyle(btnSalir.getStyle() + "-fx-text-fill: #ffcdd2;");

        sidebar.getChildren().addAll(
            encabezado,
            btnMisPedidos,
            btnNuevoPedido,
            btnMiPerfil,
            espacio,
            btnSalir
        );

        // ===== CONTENIDO CENTRAL =====
        contenido = new StackPane();
        contenido.setStyle("-fx-background-color: #f5f5f5;");
        contenido.setPadding(new Insets(20));

        mostrarPanelPrincipal(usuario);

        // ===== EVENTOS =====
        btnMisPedidos.setOnAction(e -> mostrarMisPedidos(usuario));
        btnNuevoPedido.setOnAction(e -> mostrarSeccion("Nuevo Pedido — próximamente"));
        btnMiPerfil.setOnAction(e -> mostrarMiPerfil(usuario));

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

        scene = new Scene(root, 900, 600);
        stage.setTitle("Arte y Publicidad E&D - Mi Panel");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarPanelPrincipal(Usuario usuario) {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblBienvenida = new Label("Bienvenido, " + usuario.getNombre() + "!");
        lblBienvenida.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Label lblSubtitulo = new Label("Desde aquí puedes ver tus pedidos y hacer nuevas solicitudes.");
        lblSubtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");

        // Tarjetas
        int totalPedidos = 0;
        int pedidosPendientes = 0;
        int pedidosCompletados = 0;

        Cliente clienteActual = ClienteControlador.buscarPorDni(usuario.getDni());
        if (clienteActual != null) {
            for (Pedido p : PedidoControlador.listar()) {
                if (p.getClienteId() == clienteActual.getId()) {
                    totalPedidos++;
                    if (p.getEstado().equals("PENDIENTE")) pedidosPendientes++;
                    if (p.getEstado().equals("COMPLETADO")) pedidosCompletados++;
                }
            }
        }

        HBox tarjetas = new HBox(15,
            crearTarjeta("Total Pedidos", String.valueOf(totalPedidos)),
            crearTarjeta("Pendientes", String.valueOf(pedidosPendientes)),
            crearTarjeta("Completados", String.valueOf(pedidosCompletados))
        );

        vista.getChildren().addAll(lblBienvenida, lblSubtitulo, tarjetas);
        contenido.getChildren().setAll(vista);
    }

    private void mostrarMisPedidos(Usuario usuario) {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Mis Pedidos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        TableView<Pedido> tabla = new TableView<>();
        tabla.setPrefHeight(400);

        TableColumn<Pedido, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            String.valueOf(data.getValue().getId())));
        colId.setPrefWidth(60);

        TableColumn<Pedido, String> colFecha = new TableColumn<>("Fecha Pedido");
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getFechaPedido()));
        colFecha.setPrefWidth(120);

        TableColumn<Pedido, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getEstado()));
        colEstado.setPrefWidth(120);

        TableColumn<Pedido, String> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            "S/ " + String.format("%.2f", data.getValue().getTotal())));
        colTotal.setPrefWidth(100);

        tabla.getColumns().addAll(colId, colFecha, colEstado, colTotal);

        // Filtrar solo pedidos de este cliente
        Cliente clienteActual = ClienteControlador.buscarPorDni(usuario.getDni());
        if (clienteActual != null) {
            javafx.collections.ObservableList<Pedido> pedidos =
                javafx.collections.FXCollections.observableArrayList();
            for (Pedido p : PedidoControlador.listar()) {
                if (p.getClienteId() == clienteActual.getId()) {
                    pedidos.add(p);
                }
            }
            tabla.setItems(pedidos);
        }

        vista.getChildren().addAll(lblTitulo, tabla);
        contenido.getChildren().setAll(vista);
    }

    private void mostrarMiPerfil(Usuario usuario) {
        VBox vista = new VBox(15);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Mi Perfil");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Label lblNombre = new Label("Nombre: " + usuario.getNombre() + " " + usuario.getApellidoPaterno());
        Label lblUsuario = new Label("Usuario: " + usuario.getNombreUsuario());
        Label lblCorreo = new Label("Correo: " + usuario.getCorreo());
        Label lblDni = new Label("DNI: " + usuario.getDni());

        for (Label lbl : new Label[]{lblNombre, lblUsuario, lblCorreo, lblDni}) {
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");
        }

        VBox tarjeta = new VBox(10, lblNombre, lblUsuario, lblCorreo, lblDni);
        tarjeta.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        tarjeta.setMaxWidth(400);

        vista.getChildren().addAll(lblTitulo, tarjeta);
        contenido.getChildren().setAll(vista);
    }

    private void mostrarSeccion(String mensaje) {
        Label lbl = new Label(mensaje);
        lbl.setStyle("-fx-font-size: 18px; -fx-text-fill: #4CAF50;");
        contenido.getChildren().setAll(lbl);
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
                     "-fx-font-size: 13px; -fx-padding: 10; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #388e3c; -fx-text-fill: white; " +
            "-fx-font-size: 13px; -fx-padding: 10; -fx-background-radius: 6; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: white; " +
            "-fx-font-size: 13px; -fx-padding: 10; -fx-cursor: hand;"));
        return btn;
    }

    private VBox crearTarjeta(String titulo, String valor) {
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");
        VBox tarjeta = new VBox(5, lblValor, lblTitulo);
        tarjeta.setStyle("-fx-background-color: white; -fx-padding: 18; -fx-background-radius: 10; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        tarjeta.setPrefWidth(160);
        return tarjeta;
    }

    public Scene getScene() {
        return scene;
    }
}
