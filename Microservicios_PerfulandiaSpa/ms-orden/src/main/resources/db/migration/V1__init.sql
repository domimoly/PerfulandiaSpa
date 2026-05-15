CREATE TABLE orden (
    id INTEGER NOT NULL AUTO_INCREMENT,
    numero_orden INTEGER,
    fecha_creacion VARCHAR(255),
    fecha_recibida VARCHAR(255),
    total INTEGER,
    descuento_aplicado INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO orden (numero_orden, fecha_creacion, fecha_recibida, total, descuento_aplicado)
VALUES (1, '2026-03-20', '2026-03-23', 115000, 0);

INSERT INTO orden (numero_orden, fecha_creacion, fecha_recibida, total, descuento_aplicado)
VALUES (2, '2026-03-24', '2026-03-26', 175000, 13000);