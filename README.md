# UserService

## Descripción

El **UserService** es uno de los microservicios fundamentales dentro del ecosistema de microservicios del proyecto. Se encarga de la gestión de usuarios, incluyendo registro, autenticación, roles y permisos. Este microservicio está diseñado para ser altamente escalable y eficiente, utilizando el stack de Spring Boot con programación reactiva.

Este microservicio está registrado y se comunica con otros a través de **Eureka Server** y utiliza un **Gateway Reactivo** como puerta de enlace.

## Arquitectura

El **UserService** es parte de una arquitectura de microservicios compuesta por los siguientes componentes:

- **[Gateway Service](https://github.com/DanielRodado/GatewayService-ToDoList)**: Actúa como punto de entrada a todo el sistema y enruta las solicitudes a los diferentes microservicios.
- **[Eureka Server](https://github.com/DanielRodado/EurekaServer-ToDoList)**: Registro y descubrimiento de servicios.
- **[Task Service](https://github.com/DanielRodado/TaskService-ToDoList)**: Servicio encargado de la gestión de tareas.
  
El **UserService** implementa la arquitectura reactiva utilizando **Spring WebFlux** para manejar grandes cantidades de tráfico de manera eficiente.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Reactive Web**: Módulo de Spring que permite la programación reactiva para manejar solicitudes y respuestas de manera no bloqueante.
- **Spring Data R2DBC**: Proporciona soporte para el acceso a bases de datos reactivas en tiempo real usando R2DBC.
- **Spring Cloud Netflix Eureka Client**: Proporciona el soporte necesario para que un microservicio se registre y descubra otros servicios en un servidor Eureka.
- **PostgreSQL**: Sistema de gestión de bases de datos relacional usado para almacenar los datos de los usuarios.
- **Spring Security**: Autenticación y autorización.
- **JWT (JSON Web Tokens)**: Mecanismo para autenticar a los usuarios de manera segura.
- **Postman**: Herramienta utilizada para probar y verificar las APIs del microservicio.

## Endpoints

El **UserService** expone los siguientes endpoints bajo el prefijo `/api/users`:

### 1. **Registro de Usuario**
   - **Endpoint**: `/api/users/register`
   - **Método**: `POST`
   - **Descripción**: Permite registrar un nuevo usuario en el sistema.
   - **Request Body**:
     ```json
     {
       "name": "string",
       "username": "string",
       "email": "string",
       "password": "string"
     }
     ```
   - **Response**:
     ```json
     {
       "id": "int",
       "username": "string",
       "email": "string",
     }
     ```

### 2. **Inicio de Sesión**
   - **Endpoint**: `/api/users/login`
   - **Método**: `POST`
   - **Descripción**: Autentica a un usuario en el sistema, devolviendo un JWT para ser usado en futuras solicitudes.
   - **Request Body**:
     ```json
     {
       "username": "string",
       "password": "string"
     }
     ```
   - **Response**:
     ```json
     {
       "token": "string"
     }
     ```

### 3. **Obtener Detalles del Usuario**
   - **Endpoint**: `/api/users/{userId}`
   - **Método**: `GET`
   - **Descripción**: Obtiene la información de un usuario en base a su `userId`.
   - **Response**:
     ```json
     {
       "id": "int",
       "username": "string",
       "email": "string",
     }
     ```

### 4. **Actualizar Usuario**
   - **Endpoint**: `/api/users/{userId}`
   - **Método**: `PUT`
   - **Descripción**: Actualiza la información de un usuario específico.
   - **Request Body**:
     ```json
     {
       "name": "string",
       "username": "string",
       "email": "string",
       "password": "string"
     }
     ```
   - **Response**:
     ```json
     {
       "id": "string",
       "name": "string",
       "username": "string",
       "email": "string",
     }
     ```

### 5. **Eliminar Usuario**
   - **Endpoint**: `/api/users/{userId}`
   - **Método**: `DELETE`
   - **Descripción**: Elimina a un usuario en base a su `userId`.

## Seguridad

El `UserService` implementa **Spring Security** para manejar la autenticación de los usuarios. Este microservicio .

Aunque la autenticación se gestiona dentro del `UserService`, los filtros de seguridad y otras políticas de seguridad más amplias, como el enrutamiento y la autorización, están centralizados en el **[Gateway Service](https://github.com/DanielRodado/GatewayService-ToDoList)** que utiliza **JWT** para autenticar a los usuarios. Cada solicitud al `GatewayService` debe incluir un token JWT válido en el encabezado `Authorization` en el formato `Bearer <token>`. El Gateway es responsable de aplicar estas políticas de seguridad antes de dirigir las solicitudes al `UserService`.

Para más detalles sobre la implementación de la autenticación en el `UserService`, consulta la configuración de seguridad en el archivo `SecurityConfig` dentro del microservicio.

- **Roles disponibles**: `USER`, `ADMIN`
- **Rutas protegidas**: Solo los usuarios con el rol `ADMIN` pueden realizar operaciones como: Obtener, eliminar o actualizar datos de otros usuarios.
  
## Persistencia

El **UserService** utiliza **PostgreSQL** como base de datos. A continuación, se describe la estructura de la entidad `User`:

```json
{
  "id": "string",
  "name": "string",
  "username": "string",
  "email": "string",
  "password": "string",
  "isAdmin": "boolean"
}
````
