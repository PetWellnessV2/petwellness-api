-- Insert Roles
INSERT INTO roles (id, role) VALUES
                                 (1, 'CUSTOMER'),
                                 (2, 'ADMIN'),
                                 (3, 'VETERINARIO')
    ON CONFLICT (id) DO NOTHING;


ALTER TABLE users DROP CONSTRAINT IF EXISTS unique_user_constraint;
ALTER TABLE users ADD CONSTRAINT unique_user_constraint UNIQUE (email);
-- Insert Users (2 Veterinarians and 3 Customers)
INSERT INTO users (user_id, email, contrasena, role_id)
VALUES
    (1, 'customer1@example.com', 'password123', 1),
    (2, 'customer2@example.com', 'password123', 1),
    (3, 'customer3@example.com', 'password123', 1),
    (4, 'vet1@example.com', 'password123', 3),
    (5, 'vet2@example.com', 'password123', 3),
    (6, 'admin@example.com', 'password123', 2)
    ON CONFLICT ON CONSTRAINT unique_user_constraint DO NOTHING;


ALTER TABLE usuarios DROP CONSTRAINT IF EXISTS unique_usuario_constraint;
ALTER TABLE usuarios ADD CONSTRAINT unique_usuario_constraint UNIQUE (nombre, apellido);
-- Insert Customers (Linked to Users)
INSERT INTO usuarios (user_id, nombre, apellido, telefono, tipo_usuario, created_at, updated_at)
VALUES
    (1, 'Juan', 'Pérez', '123456789', 'CUSTOMER', '2023-09-01 12:00:00', '2023-09-01 12:00:00'),
    (2, 'Ana', 'García', '987654321', 'CUSTOMER', '2023-09-02 13:00:00', '2023-09-02 13:00:00'),
    (3, 'Maria', 'Lopez', '998877665', 'CUSTOMER', '2023-09-03 14:00:00', '2023-09-03 14:00:00'),
    (4, 'JuanVet', 'PérezVet', '123456781', 'VETERINARIO', '2023-09-01 12:00:00', '2023-09-01 12:00:00'),
    (5, 'AnaVet', 'GarcíaVet', '987654320', 'VETERINARIO', '2023-09-02 13:00:00', '2023-09-02 13:00:00'),
    (6, 'Carlos', 'Rento', '123456780', 'ADMIN', '2023-09-04 15:00:00', '2023-09-04 15:00:00')
    ON CONFLICT ON CONSTRAINT unique_usuario_constraint DO NOTHING;


-- Insert Veterinarians (Linked to Users)
INSERT INTO veterinario (usuario_user_id, institucion_educativa, especialidad)
VALUES
    (4, 'UNIVERSIDAD_NACIONAL_MAYOR_DE_SAN_MARCOS', 'CIRUGIA'),
    (5, 'CORNELL_UNIVERSITY', 'ODONTOLOGIA')
    ON CONFLICT (usuario_user_id) DO NOTHING;

INSERT INTO albergue (usuario_user_id, tipo, ruc)
VALUES
    (6, 'PERMANENTE', '12345678901')
    ON CONFLICT (usuario_user_id) DO NOTHING;


ALTER TABLE horarios_disponibles DROP CONSTRAINT IF EXISTS unique_horario_disponible_constraint;
ALTER TABLE horarios_disponibles ADD CONSTRAINT unique_horario_disponible_constraint UNIQUE (vet_user_id, hora, fecha);

-- Insertar datos de prueba en la tabla horario disponible
INSERT INTO horarios_disponibles (vet_user_id, hora, fecha)
VALUES
    (4, '09:00:00', '2024-11-01'),
    (4, '10:00:00', '2024-11-01'),
    (4, '11:00:00', '2024-11-01'),
    (5, '14:00:00', '2024-11-01'),
    (5, '15:00:00', '2024-11-01')
    ON CONFLICT ON CONSTRAINT unique_horario_disponible_constraint DO NOTHING;

