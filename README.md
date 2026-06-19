# PerfulandiaSpa

**API RESTful estructurada en microservicios para el control de ventas e inventario de Perfulandia SPA.**

**Asignatura:** DSY1103 - Desarrollo Fullstack 1
**Sección:** 008D
**Equipo de Desarrollo:** Armando Rosselot - Fabián Mondaca - Dominga Ruiz

---

## 1. Contexto del Proyecto
Perfulandia SPA es una empresa chilena de ecommerce emergente, la empresa comenzó con una sucursal en el Barrio Meiggs en Santiago, pero su éxito en ventas ha llevado a la apertura de nuevas sucursales en Concepción y Viña del Mar. La empresa ahora planea continuar su expansión debido a su crecimiento exponencial a nivel nacional. Sin embargo, este rápido crecimiento ha revelado las limitaciones de su actual sistema de software monolítico. El sistema ha comenzado a fallar, presentando problemas de rendimiento y disponibilidad que ponen en riesgo las operaciones de la base de datos y la consistencia de la información.
Esta solución implementa una arquitectura distribuida basada en microservicios independientes, donde cada dominio del negocio (productos, categorías, proveedores, inventario, cupones, órdenes, devoluciones, sucursales, clientes, reseñas, tickets de soporte y usuarios) es gestionado por su propio servicio, con su propia base de datos (Database per Service), comunicación REST entre servicios, autenticación JWT y documentación mediante Swagger/OpenAPI.

La arquitectura centraliza el acceso a todos los servicios a través de un **API Gateway** y utiliza **Eureka Server** para el registro y descubrimiento dinámico de los microservicios.

---

## Microservicios Implementados

| # | Microservicio | Puerto | Base de datos |
|---|---|---|---|
| 1 | ms-eureka | 8761 | — |
| 2 | ms-gateway | 8080 | — |
| 3 | ms-auth | 8082 | ms-user |
| 4 | ms-cliente | 8083 | ms-cliente |
| 5 | ms-cupon-descuento | 8084 | ms-cupon-descuento |
| 6 | ms-devolucion | 8085 | ms-devolucion |
| 7 | ms-inventario | 8086 | ms-inventario |
| 8 | ms-orden | 8087 | ms-orden |
| 9 | ms-producto | 8088 | ms-producto |
| 10 | ms-proveedor | 8089 | ms-proveedor |
| 11 | ms-resena | 8090 | ms-resena |
| 12 | ms-sucursal | 8091 | ms-sucursal |
| 13 | ms-ticket-soporte | 8092 | ms-ticket-soporte |
| 14 | ms-usuario | 8093 | ms-usuario |
| 15 | ms-categoria | 8094 | ms-categoria |

---

## Rutas Principales del Gateway

Todas las solicitudes pasan por `http://localhost:8080` y se enrutan automáticamente vía Eureka:

| Ruta | Microservicio destino |
|---|---|
| `/auth/**` | ms-auth |
| `/api/v2/categorias/**` | ms-categoria |
| `/api/v2/productos/**` | ms-producto |
| `/api/v2/proveedores/**` | ms-proveedor |
| `/api/v2/inventarios/**` | ms-inventario |
| `/api/v2/cupones/**` | ms-cupon-descuento |
| `/api/v2/devoluciones/**` | ms-devolucion |
| `/api/v2/ordenes/**` | ms-orden |
| `/api/v2/sucursales/**` | ms-sucursal |
| `/api/v2/cliente/**` | ms-cliente |
| `/api/v2/resena/**` | ms-resena |
| `/api/v2/ticket-soporte/**` | ms-ticket-soporte |
| `/api/v2/usuario/**` | ms-usuario |

---



## Stack Tecnológico utilizado para el desarrollo del proyecto:

Spring Boot · MySQL · Maven · Docker · Eureka · Spring Cloud Gateway · JUnit · Mockito · Postman · Swagger/OpenAPI
