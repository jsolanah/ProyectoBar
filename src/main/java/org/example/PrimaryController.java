package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class PrimaryController {
    @FXML
    private Button mesa1;
    @FXML
    private Button mesa2;
    @FXML
    private Button mesa3;
    @FXML
    private Button mesa4;
    @FXML
    private Button mesa5;
    @FXML
    private Button mesa6;
    @FXML
    private Button mesa7;
    @FXML
    private Button mesa8;
    @FXML
    private Button mesa9;

    public List<Mesa> mesas;
    Image imagen = new Image("mesa.png");

    @FXML
    private void initialize() {
        this.mesas = new ArrayList<>();
        List<Button> botonesMesas = List.of(mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9);

        for (int i = 0; i < botonesMesas.size(); i++) {
            Mesa mesa = new Mesa(i + 1);
            agregarBotonMesa(botonesMesas.get(i), mesa);
        }


    }

    private void agregarBotonMesa(Button botonMesa, Mesa mesa) {
        botonMesa.setGraphic(new ImageView(imagen));
        botonMesa.setId("mesa-" + mesa.getNumero());
        actualizarBotonMesa(mesa, botonMesa);

        botonMesa.onActionProperty().set(event -> {
            try {
                abrirMesa(mesa, botonMesa);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        mesas.add(mesa);
    }

    private void abrirMesa(Mesa mesa, Button botonMesa) throws IOException {
        if (!mesa.estaOcupada()) {
            mesa.setOcupada(true);
            botonMesa.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        }

        Stage stage = new Stage();
        stage.setTitle("Mesa " + mesa.getNumero());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondary.fxml"));

        fxmlLoader.setController(new MesaController(mesa, stage));

        stage.setScene(new Scene(fxmlLoader.load(), 500, 620));
        stage.show();

        stage.setOnCloseRequest(event -> {
            if (mesa.getProductos().isEmpty()) {
                mesa.setOcupada(false);
                //botonMesa.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            }
            actualizarBotonMesa(mesa, botonMesa);
        });
    }
    private void actualizarBotonMesa(Mesa mesa, Button botonMesa) {
        if (mesa.estaOcupada()) {
            botonMesa.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        } else {
            botonMesa.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        }
    }
    @FXML
    public void mostrarHistorico() {
        try {
            InputStream reportFile = App.class.getResourceAsStream("historico.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, BaseDeDatos.getConexion());

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
