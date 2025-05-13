# 🌍 API REST de Países - Spring WebFlux + PostgreSQL

Esta es una API desarrollada con **Spring Boot WebFlux** y **R2DBC** para gestionar información de países. Permite realizar operaciones CRUD sobre un listado de países almacenados en una base de datos **PostgreSQL** de forma **reactiva y asincrónica**.

## 🚀 Tecnologías utilizadas

- **Java 17+**  
- **Spring Boot 3.x**  
- **Spring WebFlux** – Para desarrollo reactivo y no bloqueante.  
- **Spring Data R2DBC** – Para conectar y trabajar de forma reactiva con bases de datos relacionales.  
- **PostgreSQL** – Motor de base de datos relacional.  
- **R2DBC PostgreSQL Driver** – Driver específico para usar PostgreSQL de forma reactiva.  
- **Maven** – Herramienta de construcción y gestión de dependencias.

---

## 🗄️ Base de Datos

Se utiliza una base de datos PostgreSQL llamada **`Paises`**, y una tabla llamada **`paises`** con la siguiente estructura:

### Script SQL 

```sql
CREATE TABLE IF NOT EXISTS paises (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    continente VARCHAR(100),
    capital VARCHAR(100),
    idioma VARCHAR(100),
    codigo VARCHAR(10)
);

