# API REST - Gestión de Tareas del Hogar

## Descripción del Proyecto
Esta API REST permite gestionar tareas del hogar de manera eficiente. Se implementa con **Kotlin y Spring Boot** utilizando **MongoDB** como base de datos.

### 📂 Colecciones en MongoDB

#### **1. Usuario**
Cada usuario registrado en la aplicación tendrá los siguientes campos:

- **_id:** Identificador único generado por MongoDB.
- **username:** String único y obligatorio.
- **password:** String cifrada.
- **email:** String único y obligatorio.
- **roles:** Puede ser `USER` o `ADMIN`.
- **direccion:** Un objeto con los datos de la ubicación del usuario.

#### **2. Dirección** (Subdocumento dentro de `usuarios`)
La dirección del usuario se almacena como un subdocumento con los siguientes campos:

- **calle:** Nombre de la calle.
- **cumero:** Número de la vivienda.
- **ciudad:** Ciudad en la que reside el usuario.
- **municipio:** Municipio correspondiente.
- **provincia:** Provincia de residencia.
- **cp:** Código postal.

#### **3. Tareas**
Cada tarea creada por un usuario o asignada por un administrador tendrá los siguientes campos:

- **_id:** Identificador único generado por MongoDB.
- **titulo:** String obligatorio.
- **desc:** String opcional.
- **estado:** Puede ser `PENDING` (pendiente) o `DONE` (completada).
- **usuario:** Identificador del usuario que tiene la tarea.
- **fechCreacion:** Fecha en la que se creó la tarea.
- **fechActualizacion:** Fecha de la última modificación.

---

## Enumeraciones (`Enums`)

### **Rol de Usuario (`Roles`)**
- `USER`: Usuario regular con permisos limitados.
- `ADMIN`: Administrador con permisos elevados.

### **Estado de Tarea (`TaskStatus`)**
- `PENDING`: La tarea aún no ha sido completada.
- `DONE`: La tarea ha sido marcada como completada.

---

## Endpoints de la API

### **1. Usuarios (`/users`)**
| Método   | Endpoint             | Descripción                                                                                                                       |
|----------|----------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `GET`    | `/usuarios/{id}`     | Obtiene la información de un usuario por ID, un admin puede obtener cualquier información <br/>y un usuario estandard solo la suya. |
| `GET`    | `/usuarios`          | Solo para ADMIN, obtiene todos los usuarios.                                                                                      
| `POST`   | `/usuarios/register` | Registra un nuevo usuario.                                                                                                        |
| `POST`   | `/usuarios/login`    | Inicia sesión y devuelve un token JWT.                                                                                            |
| `DELETE` | `/usuarios`    | Elimina un usuario, un ADMIN podrá eliminar a cualquier usuario.                                                                  |
| `PUT`    | `/usuarios`    | Actualiza un usuario, un ADMIN podrá actualizar a cualquier usuario.  

---

### **2. Tareas (`/tasks`)**
| Método | Endpoint       | Descripción |
|--------|----------------|-------------|
| `GET`  | `/tareas`      | Un usuario obtiene sus propias tareas. Un ADMIN obtiene todas. |
| `GET`  | `/tareas/{id}` | Obtiene una tarea por ID (solo si pertenece al usuario o si es ADMIN). |
| `POST` | `/tareas`      | Un usuario crea una tarea para sí mismo, un ADMIN puede crear para cualquier usuario. |
| `PUT`  | `/tareas/{id}` | Un usuario actualiza el estado de su tarea a `DONE`. Un ADMIN puede actualizar cualquier tarea. |
| `DELETE` | `/tareas/{id}` | Un usuario elimina su propia tarea. Un ADMIN puede eliminar cualquier tarea. |

---

## Lógica de Negocio

1. **Registro y Login:**
    - Cualquier persona puede registrarse y loguearse.
    - Se genera un token JWT tras el login.

2. **Gestión de Usuarios:**
    - Los usuarios pueden ver su propio perfil.
    - Los ADMIN pueden ver todos los usuarios.

3. **Gestión de Tareas:**
    - Los usuarios pueden **crear, ver, completar, actualizar y eliminar sus propias tareas**.
    - Los ADMIN pueden **ver, asignar, completar, actualizar y eliminar tareas de cualquier usuario**.

---

## Excepciones y Códigos de Estado

| Código | Descripción |
|--------|-------------|
| `200 OK` | Operación exitosa. |
| `201 Created` | Recurso creado exitosamente. |
| `400 Bad Request` | Datos inválidos o formato incorrecto. |
| `401 Unauthorized` | No autenticado o token inválido. |
| `403 Forbidden` | Acceso denegado por permisos insuficientes. |
| `404 Not Found` | Recurso no encontrado (usuario o tarea). |
| `500 Internal Server Error` | Error inesperado. |

---
