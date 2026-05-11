create table inventario (
    id integer not null auto_increment,
    producto_id integer,
    sucursal_id integer,
    cantidad integer,
    primary key (id)
);

-- sucursal santiago (barrio meiggs))
INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (1, 1, 50);

INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (2, 1, 30);

INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (3, 1, 100);

-- sucursal concepción
INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (1, 2, 15);

INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (2, 2, 20);

INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (6, 2, 10);

-- sucursal vina del mar
INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (3, 3, 80);

INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (4, 3, 25);

INSERT INTO inventarios (producto_id, sucursal_id, cantidad)
VALUES (5, 3, 35);
