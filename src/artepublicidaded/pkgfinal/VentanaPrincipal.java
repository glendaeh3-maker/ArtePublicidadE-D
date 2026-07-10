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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Genes
 */

public class VentanaPrincipal {
    private Scene scene;
    private BorderPane root;
    private StackPane contenido; // Aquí se carga cada sección
    private Usuario usuarioActual;
    private Stage stage;

        public VentanaPrincipal(Stage stage, Usuario usuario) {
        this.usuarioActual = usuario;
        this.stage = stage;
        root = new BorderPane();

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(5);
        sidebar.setStyle("-fx-background-color: #2e7d32; -fx-padding: 20;");
        sidebar.setPrefWidth(230);

        Label lblLogo = new Label("ARTE Y PUBLICIDAD");
        lblLogo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblUsuario = new Label(usuario.getNombre());
        lblUsuario.setStyle("-fx-font-size: 12px; -fx-text-fill: #c8e6c9;");

        VBox encabezado = new VBox(2, lblLogo, lblUsuario);
        encabezado.setStyle("-fx-padding: 0 0 25 0;");

        Button btnPanelPrincipal = crearBotonMenu("Panel Principal");
        Button btnPedidos = crearBotonMenu("Pedidos");
        Button btnClientes = crearBotonMenu("Clientes");
        Button btnProductos = crearBotonMenu("Productos");
        Button btnPerfil = crearBotonMenu("Mi Perfil");

        Region espacio = new Region();
        VBox.setVgrow(espacio, Priority.ALWAYS);

        Button btnSalir = crearBotonMenu("Cerrar Sesión");
        btnSalir.setStyle(btnSalir.getStyle() + "-fx-text-fill: #ffcdd2;");

        sidebar.getChildren().addAll(
            encabezado,
            btnPanelPrincipal,
            btnPedidos,
            btnClientes,
            btnProductos,
            btnPerfil,
            espacio,
            btnSalir
        );

        // ===== CONTENIDO CENTRAL =====
        contenido = new StackPane();
        contenido.setStyle("-fx-background-color: #f5f5f5;");
        contenido.setPadding(new Insets(20));

        // Vista inicial: Panel Principal
        mostrarPanelPrincipal();

        // ===== EVENTOS DE NAVEGACIÓN =====
        btnPanelPrincipal.setOnAction(e -> mostrarPanelPrincipal());
        btnPedidos.setOnAction(e -> {
        PedidosPanel panel = new PedidosPanel(usuarioActual);
        contenido.getChildren().setAll(panel.getPanel());
        });
        btnClientes.setOnAction(e -> {
        EmpleadosClientesPanel panel = new EmpleadosClientesPanel();
        VBox panelVista = panel.getPanelSoloClientes();
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
        btnPerfil.setOnAction(e -> {
        PerfilPanel panel = new PerfilPanel(usuarioActual);
        ScrollPane scroll = new ScrollPane(panel.getPanel());
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        contenido.getChildren().setAll(scroll);
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

        scene = new Scene(root, 1000, 620);
        stage.setTitle("Arte y Publicidad E&D - Panel Principal");
        stage.setScene(scene);
        stage.show();
    }

    // ===== Botón de menú con estilo uniforme =====
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
            "-fx-background-color: #388e3c; " +
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

    // ===== Vista: Panel Principal (placeholder, luego le metemos KPIs) =====
private void mostrarPanelPrincipal() {
    VBox vista = new VBox(20);
    vista.setPadding(new Insets(10));

    Label lblTitulo = new Label("Pedidos y Ventas");
    lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

    // ===== Tarjeta de Accesos Rápidos =====
    Label lblAccesos = new Label("Accesos Rápidos");
    lblAccesos.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

    Button btnNuevoPedido = new Button("Nuevo Pedido");
    btnNuevoPedido.setStyle(
        "-fx-background-color: #43a047; -fx-text-fill: white; -fx-font-weight: bold; " +
        "-fx-pref-width: 220px; -fx-pref-height: 38px; -fx-background-radius: 6; -fx-cursor: hand;"
    );

    Button btnRegistrarCliente = new Button("Registrar Cliente");
    btnRegistrarCliente.setStyle(
        "-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold; " +
        "-fx-pref-width: 220px; -fx-pref-height: 38px; -fx-background-radius: 6; -fx-cursor: hand;"
    );

    // ===== BOTÓN "Nuevo Pedido": abre directamente el formulario real de PedidosPanel =====
    btnNuevoPedido.setOnAction(e -> {
        PedidosPanel panel = new PedidosPanel(usuarioActual);
        contenido.getChildren().setAll(panel.getPanel());
        panel.abrirNuevoPedido(stage);
    });

    // ===== BOTÓN "Registrar Cliente": abre directamente el formulario real de EmpleadosClientesPanel =====
    btnRegistrarCliente.setOnAction(e -> {
        EmpleadosClientesPanel panel = new EmpleadosClientesPanel();
        VBox panelVista = panel.getPanelSoloClientes();
        ScrollPane scroll = new ScrollPane(panelVista);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        contenido.getChildren().setAll(scroll);
        panel.abrirNuevoCliente(stage);
    });

    VBox accesos = new VBox(12, lblAccesos, btnNuevoPedido, btnRegistrarCliente);
    accesos.setAlignment(Pos.CENTER_LEFT);
    accesos.setStyle(
        "-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; " +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);"
    );

    // ===== Tarjetas pequeñas con datos reales =====
    int pedidosPendientes = PedidoControlador.contarPorEstado("PENDIENTE");
    double ventasHoy = PedidoControlador.sumaVentasHoy();
    int pedidosListos = PedidoControlador.contarPorEstado("COMPLETADO");

    HBox tarjetasFila = new HBox(15,
        crearTarjeta("Pedidos Pendientes", String.valueOf(pedidosPendientes)),
        crearTarjeta("Ventas Hoy", "S/ " + String.format("%.2f", ventasHoy)),
        crearTarjeta("Pedidos Listos", String.valueOf(pedidosListos))
    );

    // ===== Gráfico: Pedidos por Estado (datos reales de MySQL) =====
    Label lblGrafico = new Label("Pedidos por Estado");
    lblGrafico.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

    CategoryAxis ejeX = new CategoryAxis();
    ejeX.setLabel("Mes");

    NumberAxis ejeY = new NumberAxis();
    ejeY.setLabel("Cantidad de Pedidos");

    BarChart<String, Number> grafico = new BarChart<>(ejeX, ejeY);
    grafico.setPrefHeight(280);
    grafico.setLegendVisible(true);
    grafico.setAnimated(false);

    java.util.Map<String, java.util.Map<String, Integer>> datos = PedidoControlador.conteoPorMesYEstado();

    VBox tarjetaGrafico;

    if (datos.isEmpty()) {
        // Aún no hay pedidos registrados en la base de datos
        Label lblSinDatos = new Label("Todavía no hay pedidos registrados para mostrar en el gráfico.");
        lblSinDatos.setStyle("-fx-font-size: 13px; -fx-text-fill: #999;");
        tarjetaGrafico = new VBox(10, lblGrafico, lblSinDatos);
    } else {
        XYChart.Series<String, Number> seriePendiente = new XYChart.Series<>();
        seriePendiente.setName("Pendiente");

        XYChart.Series<String, Number> serieEnProceso = new XYChart.Series<>();
        serieEnProceso.setName("En Proceso");

        XYChart.Series<String, Number> serieCompletado = new XYChart.Series<>();
        serieCompletado.setName("Completado");

        XYChart.Series<String, Number> serieCancelado = new XYChart.Series<>();
        serieCancelado.setName("Cancelado");

        for (java.util.Map.Entry<String, java.util.Map<String, Integer>> entry : datos.entrySet()) {
            String mes = entry.getKey();
            java.util.Map<String, Integer> porEstado = entry.getValue();

            seriePendiente.getData().add(new XYChart.Data<>(mes, porEstado.getOrDefault("PENDIENTE", 0)));
            serieEnProceso.getData().add(new XYChart.Data<>(mes, porEstado.getOrDefault("EN PROCESO", 0)));
            serieCompletado.getData().add(new XYChart.Data<>(mes, porEstado.getOrDefault("COMPLETADO", 0)));
            serieCancelado.getData().add(new XYChart.Data<>(mes, porEstado.getOrDefault("CANCELADO", 0)));
        }

        grafico.getData().addAll(seriePendiente, serieEnProceso, serieCompletado, serieCancelado);
        tarjetaGrafico = new VBox(10, lblGrafico, grafico);
    }

    tarjetaGrafico.setStyle(
        "-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; " +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);"
    );

    vista.getChildren().addAll(lblTitulo, accesos, tarjetasFila, tarjetaGrafico);

    ScrollPane scroll = new ScrollPane(vista);
    scroll.setFitToWidth(true);
    scroll.setStyle("-fx-background-color: transparent;");

    contenido.getChildren().setAll(scroll);
}

    private VBox crearTarjeta(String titulo, String valor) {
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

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