--
-- -- Insert sample data into albergue table
-- INSERT INTO albergue (usuario_user_id, nombre_albergue, tipo_albergue, ruc)
-- VALUES
--     (1, 'Refugio de Animales Felices', 'PRIVADO', '12345678901')
-- ON CONFLICT (usuario_user_id) DO NOTHING;


ALTER TABLE mascotas DROP CONSTRAINT IF EXISTS unique_pet_constraint;
ALTER TABLE mascotas ADD CONSTRAINT unique_pet_constraint UNIQUE (especie, genero, nombre, usuarios_user_id);
-- Insertar datos de prueba en la tabla mascotas
INSERT INTO mascotas (usuarios_user_id, especie, genero, raza, nombre, edad, foto, fecha_nacimiento, descripcion, direccion, miembro_id, titular_poliza, info_adicional)
VALUES
    (1, 'PERRO', 'MACHO', 'LABRADOR', 'Max', 3, 'https://example.com/foto_max.jpg', '2020-06-15', 'Perro muy juguetón y amigable', 'Av. Siempre Viva 123', 'M1234567', 'Juan Pérez', 'Le gusta correr y jugar con pelotas'),
    (2, 'GATO', 'HEMBRA', 'SIAMES', 'Mila', 2, 'https://example.com/foto_mila.jpg', '2021-08-20', 'Gata tranquila y cariñosa', 'Calle Falsa 456', 'F2345678', 'Ana García', 'Le gusta dormir en lugares altos'),
    (3, 'PERRO', 'MACHO', 'BULLDOG', 'Bruno', 4, 'https://example.com/foto_bruno.jpg', '2019-03-05', 'Perro protector y leal', 'Av. Libertad 789', 'M3456789', 'Maria Lopez', 'Muy protector con la familia')
    ON CONFLICT ON CONSTRAINT unique_pet_constraint DO NOTHING;


ALTER TABLE archivos DROP CONSTRAINT IF EXISTS unique_archivos_constraint;
ALTER TABLE archivos ADD CONSTRAINT unique_archivos_constraint UNIQUE (nombre, mascota_id_mascota);
-- Insertar datos de prueba en la tabla archivos
INSERT INTO archivos (nombre, descripcion, fecha, mascota_id_mascota)
VALUES
    ('Vacunas Firulais', 'Registro de vacunas', '2023-08-15', 1),
    ('Checkup Mishifu', 'Chequeo anual de salud', '2023-08-20', 2)
    ON CONFLICT ON CONSTRAINT unique_archivos_constraint DO NOTHING;
--
--
-- -- Insertar datos de prueba en la tabla colecciones
-- INSERT INTO colecciones (id, nombre, usuario_id)
-- VALUES
-- (1, 'Favoritos de Juan', 1),
-- (2, 'Lista de deseos de Juan', 1),
-- (3, 'Favoritos de Maria', 2)
-- ON CONFLICT (id) DO NOTHING;
--
-- -- Insertar datos en la tabla categoria_producto
-- INSERT INTO categoria_producto (id, name, description, created_at, updated_at)
-- VALUES
--     (1, 'Alimentos', 'Productos de alimentación para animales', NOW(), NOW()),
--     (2, 'Juguetes', 'Juguetes para entretenimiento de mascotas', NOW(), NOW()),
--     (3, 'Medicamentos', 'Productos médicos y suplementos para mascotas', NOW(), NOW()),
--     (4, 'Accesorios', 'Accesorios como collares, correas, etc.', NOW(), NOW()),
--     (5, 'Higiene', 'Productos para la higiene y limpieza de mascotas', NOW(), NOW())
--
-- ON CONFLICT (id) DO NOTHING;
--
--
-- -- Insertar datos de prueba en la tabla producto
-- -- Insertar datos de prueba en la tabla producto
-- INSERT INTO producto (id_producto, nombre_producto, imagen, descripcion, costo, categoria_producto_id, stock)
-- VALUES
--     (1, 'Collar para perro', 'collar.png', 'Collar resistente para perros grandes', 20.50, 4, 100),  -- Accesorios
--     (2, 'Arena para gatos', 'arena.png', 'Arena absorbente para gatos', 10.25, 3, 200),  -- Medicamentos
--     (3, 'Comida para gatos', 'cat_food.jpg', 'Alimento premium para gatos', 25.50, 1, 200),  -- Alimentos
--     (4, 'Pelota para perros', 'pelota.png', 'Juguete de goma para perros', 5.75, 2, 150),  -- Juguetes
--     (5, 'Shampoo para perros', 'shampoo.png', 'Shampoo especial para el cuidado de la piel de los perros', 15.00, 5, 80)  -- Higiene
--
-- ON CONFLICT (id_producto) DO NOTHING;
--
--
-- -- Insertar datos de prueba de relaciones producto-colección
-- INSERT INTO productos_coleccion (id, coleccion_id, producto_id)
-- VALUES
-- (1, 1, 1),
-- (2, 1, 2),
-- (3, 2, 3)
-- ON CONFLICT (id) DO NOTHING;
--
-- -- Insertar datos de prueba en la tabla carrito_compra
-- INSERT INTO carrito_compra (id_compra, usuario_user_id, producto_id_producto, precio_total, created_at, payment_status)
-- VALUES
--     (1, 1, 1, 100.50, '2023-09-01 10:00:00', 'PENDIENTE'),
--     (2, 2, 2, 50.25, '2023-09-02 11:00:00', 'PAGADO')
-- ON CONFLICT (id_compra) DO NOTHING;


