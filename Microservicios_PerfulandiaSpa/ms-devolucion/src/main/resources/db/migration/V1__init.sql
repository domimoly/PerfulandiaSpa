CREATE TABLE devolucion (
    id INTEGER NOT NULL AUTO_INCREMENT,
    fecha_devolucion VARCHAR(255),
    motivo VARCHAR(255),
    estado VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO devolucion (fecha_devolucion, motivo, estado) 
VALUES ('2026-05-09', 'Producto defectuoso', 'Pendiente');

INSERT INTO devolucion (fecha_devolucion, motivo, estado) 
VALUES ('2026-05-10', 'Producto no deseado', 'Aprobada');