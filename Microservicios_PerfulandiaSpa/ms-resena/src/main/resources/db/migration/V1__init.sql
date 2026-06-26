CREATE TABLE resena (
    id BIGINT NOT NULL AUTO_INCREMENT,
    puntuacion INTEGER,
    comentario TEXT,
    fecha_resena VARCHAR(50),
    usuario_id BIGINT,
    primary key (id)
);

INSERT INTO resena (puntuacion, comentario, fecha_resena, usuario_id)
VALUES (5, 'Excelente perfume', '2026-05-01', 1);

INSERT INTO resena (puntuacion, comentario, fecha_resena, usuario_id)
VALUES (4, 'Muy buena fragancia', '2026-05-02', 2);