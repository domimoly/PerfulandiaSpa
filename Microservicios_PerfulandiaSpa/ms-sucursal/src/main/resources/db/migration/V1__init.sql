create table sucursal (
    id integer not null auto_increment,
    nombre varchar(255),
    direccion varchar(255),
    horarioApertura varchar(255),
    politicas varchar(255),
    primary key (id)
);

INSERT INTO sucursal (nombre, direccion, horario_apertura, politicas)
VALUES ('Barrio Meiggs', 'Barrio Meiggs, Santiago', 'Lun-Sab 9:00-20:00', 'Devolucion con boleta en 30 dias');

-- sucursal 2 → Concepcion
INSERT INTO sucursal (nombre, direccion, horario_apertura, politicas)
VALUES ('Concepcion', 'Av. OHiggins 123, Concepcion', 'Lun-Sab 10:00-19:00', 'Devolucion con boleta en 30 dias');

-- sucursal 3 → Vina del Mar
INSERT INTO sucursal (nombre, direccion, horario_apertura, politicas)
VALUES ('Vina del Mar', 'Av. Libertad 456, Vina del Mar', 'Lun-Dom 10:00-21:00', 'Devolucion con boleta en 30 dias');