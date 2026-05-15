CREATE TABLE reserva (
    id BIGINT NOT NULL AUTO_INCREMENT,
    puntuacion INTEGER,
    comentario TEXT,
    fecha_resena VARCHAR(50),
    usuario_id BIGINT,
    primary key (id)
);

INSERT INTO reserva (puntuacion, comentario, fecha_resena, usuario_id)
VALUES (5, 'Excelente perfume', '2025-05-01', 1);
INSERT INTO reserva (puntuacion, comentario, fecha_resena, usuario_id)
VALUES (4, 'Muy buena fragancia', '2025-05-02', 2);