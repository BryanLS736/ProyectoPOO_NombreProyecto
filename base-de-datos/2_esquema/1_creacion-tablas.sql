CREATE DATABASE panaderia_db;
USE panaderia_db;

-- =========================
-- EMPLEADO
-- =========================

CREATE TABLE Empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni CHAR(8) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(15),
    rol ENUM('Administrador','Cajero') NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- CLIENTE
-- =========================

CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni CHAR(8) UNIQUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- PRODUCTO
-- =========================

CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    precio DECIMAL(10,2) NOT NULL,
    estado ENUM('Activo','Inactivo') DEFAULT 'Activo',
    descripcion VARCHAR(255),
    unid_medida VARCHAR(50),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- CAJA
-- =========================

CREATE TABLE Caja (
    id_caja INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado_apertura INT NOT NULL,
    id_empleado_cierre INT NULL,
    fecha DATE NOT NULL,
    hora_apertura TIME NOT NULL,
    hora_cierre TIME,
    monto_apertura DECIMAL(10,2) NOT NULL,
    monto_cierre DECIMAL(10,2),
    estado ENUM('Abierta','Cerrada') DEFAULT 'Abierta',

    CONSTRAINT fk_caja_apertura
        FOREIGN KEY (id_empleado_apertura)
        REFERENCES Empleado(id_empleado),

    CONSTRAINT fk_caja_cierre
        FOREIGN KEY (id_empleado_cierre)
        REFERENCES Empleado(id_empleado)
);

-- =========================
-- VENTA
-- =========================

CREATE TABLE Venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT NOT NULL,
    id_caja INT NOT NULL,
    id_cliente INT,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo_despacho ENUM('Local','Delivery') DEFAULT 'Local',
    nota_adicional VARCHAR(255),
    total_venta DECIMAL(10,2) NOT NULL,
    metodo_pago ENUM('Efectivo','Tarjeta','Yape','Plin') NOT NULL,

    CONSTRAINT fk_venta_empleado
        FOREIGN KEY (id_empleado)
        REFERENCES Empleado(id_empleado),

    CONSTRAINT fk_venta_caja
        FOREIGN KEY (id_caja)
        REFERENCES Caja(id_caja),

    CONSTRAINT fk_venta_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES Cliente(id_cliente)
);

-- =========================
-- DETALLE VENTA
-- =========================

CREATE TABLE Detalle_Venta (
    id_detalle_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    detalle VARCHAR(255),

    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (id_venta)
        REFERENCES Venta(id_venta)
        ON DELETE CASCADE,

    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (id_producto)
        REFERENCES Producto(id_producto)
);