ALTER TABLE consulta DROP CONSTRAINT IF EXISTS unique_consulta_constraint;
ALTER TABLE consulta ADD CONSTRAINT unique_consulta_constraint UNIQUE (horarios_disponibles_id_horario, mascota_id_mascota);
-- Insertar datos de prueba en la tabla consulta
INSERT INTO consulta (tipo_consulta, estado_consulta, horarios_disponibles_id_horario, mascota_id_mascota, razon_consulta, created_at)
VALUES
    ('SEGUIMIENTO', 'PENDIENTE', 1, 1, 'Consulta general para Firulais','2023-09-01 12:00:00'),
    ('URGENCIA', 'PENDIENTE', 2, 2, 'Vacunación anual para Mishifu','2024-04-11 12:00:00'),
    ('SEGUIMIENTO', 'COMPLETADO', 3, 1, 'Revision mensual de Firulais','2024-04-10 12:00:00')
    ON CONFLICT ON CONSTRAINT unique_consulta_constraint DO NOTHING;
--
-- -- Insertar datos de prueba en la tabla examenes_laboratorio
-- INSERT INTO examenes_laboratorio (id_examen_lab, nombre, descripcion, fecha, registro_mascota_id_mascota)
-- VALUES
--     (1, 'Análisis de sangre', 'Análisis completo de sangre', '2023-08-15', 1),
--     (2, 'Examen de orina', 'Examen completo de orina', '2023-08-20', 2)
-- ON CONFLICT (id_examen_lab) DO NOTHING;
--

ALTER TABLE examen_fisico DROP CONSTRAINT IF EXISTS unique_examen_fisico_constraint;
ALTER TABLE examen_fisico ADD CONSTRAINT unique_examen_fisico_constraint UNIQUE (mascota_id_mascota, presion_arterial, pulso, temperatura, peso, altura);
-- Insertar datos de prueba en la tabla examen_fisico
INSERT INTO examen_fisico (mascota_id_mascota, presion_arterial, pulso, temperatura, peso, altura)
VALUES
    (1, 120, 80, 37.5, 25.5, 0.6),
    (2, 110, 75, 37.0, 4.2, 0.3)
    ON CONFLICT ON CONSTRAINT unique_examen_fisico_constraint DO NOTHING;


