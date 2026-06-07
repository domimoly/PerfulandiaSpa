CREATE TABLE categoria (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    descripcion VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO categoria (nombre, descripcion)
VALUES ('Perfume Hombre', 'Perfumes para hombres');

INSERT INTO categoria (nombre, descripcion)
VALUES ('Perfume Mujer', 'Perfumes para mujeres');
