package org.example;

import javafx.scene.image.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BaseDeDatos {
    public static Connection conexion;

    public static void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mariadb://localhost/comandas", "root", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConexion() {
        return conexion;
    }


    public static int obtenerMesaPorNumero(int numero) throws SQLException {
        PreparedStatement declaracion = conexion.prepareStatement("SELECT id FROM mesas WHERE numero = ? AND pagada = 0");

        declaracion.setInt(1, numero);

        declaracion.execute();

        ResultSet resultado = declaracion.getResultSet();

        if (resultado.next()) {
            return resultado.getInt("id");
        } else {
            return -1;
        }
    }

    public static ArrayList<Producto> obtenerProductosPorIdMesa(int id) throws SQLException {
        PreparedStatement declaracion = conexion.prepareStatement("SELECT * FROM productos WHERE id_mesa = ?");

        declaracion.setInt(1, id);

        declaracion.execute();

        ResultSet resultado = declaracion.getResultSet();

        ArrayList<Producto> productos = new ArrayList<>();

        while (resultado.next()) {
            productos.add(new Producto(resultado.getInt("id"), resultado.getString("nombre"), resultado.getFloat("precio"), resultado.getInt("cantidad")));
        }

        return productos;
    }

    public static int crearMesa(int numero) throws SQLException {
        PreparedStatement declaracion = conexion.prepareStatement("INSERT INTO mesas (numero, pagada) VALUES (?, 0)", Statement.RETURN_GENERATED_KEYS);

        declaracion.setInt(1, numero);

        declaracion.execute();

        ResultSet resultado = declaracion.getGeneratedKeys();

        resultado.next();

        return resultado.getInt(1);
    }

    public static int crearProducto(Producto producto, int idMesa) throws SQLException {
        PreparedStatement declaracion = conexion.prepareStatement("INSERT INTO productos (nombre, id_mesa, precio, cantidad) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        declaracion.setString(1, producto.getNombre());
        declaracion.setInt(2, idMesa);
        declaracion.setFloat(3, producto.getPrecio());
        declaracion.setInt(4, producto.getCantidad());

        declaracion.execute();

        ResultSet resultado = declaracion.getGeneratedKeys();

        resultado.next();

        return resultado.getInt(1);
    }

    public static void setCantidadProducto(int id, int cantidad) {
        try {
            PreparedStatement declaracion = conexion.prepareStatement("UPDATE productos SET cantidad = ? WHERE id = ?");

            declaracion.setInt(1, cantidad);
            declaracion.setInt(2, id);

            declaracion.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void eliminarProducto(int id) {
        try {
            PreparedStatement declaracion = conexion.prepareStatement("DELETE FROM productos WHERE id = ?");

            declaracion.setInt(1, id);

            declaracion.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void limpiarMesa(int id) {
        try {
            PreparedStatement declaracion = conexion.prepareStatement("DELETE FROM productos WHERE id_mesa = ?");

            declaracion.setInt(1, id);

            declaracion.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setMesaPagada(int id, boolean pagada) {
        try {
            PreparedStatement declaracion = conexion.prepareStatement("UPDATE mesas SET pagada = ? WHERE id = ?");

            declaracion.setBoolean(1, pagada);
            declaracion.setInt(2, id);

            declaracion.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
