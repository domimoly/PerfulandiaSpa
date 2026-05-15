CREATE TABLE inventario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    producto BIGINT,
    sucursal BIGINT,
    cantidad INTEGER,
    PRIMARY KEY (id)
);

-- sucursal santiago (barrio meiggs))
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (1, 1, 50);
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (2, 1, 30);
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (3, 1, 100);

-- sucursal concepción
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (1, 2, 15);
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (2, 2, 20);
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (4, 2, 10);

-- sucursal vina del mar
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (3, 3, 80);
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (4, 3, 25);
INSERT INTO inventario (producto, sucursal, cantidad) VALUES (5, 3, 35);