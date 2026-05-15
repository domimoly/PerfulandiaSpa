CREATE TABLE sucursal (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    direccion VARCHAR(255),
    horario_apertura VARCHAR(255),
    politicas VARCHAR(255),
    PRIMARY KEY (id)
);

-- Sucursal: Santiago
INSERT INTO sucursal (nombre, direccion, horario_apertura, politicas)
VALUES ('Barrio Meiggs', 'Barrio Meiggs, Santiago', 'Lun-Sab 9:00-20:00', 'Devolucion con boleta en 30 dias');

-- Sucursal: Concepción
INSERT INTO sucursal (nombre, direccion, horario_apertura, politicas)
VALUES ('Concepcion', 'Av. OHiggins 123, Concepcion', 'Lun-Sab 10:00-19:00', 'Devolucion con boleta en 30 dias');

-- Sucursal: Viña del mar
INSERT INTO sucursal (nombre, direccion, horario_apertura, politicas)
VALUES ('Vina del Mar', 'Av. Libertad 456, Vina del Mar', 'Lun-Dom 10:00-21:00', 'Devolucion con boleta en 30 dias');