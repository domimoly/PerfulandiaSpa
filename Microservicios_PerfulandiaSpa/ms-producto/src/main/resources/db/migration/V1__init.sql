CREATE TABLE producto (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    descripcion VARCHAR(255),
    precio DOUBLE,
    cantidad INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO producto (nombre, descripcion, precio, cantidad) 
VALUES ('Carolina Herrera Good Girl', 'Perfume floral oriental para mujer, 80ml', 115000.0, 50);

INSERT INTO producto (nombre, descripcion, precio, cantidad) 
VALUES ('Dior Sauvage', 'Fragancia aromática fougère para hombre, 100ml', 130000.0, 40);

INSERT INTO producto (nombre, descripcion, precio, cantidad) 
VALUES ('Calvin Klein CK One', 'Aroma cítrico y fresco unisex, 200ml', 45000.0, 30);

INSERT INTO producto (nombre, descripcion, precio, cantidad) 
VALUES ('Hugo Boss Bottled', 'Aroma amaderado especiado clásico, 100ml', 75000.0, 20);

INSERT INTO producto (nombre, descripcion, precio, cantidad) 
VALUES ('Lancome La Vie Est Belle', 'Fragancia dulce y floral para mujer, 50ml', 95000.0, 10);