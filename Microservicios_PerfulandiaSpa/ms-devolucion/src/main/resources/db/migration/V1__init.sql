create table devolucion (
    id integer not null auto_increment,
    fechaDevolucion varchar(255),
    motivo varchar(255),
    estado varchar(255),
    primary key (id)
);

INSERT INTO devolucion (fechaDevolucion, motivo, estado) VALUES ('2026-05-09', 'Producto defectuoso', 'Pendiente');
INSERT INTO devolucion (fechaDevolucion, motivo, estado) VALUES ('2026-05-10', 'Producto no deseado', 'Aprobada');