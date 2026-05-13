create table ticket_soporte (
    id integer not null auto_increment,
    asunto varchar(255),
    mensaje text,
    estado varchar(50),
    fecha_ticket varchar(50),
    cliente_id integer,
    primary key (id)
);

INSERT INTO ticket_soporte (asunto, mensaje, estado, fecha_ticket, cliente_id)
VALUES ('Problema con pedido', 'No llegó mi pedido', 'Abierto', '2026-05-01', 1);

INSERT INTO ticket_soporte (asunto, mensaje, estado, fecha_ticket, cliente_id)
VALUES ('Perfume dañado', 'El frasco llegó roto', 'Abierto', '2026-05-02', 2);