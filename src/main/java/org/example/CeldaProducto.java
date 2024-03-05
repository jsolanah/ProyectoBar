package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class CeldaProducto extends ListCell<Producto> {
    private Mesa mesa;
    public CeldaProducto(Mesa mesa) {
        this.mesa = mesa;
    }
    @Override
    public void updateItem(Producto p, boolean empty) {
        super.updateItem(p, empty);

        if (p != null && !empty) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/formatoproducto.fxml"));
                Parent root = fxmlLoader.load();
                FormatoProductoController controller = fxmlLoader.getController();
                controller.setData(p, mesa);
                setText(null);
                setGraphic(root);
                setBackground(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            setText(null);
            setGraphic(null);
            setBackground(null);
        }
    }
}
