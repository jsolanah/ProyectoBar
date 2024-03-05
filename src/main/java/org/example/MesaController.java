package org.example;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MesaController {

    @FXML
    private GridPane gridPane;
    @FXML
    private ListView<Producto> listaProductos;
    @FXML
    private Label totalLabel;

    private Mesa mesa;
    private Stage stage;
    private ImageView imageView;
    @FXML
    private Label mesaLabel;


    public MesaController(Mesa mesa, Stage stage) {
        this.mesa = mesa;
        this.stage = stage;
    }

    public void initialize() {
        String[][] productos = {
                {"Botella de Agua", "1.00", "agua grande.bmp"},
                {"Chocolate", "2.50", "batido chocolate.bmp"},
                {"Nestea", "2.00" , "Nestea.bmp"},
                {"F.Naranja", "2.00", "fanta naranja.bmp"},
                {"F.Limon", "2.00", "fanta limon.bmp"},
                {"Coca-Cola", "2.00", "coca cola.bmp"},
                {"J.Cerveza", "2.50", "jarra cerveza 1l..bmp"},
                {"B.Cerveza", "7.50", "barril cerveza.bmp"},
                {"Jack Daniel's", "6.50", "jack daniels.bmp"},
                {"DYC", "3.50", "dyc.bmp"},
                {"Larios", "3.50", "larios.bmp"}   ,
                {"Negrita", "3.50", "negrita.bmp"},

        };
        mesaLabel.setText("Mesa " + mesa.getNumero());


        int columnaIndex = 0;
        int filaIndex = 0;

        for (String[] producto : productos) {
            Button button = new Button();
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/" + producto[2])));
            imageView.setFitWidth(100);
            imageView.setFitHeight(75);
            button.setGraphic(imageView);

            button.onActionProperty().set(event -> {
                if (!mesa.aumentarCantidadProducto(producto[0], 1)) {
                    mesa.agregarProducto(new Producto(producto[0], Float.parseFloat(producto[1]), 1, mesa.getId()));
                }
            });
            gridPane.add(button, columnaIndex, filaIndex);

            columnaIndex++;
            if (columnaIndex == 4) {
                columnaIndex = 0;
                filaIndex++;
            }
        }
        listaProductos.setItems(mesa.getProductos());
        listaProductos.setCellFactory(listView -> new CeldaProducto(mesa));
        mesa.getProductos().addListener((ListChangeListener<Producto>) c -> totalLabel.setText("Total: " + mesa.getTotalFacturado() + " €"));

        totalLabel.setText("Total " + mesa.getTotalFacturado());
    }



    public void vaciarMesa() {
        mesa.getProductos().clear();

        BaseDeDatos.limpiarMesa(mesa.getId());

        stage.close();
        stage.getOnCloseRequest().handle(null);

    }

    public void mesaPagada() {
        mesa.getProductos().clear();
        BaseDeDatos.setMesaPagada(mesa.getId(), true);
        mesa = new Mesa(mesa.getNumero());
        stage.close();
        stage.getOnCloseRequest().handle(null);
    }
    public void mostrarFactura() {
        try {
            InputStream reportFile = App.class.getResourceAsStream("factura.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id_mesa", mesa.getId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, BaseDeDatos.getConexion());

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}
