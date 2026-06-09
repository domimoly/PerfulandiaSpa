CREATE TABLE cupon_descuento (
    id INTEGER NOT NULL AUTO_INCREMENT,
    categoria INTEGER,
    codigo VARCHAR(50),
    porcentaje_descuento DOUBLE,
    fecha_vencimiento DATE,
    activo BOOLEAN,
    PRIMARY KEY (id)
);

-- Categoria 1 | Perfume Hombre
INSERT INTO cupon_descuento (categoria, codigo, porcentaje_descuento, fecha_vencimiento, activo)
VALUES (1, 'HOMBRE20', 20.0, '2026-12-31', true);

-- Categoria 1 | Perfume Hombre (segundo cupón)
INSERT INTO cupon_descuento (categoria, codigo, porcentaje_descuento, fecha_vencimiento, activo)
VALUES (1, 'VERANO10', 10.0, '2026-08-31', true);

-- Categoria 2 | Perfume Mujer
INSERT INTO cupon_descuento (categoria, codigo, porcentaje_descuento, fecha_vencimiento, activo)
VALUES (2, 'MUJER15', 15.0, '2026-12-31', true);