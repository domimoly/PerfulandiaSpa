create table producto (
    id integer not null auto_increment,
    nombre varchar(255),
    descripcion varchar(255),
    precio double,
    primary key (id)
);

INSERT INTO productos (nombre, descripcion, precio) 
VALUES ('Carolina Herrera Good Girl', 'Perfume floral oriental para mujer, 80ml', 115000.0);

INSERT INTO productos (nombre, descripcion, precio) 
VALUES ('Dior Sauvage', 'Fragancia aromática fougère para hombre, 100ml', 130000.0);

INSERT INTO productos (nombre, descripcion, precio) 
VALUES ('Calvin Klein CK One', 'Aroma cítrico y fresco unisex, 200ml', 45000.0);

INSERT INTO productos (nombre, descripcion, precio) 
VALUES ('Hugo Boss Bottled', 'Aroma amaderado especiado clásico, 100ml', 75000.0);

INSERT INTO productos (nombre, descripcion, precio) 
VALUES ('Lancome La Vie Est Belle', 'Fragancia dulce y floral para mujer, 50ml', 95000.0);