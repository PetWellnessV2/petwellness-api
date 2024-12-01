
## Introducción

**PetWellness** es una plataforma integral diseñada para ofrecer soluciones personalizadas y completas a los dueños de mascotas, enfocándose en proporcionar información confiable, servicios veterinarios remotos y una comunidad activa en línea. Con PetWellness, los usuarios pueden crear una cuenta, acceder a consejos de salud adaptados a las necesidades de su mascota, y realizar consultas veterinarias en línea, así como programar citas con profesionales locales. Además, la plataforma permite a los usuarios organizar la información de sus mascotas, incluyendo registros de salud y recordatorios automáticos, para garantizar un cuidado continuo y adecuado.

Los administradores pueden gestionar eficientemente la plataforma a través de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre productos, categorías, veterinarios y usuarios, garantizando que la información y los servicios ofrecidos en la plataforma estén siempre actualizados y en línea con las necesidades del mercado.

El propósito de PetWellness es ofrecer una plataforma unificada que combine la facilidad de acceso a servicios veterinarios, productos especializados y una comunidad de apoyo, todo en un entorno seguro y fácil de usar. La plataforma permite a los dueños de mascotas no solo cuidar de sus compañeros peludos, sino también conectarse con otros cuidadores, compartiendo experiencias y consejos.

### Revisa el Progreso del Proyecto PetWellness

| **Columna**       | **Descripción**                                                                                                                                    |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backlog**       | Contiene todas las historias de usuario, tareas y características que deben desarrollarse. Es el listado de todo el trabajo pendiente.              |
| **En Progreso**   | Incluye las tareas que están actualmente en desarrollo. Visualiza el trabajo en curso para asegurar el flujo continuo de trabajo.                   |
| **Revisión**      | Después de completar una tarea, se mueve aquí para una revisión de código y revisión por pares (peer review). Esta fase incluye la creación de **pull requests** para asegurar que el código cumpla con los estándares de calidad antes de integrarse al proyecto principal. |
| **En Pruebas**    | Contiene las tareas que han pasado la revisión de código y necesitan pruebas exhaustivas (unitarias, de integración y de aceptación) para garantizar su calidad. |
| **Hecho**         | Las tareas completamente desarrolladas, revisadas y probadas se mueven aquí, indicando que están listas y finalizadas.                               |

Mira cómo va avanzando nuestro trabajo visitando el siguiente enlace: [Tablero de Trello](https://trello.com/invite/b/66df9b90adc79739768b56c7/ATTI1f0522ce1c974ea160da75e4d3ed2c8cD691B6BA/user-stories-petwellness).


### Funcionalidades de la Aplicación PetWellness

#### **Módulo de Gestión de Usuarios**

- **Creación de Usuarios e Inicio de Sesión:**
    - Permitir a los dueños de mascotas, veterinarios y administradores registrarse en la plataforma.
    - Facilitar el inicio de sesión para que los usuarios accedan a su perfil y administren la información de sus mascotas o servicios.
    - Mantener la seguridad de las credenciales de los usuarios, asegurando la protección de los datos sensibles.

#### **Módulo de Compras**

- **Compra de Productos y Servicios para Mascotas:**
    - Integración con sistemas de pago como PayPal para realizar pagos seguros y rápidos de productos y servicios veterinarios.
    - Procesamiento de transacciones para la compra de productos relacionados con el cuidado de mascotas, como alimentos, accesorios, y medicamentos.
    - Confirmación de compra y seguimiento del pedido, brindando información actualizada sobre el estado de la entrega.

#### **Módulo de Gestión de Contenido**

- **Gestión de Productos y Servicios:**
    - Añadir nuevos productos al catálogo, como alimentos, accesorios o medicamentos.
    - Editar detalles de los productos y servicios disponibles para los usuarios.
    - Eliminar productos o servicios que ya no estén disponibles.
    - Listar todos los productos y servicios disponibles para facilitar su compra por los usuarios.

- **Categorías de Productos:**
    - Clasificar productos en diferentes categorías, como "Alimentos", "Accesorios", "Medicamentos", etc.
    - Facilitar la navegación y búsqueda de productos por categoría, mejorando la experiencia de compra del usuario.
    - Mantener un catálogo organizado y accesible.

- **Gestión de Veterinarios y Consultas:**
    - Permitir a los administradores añadir y gestionar la información de los veterinarios registrados.
    - Facilitar la edición de la información de los veterinarios (especialidades, horarios disponibles, etc.).
    - Eliminar veterinarios o servicios que ya no estén activos en la plataforma.
    - Mantener actualizada la lista de veterinarios y servicios ofrecidos.

#### **Módulo de Gestión de Mascotas**

- **Registros de Salud de las Mascotas:**
    - Permitir a los usuarios registrar y administrar la información de sus mascotas, como vacunas, tratamientos médicos, y revisiones periódicas.
    - Facilitar la creación, edición y eliminación de registros de salud y servicios prestados.
    - Mejorar el acceso y gestión de la información médica de las mascotas, con opciones de recordatorios automáticos para próximas citas o tratamientos.

#### **Módulo de Reportes**

- **Reportes de Actividad y Servicios:**
    - Generar reportes sobre las consultas veterinarias realizadas, productos comprados y servicios solicitados.
    - Mostrar estadísticas sobre las actividades de los usuarios, como los productos más comprados o las consultas más solicitadas.
    - Proveer información detallada para los administradores sobre el uso de la plataforma, ayudando a mejorar la oferta de productos y servicios.

### Diagramas de la Aplicación

Para entender mejor la estructura y diseño de la aplicación "PetWellness", se podrían incluir diagramas UML como los siguientes:

- **Diagrama de Casos de Uso:** Describirá las interacciones entre los diferentes tipos de usuarios (dueños de mascotas, veterinarios y administradores) con la plataforma.
- **Diagrama de Clases:** Detallará las entidades principales, como *Usuarios*, *Mascotas*, *Productos*, *Consultas*, y sus relaciones entre sí.
- **Diagrama de Secuencia:** Representará el flujo de los principales procesos, como la compra de un producto, la programación de una consulta o la gestión de un registro de salud de una mascota.
- **Diagrama de Componentes:** Explicará cómo los diferentes módulos de la aplicación se comunican entre sí y cómo están distribuidos.

## Diagramas de la Aplicación

Para entender mejor la estructura y diseño de la aplicación "BookHub", revisa los siguientes diagramas:

### Diagrama de Clases

![Diagrama de Clases](diagrama_clase_venta_libro.png)


### Diagrama de Base de Datos

![Diagrama de Base de Datos](diagrama_base_datos_venta_libro.png)

Este diagrama ilustra el esquema de la base de datos utilizada por la aplicación, mostrando las tablas, columnas, y relaciones entre las entidades.

### Descripción de Capas del Proyecto

| capa        | descripción                                                                                  |
|-------------|----------------------------------------------------------------------------------------------|
| api         | Contiene los controladores REST que manejan las solicitudes HTTP y las respuestas.            |
| entity      | Define las entidades del modelo de datos que se mapean a las tablas de la base de datos.      |
| repository  | Proporciona la interfaz para las operaciones CRUD y la interacción con la base de datos.      |
| service     | Declara la lógica de negocio y las operaciones que se realizarán sobre las entidades.         |
| service impl| Implementa la lógica de negocio definida en los servicios, utilizando los repositorios necesarios. |
