create table orden (
    id integer not null auto_increment,
    numeroOrden integer,
    fechaCreacion varchar(255),
    fechaRecibida varchar(255),
    total integer,
    descuentoAplicado integer,
    primary key (id)
);

INSERT INTO orden (numeroOrden, fechaCreacion, fechaRecibida, total, descuentoAplicado) VALUES (1, '2026-03-20', '2026-03-23', 5000, 500);
INSERT INTO orden (numeroOrden, fechaCreacion, fechaRecibida, total, descuentoAplicado) VALUES (2, '2026-03-24', '2026-03-26', 6000, 500);