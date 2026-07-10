/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artepublicidaded.pkgfinal;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
/**
 *
 * @author Genes
 */
public class ReportesPanel {
    public VBox getPanel() {
        VBox vista = new VBox(20);
        vista.setPadding(new Insets(10));

        Label lblTitulo = new Label("Reportes");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

        // ===== TABS PARA CADA REPORTE =====
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().addAll(
            crearTabPedidosPorEstado(),
            crearTabVentasPorMes(),
            crearTabClientesFrecuentes(),
            crearTabProductosMasVendidos(),
            crearTabPagos()
        );

        vista.getChildren().addAll(lblTitulo, tabPane);
        return vista;
    }

    // ===== TAB 1: Pedidos por Estado =====
    private Tab crearTabPedidosPorEstado() {
        Tab tab = new Tab("Pedidos por Estado");

        Map<String, Integer> conteo = new HashMap<>();
        conteo.put("PENDIENTE", 0);
        conteo.put("EN PROCESO", 0);
        conteo.put("COMPLETADO", 0);
        conteo.put("CANCELADO", 0);

        for (Pedido p : PedidoControlador.listar()) {
            String estado = p.getEstado();
            conteo.put(estado, conteo.getOrDefault(estado, 0) + 1);
        }

        CategoryAxis ejeX = new CategoryAxis();
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Cantidad");

        BarChart<String, Number> grafico = new BarChart<>(ejeX, ejeY);
        grafico.setTitle("Pedidos por Estado");
        grafico.setLegendVisible(false);
        grafico.setAnimated(false);
        grafico.setPrefHeight(350);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : conteo.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        grafico.getData().add(serie);

        tab.setContent(new VBox(grafico));
        return tab;
    }

    // ===== TAB 2: Ventas por Mes =====
    private Tab crearTabVentasPorMes() {
        Tab tab = new Tab("Ventas por Mes");

        Map<String, Double> ventasPorMes = new LinkedHashMap<>();
        String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
                          "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        for (String mes : meses) ventasPorMes.put(mes, 0.0);

        for (Pago p : PagoControlador.listar()) {
            if (p.getEstado().equals("COMPLETADO") && p.getFechaPago() != null) {
                int mesNum = p.getFechaPago().getMonthValue() - 1;
                String mes = meses[mesNum];
                ventasPorMes.put(mes, ventasPorMes.get(mes) + p.getMonto());
            }
        }

        CategoryAxis ejeX = new CategoryAxis();
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("S/");

        BarChart<String, Number> grafico = new BarChart<>(ejeX, ejeY);
        grafico.setTitle("Ventas por Mes");
        grafico.setLegendVisible(false);
        grafico.setAnimated(false);
        grafico.setPrefHeight(350);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (Map.Entry<String, Double> entry : ventasPorMes.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        grafico.getData().add(serie);

        tab.setContent(new VBox(grafico));
        return tab;
    }

    // ===== TAB 3: Clientes más frecuentes =====
    private Tab crearTabClientesFrecuentes() {
        Tab tab = new Tab("Clientes Frecuentes");

        Map<Integer, Integer> pedidosPorCliente = new HashMap<>();
        for (Pedido p : PedidoControlador.listar()) {
            pedidosPorCliente.put(p.getClienteId(),
                pedidosPorCliente.getOrDefault(p.getClienteId(), 0) + 1);
        }

        TableView<Map.Entry<String, Integer>> tabla = new TableView<>();
        tabla.setPrefHeight(350);

        TableColumn<Map.Entry<String, Integer>, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setPrefWidth(300);
        colCliente.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));

        TableColumn<Map.Entry<String, Integer>, String> colPedidos = new TableColumn<>("Total Pedidos");
        colPedidos.setPrefWidth(150);
        colPedidos.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getValue())));

        tabla.getColumns().addAll(colCliente, colPedidos);

        List<Map.Entry<String, Integer>> lista = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : pedidosPorCliente.entrySet()) {
            Cliente c = ClienteControlador.buscarPorDni(String.valueOf(entry.getKey()));
            String nombre = c != null ? c.getNombre() + " " + c.getApellido_paterno() : "ID: " + entry.getKey();
            lista.add(new AbstractMap.SimpleEntry<>(nombre, entry.getValue()));
        }
        lista.sort((a, b) -> b.getValue() - a.getValue());
        tabla.setItems(FXCollections.observableArrayList(lista));

        tab.setContent(new VBox(tabla));
        return tab;
    }

    // ===== TAB 4: Productos más vendidos =====
    private Tab crearTabProductosMasVendidos() {
        Tab tab = new Tab("Productos más Vendidos");

        TableView<String[]> tabla = new TableView<>();
        tabla.setPrefHeight(350);

        TableColumn<String[], String> colProducto = new TableColumn<>("Producto");
        colProducto.setPrefWidth(250);
        colProducto.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setPrefWidth(150);
        colCategoria.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setPrefWidth(100);
        colPrecio.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        tabla.getColumns().addAll(colProducto, colCategoria, colPrecio);

        List<String[]> lista = new ArrayList<>();
        for (Producto p : ProductoControlador.listar()) {
            lista.add(new String[]{
                p.getNombreProducto(),
                p.getCategoria(),
                "S/ " + String.format("%.2f", p.getPrecioUnitario())
            });
        }
        tabla.setItems(FXCollections.observableArrayList(lista));

        tab.setContent(new VBox(tabla));
        return tab;
    }

    // ===== TAB 5: Pagos pendientes vs completados =====
    private Tab crearTabPagos() {
        Tab tab = new Tab("Estado de Pagos");

        int pendientes = 0, completados = 0;
        double totalPendiente = 0, totalCompletado = 0;

        for (Pago p : PagoControlador.listar()) {
            if (p.getEstado().equals("PENDIENTE")) {
                pendientes++;
                totalPendiente += p.getMonto();
            } else if (p.getEstado().equals("COMPLETADO")) {
                completados++;
                totalCompletado += p.getMonto();
            }
        }

        // Gráfico de pastel
        PieChart grafico = new PieChart();
        grafico.setTitle("Pagos por Estado");
        grafico.setAnimated(false);
        grafico.setPrefHeight(250);
        grafico.getData().addAll(
            new PieChart.Data("Pendientes (" + pendientes + ")", pendientes),
            new PieChart.Data("Completados (" + completados + ")", completados)
        );

        // Tarjetas de resumen
        HBox tarjetas = new HBox(20,
            crearTarjetaReporte("Pagos Pendientes", pendientes + " pagos",
                "S/ " + String.format("%.2f", totalPendiente), "#c62828"),
            crearTarjetaReporte("Pagos Completados", completados + " pagos",
                "S/ " + String.format("%.2f", totalCompletado), "#388e3c")
        );
        tarjetas.setAlignment(Pos.CENTER);

        VBox contenido = new VBox(20, grafico, tarjetas);
        contenido.setPadding(new Insets(10));
        tab.setContent(contenido);
        return tab;
    }

    private VBox crearTarjetaReporte(String titulo, String subtitulo, String valor, String color) {
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        Label lblSubtitulo = new Label(subtitulo);
        lblSubtitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        VBox tarjeta = new VBox(5, lblTitulo, lblSubtitulo, lblValor);
        tarjeta.setStyle("-fx-background-color: white; -fx-padding: 18; -fx-background-radius: 10; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        tarjeta.setPrefWidth(200);
        return tarjeta;
    }
}
