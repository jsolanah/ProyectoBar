package org.example;

import javafx.scene.image.Image;

import java.sql.SQLException;

public class Producto {
    private int id;
    private final String nombre;
    private final float precio;
    private int cantidad;

    public Producto(int id, String nombre, float precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(String nombre, float precio, int cantidad, int idMesa) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;

        try {
            this.id = BaseDeDatos.crearProducto(this, idMesa);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNombre() {
        return nombre;
    }


    public float getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;

        if (this.cantidad == 0) {
            BaseDeDatos.eliminarProducto(this.id);
        } else {
            BaseDeDatos.setCantidadProducto(this.id, this.cantidad);
        }
    }

    public float getTotal() {
        return precio * cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
