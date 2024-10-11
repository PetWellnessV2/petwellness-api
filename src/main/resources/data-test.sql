-- Insertar datos de prueba en la tabla usuarios
INSERT INTO usuarios (user_id, nombre, apellido, email, telefono, contrasena, tipo_usuario, created_at, updated_at)
VALUES
    (1, 'Juan', 'Pérez', 'juan.perez@example.com', '123456789', 'password123', 'DUEÑO', '2023-09-01 12:00:00', '2023-09-01 12:00:00'),
    (2, 'Ana', 'García', 'ana.garcia@example.com', '987654321', 'password456', 'VETERINARIO', '2023-09-02 13:00:00', '2023-09-02 13:00:00'),
    (3, 'Jose', 'Jimenez', 'jose4@gmail.com', '985652985', 'contra123', 'VETERINARIO', '2023-10-08 13:00:00', '2023-10-08 13:00:00')
ON CONFLICT (user_id) DO NOTHING;

-- Insertar datos de prueba en la tabla veterinario
INSERT INTO veterinario (usuario_user_id, institucion_educativa, especialidad)
VALUES
    (2, 'UNIVERSIDAD_NACIONAL_MAYOR_DE_SAN_MARCOS', 'CIRUGIA'),
    (3, 'CORNELL_UNIVERSITY', 'ODONTOLOGIA')
ON CONFLICT (usuario_user_id) DO NOTHING;

-- Insertar datos de prueba en la tabla albergue
INSERT INTO horarios_disponibles (id_horario, vet_user_id, hora, fecha)
VALUES
    (1, 2, 10, '2023-09-03'),
    (2, 3, 15, '2023-09-04'),
    (3, 2, 17, '2023-09-05')
ON CONFLICT (id_horario) DO NOTHING;

-- Insert sample data into albergue table
INSERT INTO albergue (usuario_user_id, nombre_albergue, tipo_albergue, ruc)
VALUES
    (1, 'Refugio de Animales Felices', 'PRIVADO', '12345678901')
ON CONFLICT (usuario_user_id) DO NOTHING;

-- Insertar datos de prueba en la tabla registro_mascota
INSERT INTO registro_mascota (id_mascota, usuario_user_id, especie, genero, raza, nombre, edad, foto, fecha_nacimiento, descripcion, direccion, miembro_id, titular_poliza, info_adicional)
VALUES
    (1, 1, 'PERRO', 'MACHO', 'LABRADOR', 'Firulais', 5, 'foto_perro.png', '2018-01-01', 'Perro juguetón', 'Av. Siempre Viva 123', '00000123', 'Juan Pérez', 'Sin observaciones'),
    (2, 2, 'GATO', 'HEMBRA', 'SIAMES', 'Mishifu', 3, 'foto_gato.png', '2020-03-01', 'Gato independiente', 'Calle Falsa 456', '00000456', 'Ana García', 'Observaciones de salud')
ON CONFLICT (id_mascota) DO NOTHING;

-- Insertar datos de prueba en la tabla archivos
INSERT INTO archivos (id_archivos, nombre_archivo, descripcion_archivo, fecha, registro_mascota_id_mascota)
VALUES
    (1, 'Vacunas Firulais', 'Registro de vacunas', '2023-08-15', 1),
    (2, 'Checkup Mishifu', 'Chequeo anual de salud', '2023-08-20', 2)
ON CONFLICT (id_archivos) DO NOTHING;


-- Insertar datos de prueba en la tabla colecciones
INSERT INTO colecciones (id, nombre, usuario_id)
VALUES 
(1, 'Favoritos de Juan', 1),
(2, 'Lista de deseos de Juan', 1),
(3, 'Favoritos de Maria', 2)
ON CONFLICT (id) DO NOTHING;

-- Insertar datos de prueba en la tabla producto
INSERT INTO producto (id_producto, nombre_producto, imagen, descripcion, costo, tipo_producto, stock)
VALUES
    (1, 'Collar para perro', 'collar.png', 'Collar resistente para perros grandes', 20.50, 'ACCESORIO', 100),
    (2, 'Arena para gatos', 'arena.png', 'Arena absorbente para gatos', 10.25, 'MEDICAMENTO', 200),
    (3, 'Comida para gatos', 'cat_food.jpg', 'Alimento premium para gatos', 25.50, 'ALIMENTO', 200)

ON CONFLICT (id_producto) DO NOTHING;

-- Insertar datos de prueba de relaciones producto-colección
INSERT INTO productos_coleccion (id, coleccion_id, producto_id)
VALUES 
(1, 1, 1),
(2, 1, 2),
(3, 2, 3)
ON CONFLICT (id) DO NOTHING;

