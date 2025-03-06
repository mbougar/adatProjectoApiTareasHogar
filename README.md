# API REST - Gestión de Tareas del Hogar

Se puede descargar el video que muestra el funcionamiento de la api en una aplicación de android aqui: https://drive.google.com/file/d/1P7rnOt3tTyvKDs65awpbULc0tkjDQkV6/view


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

## Pruebas Gestión Usuarios

### Configuración de la conexión a Mongo

Se ha usado una variable de entorno para configurar la URI de MongoDB, además se han añadido las clases necesarias para gestionar la conexión a la base de datos usando Spring (Controller, Service y Repository)

![ConexionMongo.png](src/main/resources/screenshots/ConexionMongo.png)

### Implementación de los endpoints relativos a realizar login y registro

Se han implementado los endpoints necesarios para realizar un login y registro asi como las posibles excepciones que estos endpoints pueden levantar.

 - `POST` `/usuarios/login`: Las posibles respuestas son 201 created, 401 unathorized o 500 internal server error.
 - `POST` `/usuarios/register`: Las posibles respuestas son 201 created, 401 unathorized o 500 internal server error.

El proceso de login da como resultado un token JWT que permite al usuario autorizarse en futuras peticiones.

![EndpointLogin.png](src/main/resources/screenshots/EndpointLogin.png)

### Pruebas de correcto funcionamiento de la gestión de usuarios de la API

#### Login

 - 201 Created: El usuario ha podido iniciar sesion correctamente y devuelve el token.

Imagen Insomnia:

![loginInsomnia201.png](src/main/resources/screenshots/loginInsomnia201.png)

Imagen APP:

![loginApp201.png](src/main/resources/screenshots/loginApp201.png)

Imagen BBDD:

![loginDatabase201.png](src/main/resources/screenshots/loginDatabase201.png)


 - 401 Unauthorized: El usuario ha introducido credenciales incorrectas.

Imagen Insomnia:

![loginInsomnia401.png](src/main/resources/screenshots/loginInsomnia401.png)

Imagen APP:

![loginApp401.png](src/main/resources/screenshots/loginApp401.png)

- 500 Internal Server Error: Ha ocurrido un error insesperado o no se ha enviado un body (no es posible obtenerlo desde la app).

Imagen Insomnia:

![loginInsomnia500.png](src/main/resources/screenshots/loginInsomnia500.png)


#### Register

- 201 Created: El usuario ha podido crear un usuario correctamente.

Imagen Insomnia:

![registerInsomnia201.png](src/main/resources/screenshots/registerInsomnia201.png)

Imagen APP:

![registerApp201.png](src/main/resources/screenshots/registerApp201.png)

Imagen BBDD (la primera imagen muestra el usuario creado con insomnia y la segunda el usuario creado con la app):

![registerDatabase201.png](src/main/resources/screenshots/registerDatabase201.png)

![registerDatabase201_2.png](src/main/resources/screenshots/registerDatabase201_2.png)


- 400 Bad Request: El usuario ha introducido datos inválidos, esta excepción puede ser levantada por multiples razones (usuario repetido, correo repetido, municipio o provincia inválidos, contraseñas no iguales, campos vacíos o email inválido).

Imagen Insomnia:

![registerInsomnia400.png](src/main/resources/screenshots/registerInsomnia400.png)

![registerInsomnia400_2.png](src/main/resources/screenshots/registerInsomnia400_2.png)

Imagen APP (las contraseñas no coinciden):

![registerApp400.png](src/main/resources/screenshots/registerApp400.png)

- 500 Internal Server Error: Ha ocurrido un error insesperado o no se ha enviado un body (no es posible obtenerlo desde la app).

Imagen Insomnia:

![registerInsomnia500.png](src/main/resources/screenshots/registerInsomnia500.png)


---

# Pruebas Gestión Tareas

## Usuario con rol USER

### Ver todas SUS tareas

- 200 OK: Operación exitosa.

Imagen Insomnia:

![img.png](src/main/resources/screenshots/Part3/img.png)

Imagen APP:

![img_1.png](src/main/resources/screenshots/Part3/img_1.png)

- 401 Unauthorized: No autenticado o token inválido (No es posible de obtener en la app).

Imagen Insomnia:

![img_2.png](src/main/resources/screenshots/Part3/img_2.png)

---

### Marcar como hecha una tarea propia

- 200 OK: Operación exitosa.

Imagen Insomnia:

![img_3.png](src/main/resources/screenshots/Part3/img_3.png)

Imagen APP:

![img_4.png](src/main/resources/screenshots/Part3/img_4.png)

Imagen BBDD:

![img_5.png](src/main/resources/screenshots/Part3/img_5.png)

- 401 Unauthorized: No autenticado o token inválido.

Imagen Insomnia:

