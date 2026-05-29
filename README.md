# 🎓 API Matrícula

Backend REST para el control de registros de matrícula de estudiantes en una academia.
Desarrollado con **Spring Boot 4** y **Java 25**, cumpliendo los requisitos del trabajo final e incorporando implementaciones adicionales de nivel profesional.

> **Repositorio:** https://github.com/robertomosquera-dev/API_MATRICULA

---

## 📋 Tabla de contenidos

- [Requisitos del trabajo cumplidos](#-requisitos-del-trabajo-cumplidos)
- [Implementaciones adicionales](#-implementaciones-adicionales)
- [Stack tecnológico](#%EF%B8%8F-stack-tecnológico)
- [Ejecución con Docker](#-ejecución-con-docker-recomendado)
- [Ejecución local](#-ejecución-local-sin-docker)
- [Endpoints](#-endpoints)

---

## ✅ Requisitos del trabajo cumplidos

| # | Requisito | Estado |
|---|---|---|
| 1 | CRUD completo de **Estudiantes** (id, nombres, apellidos, DNI, edad) | ✅ |
| 2 | CRUD completo de **Cursos** (id, nombre, siglas, estado) | ✅ |
| 3 | Registro de **Matrícula** con fecha, estudiante, detalle (curso + aula) y estado | ✅ |
| 4 | Listar estudiantes ordenados **descendente por edad** con programación funcional | ✅ |
| 5 | Relación cursos → estudiantes usando `Map<K, V>` con programación funcional | ✅ |
| 6 | **Control de excepciones globales** (`@RestControllerAdvice`) | ✅ |
| 7 | **Validación de requests** con `@Valid` | ✅ |
| 8 | Base de datos **PostgreSQL** | ✅ |

---

## 🚀 Implementaciones adicionales

### 🏛️ Arquitectura Hexagonal (Ports & Adapters)

Se aplicó arquitectura hexagonal para desacoplar completamente la lógica de negocio de los detalles de infraestructura. La lógica central no depende de Spring, JPA ni Redis — solo de interfaces (puertos).

```
domain/          →  Entidades y reglas de negocio puras
application/     →  Casos de uso (puertos de entrada / servicios)
infrastructure/  →  Adaptadores: controllers REST, repositorios JPA, config Redis
```

---

### ⚡ Caché con Redis

Se integró Redis como capa de caché para mejorar el rendimiento en consultas frecuentes. Usa `spring-boot-starter-cache` + `spring-boot-starter-session-data-redis`.

---

### 📄 Documentación con Swagger / OpenAPI 3

La API está documentada con **Springdoc OpenAPI 3**. Una vez levantada la aplicación, la UI interactiva está disponible en:

| Modo | URL de Swagger |
|---|---|
| Local | `http://localhost:8080/swagger-ui.html` |
| Docker | `http://localhost:8090/swagger-ui.html` |

---

### 🗺️ MapStruct + Lombok

Se usa **MapStruct** para mapeo automático entre entidades JPA y DTOs, combinado con **Lombok** para eliminar boilerplate (getters, setters, constructores).

---

### 🐳 Contenerización con Docker

La aplicación incluye `Dockerfile` y `docker-compose.yaml`. El compose levanta los tres servicios de manera orquestada, con `healthcheck` en PostgreSQL para garantizar que la app arranque solo cuando la base de datos está lista.

---

## 🛠️ Stack tecnológico

| Tecnología | Versión | Rol |
|---|---|---|
| Java | 25 | Lenguaje principal |
| Spring Boot | 4.0.6 | Framework |
| PostgreSQL | 16-alpine | Base de datos relacional |
| Redis | 8-alpine | Caché |
| MapStruct | 1.6.3 | Mapeo de DTOs |
| Lombok | 1.18.38 | Reducción de boilerplate |
| Springdoc OpenAPI | 3.0.2 | Documentación Swagger |
| Docker / Compose | — | Contenerización |

---

## 🐳 Ejecución con Docker (recomendado)

Este modo levanta automáticamente **PostgreSQL**, **Redis** y la **aplicación** en contenedores. No es necesario tener nada instalado localmente salvo Docker.

### Requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y corriendo

### Paso 1 — Clonar el repositorio

```bash
git clone https://github.com/robertomosquera-dev/API_MATRICULA.git
cd API_MATRICULA
```

### Paso 2 — Crear el archivo `.env`

En la raíz del proyecto crea un archivo llamado `.env` con el siguiente contenido:

```env
# Base de datos
BD_USER=postgres
BD_PASS=postgres123
BD_NAME=tuition_db
BD_URL=jdbc:postgresql://db:5432/tuition_db

# Redis
REDIS_HOST=redis-cache
REDIS_PORT=6379
REDIS_PASS=redis123
```

> ⚠️ El valor `db` en `BD_URL` hace referencia al **nombre del servicio** de PostgreSQL dentro de la red Docker. No lo cambies al usar compose.

### Paso 3 — Levantar los servicios

```bash
docker compose up --build
```

Docker construirá la imagen de la app y levantará los tres servicios en orden:

```
tuition-db      → PostgreSQL en puerto 5433 (externo) / 5432 (interno)
tuition-redis   → Redis     en puerto 6370 (externo) / 6379 (interno)
tuition-app     → API REST  en puerto 8090 (externo) / 8080 (interno)
```

### Paso 4 — Verificar

```
API:     http://localhost:8090/api
Swagger: http://localhost:8090/swagger-ui.html
```

### Detener los servicios

```bash
docker compose down
```

Para eliminar también los volúmenes (datos de la BD y Redis):

```bash
docker compose down -v
```

---

## 💻 Ejecución local (sin Docker)

Este modo ejecuta la aplicación directamente en tu máquina. Necesitas tener PostgreSQL y Redis instalados y corriendo localmente.

### Requisitos

- Java 25+
- Maven 3.9+
- PostgreSQL corriendo en `localhost:5432`
- Redis corriendo en `localhost:6379`

### Paso 1 — Clonar el repositorio

```bash
git clone https://github.com/robertomosquera-dev/API_MATRICULA.git
cd API_MATRICULA
```

### Paso 2 — Crear la base de datos

> ⚠️ **Importante:** La base de datos debe existir antes de levantar la aplicación. Spring Boot creará las tablas automáticamente, pero el esquema (base de datos) debe estar creado manualmente.

```bash
# Conectarte a PostgreSQL y crear la base de datos
psql -U postgres -c "CREATE DATABASE tuition_db;"
```

O usando pgAdmin / DBeaver: crear una base de datos llamada `tuition_db`.

### Paso 3 — Configurar las variables de entorno

Crea el archivo `.env` en la raíz del proyecto con los valores apuntando a `localhost`:

```env
# Base de datos (apunta a tu PostgreSQL local)
BD_USER=postgres
BD_PASS=tu_password
BD_NAME=tuition_db
BD_URL=jdbc:postgresql://localhost:5432/tuition_db

# Redis (apunta a tu Redis local)
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASS=tu_redis_password
```

> ⚠️ A diferencia del modo Docker, aquí `BD_URL` usa `localhost` en vez de `db`, y `REDIS_HOST` usa `localhost` en vez de `redis-cache`.

### Paso 4 — Compilar y ejecutar

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

### Paso 5 — Verificar

```
API:     http://localhost:8080/api
Swagger: http://localhost:8080/swagger-ui.html
```

---

## 🔗 Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/estudiantes` | Listar estudiantes ordenados desc. por edad |
| `GET` | `/api/estudiantes/{id}` | Obtener estudiante por ID |
| `POST` | `/api/estudiantes` | Crear estudiante |
| `PUT` | `/api/estudiantes/{id}` | Actualizar estudiante |
| `DELETE` | `/api/estudiantes/{id}` | Eliminar estudiante |
| `GET` | `/api/cursos` | Listar cursos |
| `GET` | `/api/cursos/{id}` | Obtener curso por ID |
| `POST` | `/api/cursos` | Crear curso |
| `PUT` | `/api/cursos/{id}` | Actualizar curso |
| `DELETE` | `/api/cursos/{id}` | Eliminar curso |
| `POST` | `/api/matriculas` | Registrar matrícula |
| `GET` | `/api/matriculas/por-curso` | Cursos con sus estudiantes matriculados (`Map<K,V>`) |

> La documentación completa con ejemplos de request/response está en **Swagger UI**.

---

## 📂 Estructura del proyecto

```
src/
└── main/
    └── java/
        └── org/mt/ev/
            ├── domain/           # Entidades y contratos (puertos)
            ├── application/      # Casos de uso / servicios
            └── infrastructure/   # Controllers, repositorios JPA, config Redis/Swagger
```

---

