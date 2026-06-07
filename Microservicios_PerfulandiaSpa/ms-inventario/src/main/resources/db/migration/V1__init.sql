CREATE TABLE inventario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    producto BIGINT,
    sucursal BIGINT,
    cantidad INTEGER,
    stock_minimo INTEGER,
    fecha_inventario DATE,
    proveedor BIGINT,
    PRIMARY KEY (id)
);

-- sucursal santiago (barrio meiggs)
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (1, 1, 50, 10, '2025-01-15', 1);
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (2, 1, 30, 5, '2025-01-15', 1);
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (3, 1, 100, 20, '2025-01-15', 1);

-- sucursal concepción
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (1, 2, 15, 5, '2025-02-10', 2);
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (2, 2, 20, 5, '2025-02-10', 2);
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (4, 2, 10, 3, '2025-02-10', 2);

-- sucursal viña del mar
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (3, 3, 80, 15, '2025-03-05', 3);
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (4, 3, 25, 5, '2025-03-05', 3);
INSERT INTO inventario (producto, sucursal, cantidad, stock_minimo, fecha_inventario, proveedor) VALUES (5, 3, 35, 8, '2025-03-05', 3);