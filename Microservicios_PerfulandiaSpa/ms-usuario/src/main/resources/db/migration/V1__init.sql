CREATE TABLE usuarios (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    username VARCHAR(255),
    email VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(50),
    tipo VARCHAR(50),
    password VARCHAR(255),
    PRIMARY KEY (id)
);


INSERT INTO usuarios (nombre, username, email, direccion, telefono, tipo, password)
VALUES ('Cliente', 'cliente1', 'cliente@gmail.com', 'Av. Principal 456', '987654321', 'USER', '1234');

INSERT INTO usuarios (nombre, username, email, direccion, telefono, tipo, password)
VALUES ('Juan Perez', 'juanp', 'juan@gmail.com', 'Santiago Centro 101', '911111111', 'USER', '1234');

INSERT INTO usuarios (nombre, username, email, direccion, telefono, tipo, password)
VALUES ('Admin', 'admin', 'admin@perfulandia.cl', 'Meiggs 123', '912345678', 'ADMIN', '1234');

INSERT INTO usuarios (nombre, username, email, direccion, telefono, tipo, password)
VALUES ('Carlos Rojas', 'carlosr', 'carlos@gmail.com', 'Las Condes 303', '933333333', 'ADMIN', 'admin123');