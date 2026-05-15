CREATE TABLE cupon_descuento (
    id INTEGER NOT NULL AUTO_INCREMENT,
    producto INTEGER,
    codigo VARCHAR(50),
    porcentaje_descuento DOUBLE,
    fecha_vencimiento DATE,
    activo BOOLEAN,
    PRIMARY KEY (id)
);

-- Producto 1 | Carolina Herrera
INSERT INTO cupon_descuento (producto, codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES (1, 'VERANO20', 20.0, '2026-12-31', true);

-- Producto 2 | Dior Sauvage
INSERT INTO cupon_descuento (producto, codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES (2, 'BIENVENIDA10', 10.0, '2026-12-31', true);

-- Producto 3 | Calvin Klein CK One
INSERT INTO cupon_descuento (producto, codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES (3, 'INVIERNO15', 15.0, '2026-06-01', true);