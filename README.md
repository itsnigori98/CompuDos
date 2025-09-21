# 📌 API AdminController - Fitness App

Este documento describe todos los endpoints disponibles en el controlador `AdminController` (`/api/admin`) de la aplicación Fitness.

localhost: `http://localhost:8080`

---

## 🔍 Endpoints de Ejercicios (Consultas - Taller)

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/admin/ejercicio1` | Usuarios con rol `ROLE_USER`. |
| GET | `/api/admin/ejercicio2` | Rutinas del usuario con `id=1`. |
| GET | `/api/admin/ejercicio3` | Último progreso del usuario con `id=2` (ordenado por fecha desc). |
| GET | `/api/admin/ejercicio4` | Ejercicios cuyo nombre contiene `"press"`. |
| GET | `/api/admin/ejercicio5` | Notificaciones del usuario con `id=1`. |
| GET | `/api/admin/ejercicio6` | Próximos eventos desde la fecha actual. |
| GET | `/api/admin/ejercicio7` | Eventos en los que participa el usuario con `id=2`. |
| GET | `/api/admin/ejercicio8` | Usuarios en rango de edad `18..30`. |
| GET | `/api/admin/ejercicio9` | Trainers cuya especialidad contiene `"Bici"`. |
| GET | `/api/admin/ejercicio10` | Eventos ligados a la notificación con `id=1`. |

---

## 👤 Gestión de Usuarios

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/admin/users` | Lista todos los usuarios. |
| GET | `/api/admin/users/{id}` | Obtiene un usuario por ID. |
| POST | `/api/admin/users` | Crea un nuevo usuario (JSON: `name`, `email`, `password`, `roles`). |
| PUT | `/api/admin/users/{id}` | Actualiza información básica de un usuario. |
| PUT | `/api/admin/users/{id}/roles` | Establece los roles de un usuario. |
| POST | `/api/admin/users/{id}/roles/{roleName}` | Agrega un rol a un usuario. |
| DELETE | `/api/admin/users/{id}/roles/{roleName}` | Elimina un rol de un usuario. |
| DELETE | `/api/admin/users/{id}` | Elimina un usuario. |

---

## 🛡️ Gestión de Roles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/admin/roles` | Lista todos los roles. |
| GET | `/api/admin/roles/{name}` | Obtiene un rol por nombre. |
| POST | `/api/admin/roles` | Crea un nuevo rol (JSON: `name`, `permissions`). |
| PUT | `/api/admin/roles/{name}/permissions` | Establece los permisos de un rol. |
| POST | `/api/admin/roles/{name}/permissions/{permissionName}` | Agrega un permiso a un rol. |
| DELETE | `/api/admin/roles/{name}/permissions/{permissionName}` | Elimina un permiso de un rol. |
| DELETE | `/api/admin/roles/{name}` | Elimina un rol. |

---

## 🔑 Gestión de Permisos

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/admin/permissions` | Crea un permiso nuevo. |
| GET | `/api/admin/permissions` | Lista todos los permisos. |
| GET | `/api/admin/permissions/{name}` | Obtiene un permiso por nombre. |
| DELETE | `/api/admin/permissions/{name}` | Elimina un permiso. |

---

## 🏋️‍♂️ Gestión de Ejercicios (Catálogo)

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/admin/exercises` | Crea un nuevo ejercicio. |
| PUT | `/api/admin/exercises/{id}` | Actualiza un ejercicio existente. |
| DELETE | `/api/admin/exercises/{id}` | Elimina un ejercicio. |

---

## 📅 Gestión de Eventos (Catálogo)

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/admin/events` | Crea un nuevo evento. |
| PUT | `/api/admin/events/{id}` | Actualiza un evento existente. |
| DELETE | `/api/admin/events/{id}` | Elimina un evento. |

---

## ⚙️ Notas
- Todos los endpoints están bajo el prefijo `/api/admin`.
- El formato de las fechas en eventos debe ser **ISO-8601** (`yyyy-MM-dd'T'HH:mm:ss`).
- Se usan respuestas estándar HTTP (`200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `404 Not Found`).

---


# Ejecución de JaCoCo

Debemos tener **JDK 21 de Java** para ejecutarlo.  
Luego de eso, asegúrate de que los **tests estén corridos**.

En IntelliJ:
1. Abre la pestaña de **Maven** en la barra derecha.
2. Selecciona tu proyecto (ejemplo: `mSpringBoot1`).
3. Dentro de **Lifecycle**, da clic en **test** para ejecutar las pruebas.

Después, en la terminal de IntelliJ ejecuta el siguiente comando para correr JaCoCo con los tests:

```bash
** mvn clean verify **



