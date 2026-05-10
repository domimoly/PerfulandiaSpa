create table cupon_descuento (
    id integer not null auto_increment,
    codigo varchar(50) not null,
    porcentaje_descuento double not null,
    fecha_vencimiento date not null,
    activo boolean not null,
    primary key (id)
);

INSERT INTO cupones_descuento (codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES ('VERANO20', 20.0, '2026-12-31', true);

INSERT INTO cupones_descuento (codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES ('BIENVENIDA10', 10.0, '2026-12-31', true);

-- Nota | Este cupón es de prueba para demostrar que pasa si el usuario pone un cupón
-- Vencido, así se aprovecha las validaciones y se demuestra que el sistema atrapa errores
-- que no tienen que ver con algo mal hecho :3
INSERT INTO cupones_descuento (codigo, porcentaje_descuento, fecha_vencimiento, activo) 
VALUES ('INVIERNO15', 15.0, '2023-01-01', false);