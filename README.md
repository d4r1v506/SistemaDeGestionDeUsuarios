# Microservicio de Gestion De Usuarios
## Descripción

Este microservicio forma parte del sistema de gestión de tareas desarrollado para la empresa XYZ. Su proposito es getionar la información de los usuarios.

Es un Backend en Java con Spring Boot para un sistema de gestión de usuarios. Este proyecto expone un conjunto de APIs RESTful para la gestión de usuarios, proporciona funcionalidades CRUD, y permite su integración con otros microservicios del sistema, como el servicio de tareas (task-service), a través de una arquitectura basada en microservicios y comunicación asíncrona.

## Características Principales
- Implementación de CRUD completo para la gestión de usuarios.
- Validación de datos de entrada para garantizar integridad y consistencia.
- Integración con el API Gateway y comunicación asíncrona con otros microservicios.
- Contenedor Docker para facilitar el despliegue y escalabilidad.

## Entidad Usuario
Los usuarios son personas que pueden ser asignadas a proyectos o tareas dentro del sistema. La estructura de la entidad usuario es la siguiente:
- **ID:** Identificador único.
- **Identifificación:** Número de indetificación.
- **Nombres:** Nombre(s) del usuario.
- **Apellidos:** Apellido(s) del usuarios.
- **Edad:** Edad del usuario.
- **Cargo:** Posición o rol del usuario en la empresa.
- **Estado:** Estado ACTIVO/INACTIVO a nivel de base de datos.

## Entidad Estado
el estado del usuario dentro del sistema puede ser Activo/Inactivo. La estructura de la entidad estado es la siguiente:
- **ID:** identificador único.
- **Nombre:** Descripción unico del estado ACTIVO/INACTIVO.

## Endpoints del Microservicio
**Obtener Token**
- **Método:** POST
- **URL:** /api/auth/getToken
- **RequestBody:**

{
    "username": "admin",
    "password": "admin123"
}

**Crear Usuario**
- **Método:** POST
- **URL:** /api/usuarios/
- **Authorization:** Token Bearer
- **Request Body:**

{
    "identificacion": "1719512392",
    "nombres": "Darius",
    "apellidos": "Falconi",
    "edad": "35",
    "cargo": "Desarrollador",
    "estado": "ACTIVO"
}

- **Response:**

{
    "state": "SUCCESS",
    "data": {
        "id": 1,
        "identificacion": "1719512392",
        "nombres": "Darius",
        "apellidos": "Falconi",
        "edad": 35,
        "cargo": "Desarrollador",
        "estado": {
            "id": 1,
            "estado": "ACTIVO"
        }
    },
    "message": [
        "Usuario creado exitosamente"
    ]
}

- **Codigos de Estado:**
    - 201: Creado.
    - 400: Datos inválidos
    - 300: Prohibido
    - 500: Usuario Duplicado

**Obtener Usuario por cédula**
- **Método:** GET
- **URL:** /api/usuarios/{identificacion}
- **Authorization:** Token Bearer
- **Response:**

{
    "state": "SUCCESS",
    "data": {
        "id": 1,
        "identificacion": "1719512392",
        "nombres": "Darius",
        "apellidos": "Falconi",
        "edad": 35,
        "cargo": "Desarrollador",
        "estado": {
            "id": 1,
            "estado": "ACTIVO"
        }
    },
    "message": [
        "Usuario encontrado."
    ]
}

- **Codigos de Estado:**
    - 200: OK.
    - 404: Usuario no encontrado
    - 300: Prohibido

**Actualizar Usuario**
- **Método:** PUT
- **URL:** /api/usuarios/{identificacion}
- **Authorization:** Token Bearer
- **Request Body:**

{
    "identificacion": "1719512392",
    "nombres": "Darius",
    "apellidos": "Falconi",
    "edad": "35",
    "cargo": "Desarrollador",
     "estado": {
            "id": 1,
            "estado": "inactivo"
        }
}

- **Response:**

{
    "state": "SUCCESS",
    "data": {
        "id": 3,
        "identificacion": "1719512392",
        "nombres": "Darius",
        "apellidos": "Falconi",
        "edad": 35,
        "cargo": "Desarrollador",
        "estado": {
            "id": 2,
            "estado": "INACTIVO"
        }
    },
    "message": [
        "Usuario actualizado exitosamente."
    ]
}

- **Codigos de Estado:**
    - 200: OK.
    - 500: Usuario no encontrado
    - 300: Prohibido

**Eliminar Usuario**

- **Método:** DELETE
- **URL:** /api/usuarios/{identificacion}
- **Authorization:** Token Bearer
- **Response:**

{
    "state": "SUCCESS",
    "data": null,
    "message": [
        "Usuario con ID: 1719512393 eliminado exitosamente"
    ]
}

- **Codigos de Estado:**
    - 200: OK.
    - 404: Usuario no encontrado
    - 300: Prohibido

## Validaciones de Datos
- Identificación: Logitud hasta 15 caracteres
- Estado: valores permitidos: "Activo", "Inactivo".


## Levantar el proyecto en Docker
El microservicio está preparado para ejecutarse en un contenedor Docker.

**Requisitos Previos**
- Docker Desktop instalado en el sistema
- Maven instalado para compilar el proyecto o el IDE Spring Tool.
- Java 8
- Git 

1. Abrir una terminal (cmd)
2. Clonar el proyecto con el comando:

    git clone https://github.com/d4r1v506/SistemaDeGestionDeUsuarios.git

3. Compilar el proyecto

    (opción 1)

   Si se tiene instalado Maven, ejecutar en una terminal dentro de la raiz del proyecto el comando:
   
   mvn clean install
   
    (opción 2)
    
    Abrir el proyecto con un IDE, de preferencia Spring tool.

   Dar clic derecho en la raiz del proyecto y seleccionar:
    - Run As - Maven Clean
    - Run As - Maven Install

4. Validar que se creo dentro del directorio target el archivo *gestor_usuarios.jar*

5. Crear el contenedor de la base postgreSQL "SI AUN NO ESTA CREADO", en la terminal escribimos el comando:

    docker run -d --name postgres_container -e POSTGRES_USER=postgres -e POSTGRES_PASWORD=postgres -p 5432:5432 postgres

    5.1. Ingresamos al contenedor de postgres con el comando:

    docker exec -it postgres_container psql -U postgres

    5.2. Una vez dentro del contenedor usamos el siguiente comando para visualizar las bases de datos: 
    
    \l

    5.3. Si no existe la base de datos *gestion_usuarios* ejecutamos los query del archivo: query.sql

    *Nota:* Para conectarse a la base usamos el comando: 
    
    \c gestion_usuarios

6. Construir la imagen del microservicio, ingresar a la raiz del proyecto desde la terminal y ejecutar el comando:

    docker build -t gestor-usuarios:1.0 .

    6.1. Ejecutar el contenedor con el comando:

    docker run -d -p 8082:8082 --name gestor-usuarios-container gestor-usuarios:1.0

7. verificar que el microservicio está funcionando ingresando en el navegador:

    http://localhost:8082/gestor/api/usuarios

8. Finalmente Abrir postman y ejecutar los endpoints del microservicio anteriormente descritos


