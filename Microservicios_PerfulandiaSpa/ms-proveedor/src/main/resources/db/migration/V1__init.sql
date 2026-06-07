CREATE TABLE proveedor (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(50),
    direccion VARCHAR(255),
    sucursal BIGINT,
    producto BIGINT,
    PRIMARY KEY (id)
);

INSERT INTO proveedor (nombre, email, telefono, direccion, sucursal, producto) 
VALUES ('Perfumeria Italiana', 'contacto@perfumeriaitaliana.cl', '+56975842043', 'Av. Italia 1439, Providencia', 1, 1);

INSERT INTO proveedor (nombre, email, telefono, direccion, sucursal, producto) 
VALUES ('Perfumarte', 'importaciones@perfumarte.cl', '+56911223344', 'Libertador Gral. Bernardo OHiggins 780, Concepción', 2, 2);

INSERT INTO proveedor (nombre, email, telefono, direccion, sucursal, producto) 
VALUES ('Aroma di Vita Perfumeria', 'ventas@divita.cl', '+56987654321', 'Av. Borgoño 14580, Viña del Mar', 3, 3);