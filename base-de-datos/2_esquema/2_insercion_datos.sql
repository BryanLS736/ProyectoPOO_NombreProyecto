-- INSERCIÓN DE EMPLEADOS --

INSERT INTO Empleado (nombres, apellidos, dni, contrasenia, direccion, telefono, rol,activo)
VALUES  
		('Juan Esteban', 'Perez Gomez', '12345678', '1234', 'Av. Lima 123', '987654321', 'Administrador', TRUE),
		('Maria Alexa', 'Lopez Diaz', '23456789', 'abcd', 'Jr. Los Olivos 456', '987654322', 'Empleado', TRUE),
		('Carlos', 'Ramirez Soto', '34567890', 'pass1', 'Av. Brasil 789', '987654323', 'Empleado', TRUE),
		('Ana Maria', 'Vargas Ruiz', '45678901', 'pass2', 'Av. Peru 321', '987654324', 'Empleado', TRUE);


-- INSERCIÓN DE CLIENTES

INSERT INTO Cliente (nombre, dni,activo)
VALUES 
		('Luis', '76491376', TRUE),
		('Pedro', '71679813', TRUE),
		('Lucia', '06615298', TRUE),
		('Miguel', '78958648', TRUE);


-- INSERCIÓN DE PRODUCTOS --

INSERT INTO Producto (nombre, categoria, stock, precio, activo)
VALUES 
		('Pan francés', 'Panes', 50, 0.20, TRUE),
		('Alfajor', 'Bocaditos', 20, 2.00, TRUE),
		('Café americano', 'Bebidas', 60, 2.00, TRUE),
		('Torta de Vainilla', 'Tortas', 10, 5.00, TRUE);


-- INSERCIÓN DE CAJA --

INSERT INTO Caja (id_empleado_apertura, id_empleado_cierre, fecha, hora_apertura, hora_cierre, monto_apertura, monto_cierre, estado)
VALUES 
		(1, 2, '2026-06-24', '08:00:00', '18:00:00', 100.00, 500.00, 'Cerrada'),
		(3, 1, '2026-06-25', '09:00:00', '17:00:00', 80.00, 300.00, 'Cerrada');


-- INSERCIÓN DE VENTAS --

INSERT INTO Venta (id_empleado, id_caja, id_cliente, tipo_despacho, nota_adicional, total_venta, metodo_pago)
VALUES 
		(1, 1, 1, 'Aquí', 'Sin azúcar', 10.00, 'Efectivo'),
		(2, 2, 2, 'Llevar', 'Entrega rápida', 8.00, 'Yape'),
		(3, 1, 3, 'Delivery', 'Llamar antes', 12.00, 'Tarjeta'),
		(4, 2, 4, 'Aquí', '', 15.00, 'Efectivo');
        

-- INSERCIÓN DE DETALLE_VENTA --

INSERT INTO Detalle_Venta (id_venta, id_producto, cantidad, precio_unitario, precio_total)
VALUES 
		(1, 1, 2, 0.20, 0.40),
		(2, 2, 3, 2.00, 6.00),
		(3, 3, 2, 2.00, 4.00),
		(4, 4, 2, 5.00, 10.00);