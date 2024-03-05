package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mesa {
    private int id;
    private final int numero;
    private final ObservableList<Producto> productos;
    private boolean ocupada;

    public Mesa(int numero) {
        this.numero = numero;
        this.productos = FXCollections.observableArrayList();
        this.ocupada = false;

        try {
            int id = BaseDeDatos.obtenerMesaPorNumero(numero);

            if (id != -1) {
                this.id = id;
                ArrayList<Producto> productos = BaseDeDatos.obtenerProductosPorIdMesa(id);
                this.productos.addAll(productos);
                if (!this.productos.isEmpty()) {
                    this.ocupada = true;
                }
            } else {
                this.id = BaseDeDatos.crearMesa(numero);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getNumero() {
        return numero;
    }
    public float getTotalFacturado() {
        float total = 0;
        for (Producto producto : productos) {
            total += producto.getTotal();
        }
        return total;
    }
    public boolean estaOcupada() {
        return ocupada;
    }
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
    public int getCantidadProductos() {
        return productos.size();
    }
    public boolean aumentarCantidadProducto(String nombreProducto, int cantidad) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(nombreProducto)) {
                producto.setCantidad(producto.getCantidad() + cantidad);
                productos.set(productos.indexOf(producto), producto);
                return true;
            }
        }
        return false;
    }
    public ObservableList<Producto> getProductos() {
        return productos;
    }
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero=" + numero +
                ", productos=" + productos +
                ", ocupada=" + ocupada +
                '}';
    }
}

