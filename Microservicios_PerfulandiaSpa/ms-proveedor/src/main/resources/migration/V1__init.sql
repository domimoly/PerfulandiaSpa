create table proveedor (
    id integer not null auto_increment,
    nombre varchar(255),
    email varchar(255),
    telefono varchar(50),
    direccion varchar(255),
    primary key (id)
);

INSERT INTO proveedores (nombre, email, telefono, direccion) 
VALUES ('Perfumeria Italiana', 'contacto@perfumeriaitaliana.cl', '+56975842043', 'Av. Italia 1439, Providencia');

INSERT INTO proveedores (nombre, email, telefono, direccion) 
VALUES ('Perfumarte', 'importaciones@perfumarte.cl', '+56911223344', 'Libertador Gral. Bernardo OHiggins 780, Concepción');

INSERT INTO proveedores (nombre, email, telefono, direccion) 
VALUES ('Aroma di Vita Perfumeria', 'ventas@divita.cl', '+56987654321', 'Av. Borgoño 14580, Viña del Mar');