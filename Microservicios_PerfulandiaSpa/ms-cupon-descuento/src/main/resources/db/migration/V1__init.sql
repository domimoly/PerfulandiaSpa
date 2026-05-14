create table cupon_descuento (
    id integer not null auto_increment,
     producto_id integer,
    codigo varchar(50),
    porcentaje_descuento double,
    fecha_vencimiento date,
    activo boolean,
    primary key (id)
);

-- Producto 1 | Carolina Herrera
INSERT INTO cupon_descuento (producto_id, codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES (1, 'VERANO20', 20.0, '2026-12-31', true);

-- Producto 2 | Dior Sauvage
INSERT INTO cupon_descuento (producto_id, codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES (2, 'BIENVENIDA10', 10.0, '2026-12-31', true);

-- Producto 3 | Calvin Klein CK One
INSERT INTO cupon_descuento (producto_id, codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES (3, 'INVIERNO15', 15.0, '2026-06-01', true);