create table cliente (
    id integer not null auto_increment,
    fecha_registro varchar(50),
    direccion_envio varchar(255),
    usuario_id integer,
    primary key (id)
);

INSERT INTO cliente (fecha_registro, direccion_envio, usuario_id)
VALUES ('2026-01-10', 'Av. Principal 456', 1);

INSERT INTO cliente (fecha_registro, direccion_envio, usuario_id)
VALUES ('2026-02-15', 'Las Condes 303', 2);