![img_6.png](src/main/resources/screenshots/Part3/img_6.png)

- 404 Not Found: No se encuentra la tarea con ese ID (solo obtenible en insomnia.

Imagen Insomnia:

![img_26.png](src/main/resources/screenshots/Part3/img_26.png)


---

### Eliminar una tarea propia


- 204 No Content: Operación exitosa y no devuelve nada.

Imagen Insomnia:

![img_7.png](src/main/resources/screenshots/Part3/img_7.png)

Imagen APP (La tarea 1 ya no existe porque se ha eliminado, también se podría hacer pulsando el icono de eliminar):

![img_8.png](src/main/resources/screenshots/Part3/img_8.png)

Imagen BBDD:

![img_9.png](src/main/resources/screenshots/Part3/img_9.png)


Imagen Insomnia:

![img_10.png](src/main/resources/screenshots/Part3/img_10.png)


- 403 Forbidden: Acceso denegado por permisos insuficientes.

Imagen Insomnia (Se ha intentado eliminar una tarea que no es del usuario, solo se puede hacer desde insomnia:

![img_11.png](src/main/resources/screenshots/Part3/img_11.png)

- 404 Not Found: No se encuentra la tarea con ese ID (solo obtenible en insomnia.

Imagen Insomnia:

![img_25.png](src/main/resources/screenshots/Part3/img_25.png)


---

### Darse de alta A SÍ MISMO una tarea


- 200 OK: Operación exitosa.

Imagen Insomnia:

![img_12.png](src/main/resources/screenshots/Part3/img_12.png)

Imagen APP:

![img_13.png](src/main/resources/screenshots/Part3/img_13.png) ![img_14.png](src/main/resources/screenshots/Part3/img_14.png)

Imagen BBDD:

![img_15.png](src/main/resources/screenshots/Part3/img_15.png)


- 400 Bad Request: Datos inválidos o formato incorrecto (En este caso, el titulo de la tarea debe ser único por usuario, podrían existir dos tareas con el mismo nombre, pero no asignadas al mismo usuario).

Imagen Insomnia:

![img_17.png](src/main/resources/screenshots/Part3/img_17.png)


- 401 Unauthorized: No autenticado o token inválido.

Imagen Insomnia:

![img_18.png](src/main/resources/screenshots/Part3/img_18.png)


- 403 Forbidden: Acceso denegado por permisos insuficientes (Solo se puede por Insomnia, en la APP simplemente no generaría la tarea).

Imagen Insomnia:

![img_16.png](src/main/resources/screenshots/Part3/img_16.png)


---

## Usuario con rol ADMIN


### Ver todas las tareas

- 200 OK: Operación exitosa.

Imagen Insomnia:

![img_19.png](src/main/resources/screenshots/Part3/img_19.png)

Imagen APP:

![img_20.png](src/main/resources/screenshots/Part3/img_20.png)

- 401 Unauthorized: Token inválido (solo podible si ponemos mal el token).

Imagen Insomnia:

![img_21.png](src/main/resources/screenshots/Part3/img_21.png)

---

### Eliminar cualquier tarea de cualquier usuario

- 204 No Content: Operación exitosa y no devuelve nada (En este caso eliminamos una tarea del usuario manuelBouza desde el admin).

Imagen Insomnia:

![img_22.png](src/main/resources/screenshots/Part3/img_22.png)

Imagen APP:

![img_23.png](src/main/resources/screenshots/Part3/img_23.png)

Imagen BBDD (ahora la bbdd tiene 4 entradas en vez de 3):

![img_24.png](src/main/resources/screenshots/Part3/img_24.png)

- 401 Unauthorized: Token inválido (solo podible si ponemos mal el token).

Imagen Insomnia:

![img_21.png](src/main/resources/screenshots/Part3/img_21.png)

- 404 Not Found: No se encuentra la tarea con ese ID (solo obtenible en insomnia.

Imagen Insomnia:

![img_25.png](src/main/resources/screenshots/Part3/img_25.png)


---

### Dar de alta tareas a cualquier usuario

- 200 OK: Operación exitosa.

Imagen Insomnia:

![img_28.png](src/main/resources/screenshots/Part3/img_28.png)

Imagen APP:

![img_29.png](src/main/resources/screenshots/Part3/img_29.png)

Imagen BBDD:

![img_30.png](src/main/resources/screenshots/Part3/img_30.png)


- 400 Bad Request: Datos inválidos o formato incorrecto (En este caso, el titulo de la tarea debe ser único por usuario, podrían existir dos tareas con el mismo nombre, pero no asignadas al mismo usuario).

Imagen Insomnia:

![img_27.png](src/main/resources/screenshots/Part3/img_27.png)


- 401 Unauthorized: Token inválido.

Imagen Insomnia:

![img_31.png](src/main/resources/screenshots/Part3/img_31.png)
