CREATE TABLE proveedor (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(50),
    direccion VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO proveedor (nombre, email, telefono, direccion) 
VALUES ('Perfumeria Italiana', 'contacto@perfumeriaitaliana.cl', '+56975842043', 'Av. Italia 1439, Providencia');

INSERT INTO proveedor (nombre, email, telefono, direccion) 
VALUES ('Perfumarte', 'importaciones@perfumarte.cl', '+56911223344', 'Libertador Gral. Bernardo OHiggins 780, Concepción');

INSERT INTO proveedor (nombre, email, telefono, direccion) 
VALUES ('Aroma di Vita Perfumeria', 'ventas@divita.cl', '+56987654321', 'Av. Borgoño 14580, Viña del Mar');