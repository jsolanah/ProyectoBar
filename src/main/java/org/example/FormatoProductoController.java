package org.example;

import javafx.scene.control.Label;

public class FormatoProductoController {
    public Label nombre;
    private Producto producto;
    private Mesa mesa;
    public Label descripcion;



    public void setData(Producto p, Mesa mesa) {
        nombre.setText("Producto: " + p.getNombre());
        descripcion.setText("Cantidad: " + p.getCantidad() + ", P.Unitario " + p.getPrecio() + " €, Total = " + p.getTotal() + " €");

        this.producto = p;
        this.mesa = mesa;
    }
}