-- Insertar datos de prueba en la tabla carrito_compra
INSERT INTO carrito_compra (id_compra, usuario_user_id, producto_id_producto, precio_total, created_at, payment_status)
VALUES
    (1, 1, 1, 100.50, '2023-09-01 10:00:00', 'PENDIENTE'),
    (2, 2, 2, 50.25, '2023-09-02 11:00:00', 'PAGADO')
ON CONFLICT (id_compra) DO NOTHING;

-- Insertar datos de prueba en la tabla consulta
INSERT INTO consulta (id_consulta, tipo_consulta, estado_consulta, horarios_disponibles_id_horario, registro_mascota_id_mascota, razon_consulta, created_at, updated_at)
VALUES
    (1, 'SEGUIMIENTO', 'PENDIENTE', 1, 1, 'Consulta general para Firulais','2023-09-01 12:00:00', '2023-09-01 12:00:00'),
    (2, 'URGENCIA', 'PENDIENTE', 2, 2, 'Vacunación anual para Mishifu','2024-04-11 12:00:00', '2024-04-11 12:00:00'),
    (3, 'SEGUIMIENTO', 'COMPLETADO', 3, 1, 'Revision mensual de Firulais','2024-04-10 12:00:00', '2024-04-10 12:00:00')
ON CONFLICT (id_consulta) DO NOTHING;

-- Insertar datos de prueba en la tabla examenes_laboratorio
INSERT INTO examenes_laboratorio (id_examen_lab, nombre, descripcion, fecha, registro_mascota_id_mascota)
VALUES
    (1, 'Análisis de sangre', 'Análisis completo de sangre', '2023-08-15', 1),
    (2, 'Examen de orina', 'Examen completo de orina', '2023-08-20', 2)
ON CONFLICT (id_examen_lab) DO NOTHING;

-- Insertar datos de prueba en la tabla examen_fisico
INSERT INTO examen_fisico (id_examen, registro_mascota_id_mascota, presion_arterial, pulso, temperatura, peso, altura)
VALUES
    (1, 1, 120, 80, 37.5, 25.5, 0.6),
    (2, 2, 110, 75, 37.0, 4.2, 0.3)
ON CONFLICT (id_examen) DO NOTHING;

-- Insertar datos de prueba en la tabla medicamentos
INSERT INTO medicamentos (id_medicamento, descripcion, fecha, registro_mascota_id_mascota)
VALUES
    (1, 'Antibiótico', '2023-09-01', 1),
    (2, 'Desparasitante', '2023-09-02', 2)
ON CONFLICT (id_medicamento) DO NOTHING;

-- Insertar datos de prueba en la tabla notas_consulta
INSERT INTO notas_consulta (id_notas, fecha, descripcion_nota, registro_mascota_id_mascota)
VALUES
    (1, '2023-09-01', 'Notas sobre el estado de Firulais', 1),
    (2, '2023-09-02', 'Notas sobre el estado de Mishifu', 2)
ON CONFLICT (id_notas) DO NOTHING;

-- Insertar datos de prueba en la tabla recordatorios
INSERT INTO recordatorio (
    recordatorio_id, usuario_id, mascota_id, tipo_recordatorio, titulo, descripcion, fecha_hora, recordatorio_status
)
VALUES
    (1, 1, 1, 'VACUNACION', 'Vacunación Anual', 'Vacunación anual para Firulais', '2023-09-05 09:00:00', 'CREADO'),
    (2, 1, 2, 'DESPARASITACION', 'Desparasitación', 'Desparasitación de Mishifu', '2023-09-10 10:00:00', 'ENVIADO')
ON CONFLICT (recordatorio_id) DO NOTHING;

-- Insertar datos de prueba en la tabla pedidos
INSERT INTO pedidos (id_pedido, usuario_id, fecha_pedido, estado)
VALUES
    (1, 1, '2023-09-01 10:00:00', ' '),
    (2, 2, '2023-09-02 11:00:00', 'EN_PROCESO'),
    (3, 1, '2023-09-03 12:00:00', 'ENVIADO')
ON CONFLICT (id_pedido) DO NOTHING;

-- Insertar datos de prueba en la tabla detalle_pedidos
INSERT INTO detalle_pedidos (id_detalle, id_pedido, id_producto, cantidad, precio_total)
VALUES
    (1, 1, 1, 2, 41.00),
    (2, 1, 2, 1, 10.25),
    (3, 2, 3, 3, 76.50),
    (4, 3, 1, 1, 20.50),
    (5, 3, 2, 2, 20.50)
ON CONFLICT (id_detalle) DO NOTHING;

-- Insertar datos de prueba en la tabla notificacion
INSERT INTO notificaciones (id_notificacion, usuario_user_id, mensaje, leida, fecha_creacion)
VALUES
    (1, 1, 'Tu mascota ha sido registrada exitosamente', false, '2023-09-01 10:00:00'),
    (2, 2, 'La información de tu mascota ha sido actualizada', false, '2023-09-01 10:00:00')
ON CONFLICT (id_notificacion) DO NOTHING;
