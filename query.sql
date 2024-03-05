CREATE DATABASE IF NOT EXISTS comandas;

use comandas;
-- Crear la tabla 'mesas'

CREATE TABLE mesas (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       numero INT NOT NULL,
                       pagada BOOLEAN NOT NULL
);

-- Crear la tabla 'productos'
CREATE TABLE productos (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           id_mesa INT NOT NULL,
                           imagen VARCHAR(255) NOT NULL,
                           precio FLOAT NOT NULL,
                           cantidad INT NOT NULL,
                           FOREIGN KEY (id_mesa) REFERENCES mesas(id)
);