ALTER TABLE medicamentos DROP CONSTRAINT IF EXISTS unique_medicamentos_constraint;
ALTER TABLE medicamentos ADD CONSTRAINT unique_medicamentos_constraint UNIQUE (nombre, fecha, mascota_id_mascota);
-- Insertar datos de prueba en la tabla medicamentos
INSERT INTO medicamentos (nombre, descripcion, fecha, mascota_id_mascota)
VALUES
    ('Antibiótico', 'Cada 5 horas por 3 semanas', '2023-09-01', 1),
    ('Desparasitante', 'Cada 8 horas por 1 semana', '2023-09-02', 2)
    ON CONFLICT ON CONSTRAINT unique_medicamentos_constraint DO NOTHING;
--
-- -- Insertar datos de prueba en la tabla notas_consulta
-- INSERT INTO notas_consulta (id_notas, fecha, descripcion_nota, registro_mascota_id_mascota)
-- VALUES
--     (1, '2023-09-01', 'Notas sobre el estado de Firulais', 1),
--     (2, '2023-09-02', 'Notas sobre el estado de Mishifu', 2)
-- ON CONFLICT (id_notas) DO NOTHING;
--
-- -- Insertar datos de prueba en la tabla recordatorios
-- INSERT INTO recordatorio (
--     recordatorio_id, usuario_id, mascota_id, tipo_recordatorio, titulo, descripcion, fecha_hora, recordatorio_status
-- )
-- VALUES
--     (1, 1, 1, 'VACUNACION', 'Vacunación Anual', 'Vacunación anual para Firulais', '2023-09-05 09:00:00', 'CREADO'),
--     (2, 1, 2, 'DESPARASITACION', 'Desparasitación', 'Desparasitación de Mishifu', '2023-09-10 10:00:00', 'ENVIADO')
-- ON CONFLICT (recordatorio_id) DO NOTHING;
--
-- -- Insertar datos de prueba en la tabla pedidos
-- INSERT INTO pedidos (id_pedido, usuario_id, fecha_pedido, estado)
-- VALUES
--     (1, 1, '2023-09-01 10:00:00', 'PENDIENTE'),
--     (2, 2, '2023-09-02 11:00:00', 'PENDIENTE'),
--     (3, 1, '2023-09-03 12:00:00', 'PENDIENTE')
-- ON CONFLICT (id_pedido) DO NOTHING;
--
-- -- Insertar datos de prueba en la tabla detalle_pedidos
-- INSERT INTO detalle_pedidos (id_detalle, id_pedido, id_producto, cantidad, precio_total)
-- VALUES
--     (1, 1, 1, 2, 41.00),
--     (2, 1, 2, 1, 10.25),
--     (3, 2, 3, 3, 76.50),
--     (4, 3, 1, 1, 20.50),
--     (5, 3, 2, 2, 20.50)
-- ON CONFLICT (id_detalle) DO NOTHING;
--

ALTER TABLE notificaciones DROP CONSTRAINT IF EXISTS unique_notificaciones_constraint;
ALTER TABLE notificaciones ADD CONSTRAINT unique_notificaciones_constraint UNIQUE (usuario_user_id, mensaje, leida);
-- Insertar datos de prueba en la tabla notificacion
INSERT INTO notificaciones (usuario_user_id, mensaje, leida, fecha_creacion)
VALUES
    (1, 'Tu mascota ha sido registrada exitosamente', false, '2023-09-01 10:00:00'),
    (2, 'La información de tu mascota ha sido actualizada', false, '2023-09-01 10:00:00')
ON CONFLICT ON CONSTRAINT unique_notificaciones_constraint DO NOTHING;


ALTER TABLE notas_consulta DROP CONSTRAINT IF EXISTS unique_notas_consulta_constraint;
ALTER TABLE notas_consulta ADD CONSTRAINT unique_notas_consulta_constraint UNIQUE (mascota_id_mascota, fecha, descripcion);
INSERT INTO notas_consulta (mascota_id_mascota, fecha, descripcion)
VALUES
    (1, '2024-10-10', 'Consulta de revisión general para la mascota'),
    (2, '2024-10-15', 'Vacunación de refuerzo aplicada'),
    (1, '2024-11-01', 'Consulta de seguimiento de tratamiento')
    ON CONFLICT ON CONSTRAINT unique_notas_consulta_constraint DO NOTHING;



