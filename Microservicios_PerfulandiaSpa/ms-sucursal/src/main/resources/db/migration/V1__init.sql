create table sucursal (
    id integer not null auto_increment,
    nombre varchar(255),
    direccion varchar(255),
    horarioApertura varchar(255),
    politicas varchar(255),
    primary key (id)
);

INSERT INTO sucursal (nombre, direccion, horarioApertura, politicas) VALUES ('Sucursal Central', 'Calle Principal 123', '09:00-18:00', 'Política de devolución: 30 días');
INSERT INTO sucursal (nombre, direccion, horarioApertura, politicas) VALUES ('Sucursal Norte', 'Avenida Norte 456', '10:00-19:00', 'Política de devolución: 30 días');