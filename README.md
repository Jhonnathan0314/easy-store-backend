# Proyecto Spring Boot para Plataforma de Compras Online

Este proyecto es el backend de una plataforma de compras online diseñada como una API RESTful. Está construido con Spring Boot versión 3 y Java 17, proporcionando una base robusta y escalable para la gestión de operaciones de comercio electrónico.

## Descripción

La plataforma ofrece una amplia gama de funcionalidades centradas en facilitar la interacción entre el negocio de ventas y los clientes. A través de la API, se puede gestionar un catálogo de productos que incluye diversas categorías y subcategorías, adaptándose así a diferentes líneas de productos. Además, los clientes pueden navegar por este catálogo, añadir productos a sus carritos de compra y realizar pedidos.

Los usuarios disponen de múltiples medios de pago, lo que proporciona flexibilidad en las transacciones y mejora la experiencia de compra. Cada compra se registra cuidadosamente en un sistema de facturación que permite a los administradores del sistema llevar un seguimiento detallado de las ventas.

Las características clave de la API incluyen:

- **Gestión de Productos**: Permite a los administradores agregar, actualizar o eliminar productos, así como gestionar su información relacionada.
- **Categorías y Subcategorías**: Organiza los productos en categorías y subcategorías para una navegación intuitiva y eficiente.
- **Carrito de Compras**: Ofrece a los clientes la capacidad de guardar productos y gestionar su carrito antes de realizar la compra.
- **Gestión de Clientes**: Administra la información de los clientes, incluyendo datos personales y de contacto, para personalizar la experiencia de usuario.
- **Facturación**: Genera y mantiene registros de facturas para cada transacción completada en la plataforma.
- **Medios de Pago**: Soporta diferentes opciones de pago para brindar a los clientes varias alternativas al momento de pagar.

La seguridad de las transacciones y los datos de los usuarios es una prioridad, y la API utiliza Spring Security y JWT (JSON Web Tokens) para garantizar que todas las operaciones se realicen de manera segura.

## Estructura de paquetes del proyecto

```
com.easy.store.backend
│
├── config
│   └── S3Config.java  # Configuración de conexión a S3
│
├── utils
│   ├── constants
│   ├── exceptions
│   ├── http
│   ├── mappers
│   └── message
│
├── security
│   ├── config         # Configuración de headers, CORS, etc.
│   ├── controller     # Endpoints de autenticación
│   ├── jwt            # Generación y filtrado de JWT
│   ├── models         # Modelos de seguridad
│   └── service        # Implementación de login y register
│
└── context
    ├── codes
    ├── roles
    ├── account
    ├── user
    ├── account_has_user
    ├── category
    ├── subcategory
    ├── payment_type
    ├── product
    ├── purchase
    ├── purchase_has_product
    ├── email
    │   ├── service
    │   └── controller
    └── s3
    │   ├── model
    │   ├── service
    │   └── controller
    
A excepción de email y s3, cada contexto contiene la siguiente estructura:
    ├── application
    │   ├── usecase
    │   └── dto
    ├── domain
    │   ├── model
    │   └── ports
    ├── infrastructure
    │   ├── adapter
    │   ├── mappers
    │   └── persistence
    └── presentation
        └── controller
```

## Documentación API

Para más detalles sobre cómo consumir la API y utilizar sus diferentes endpoints, refiérase a la documentación de la API proporcionada a continuación:

[Documentación API](https://documenter.getpostman.com/view/21994524/2s9YRGyUzK)

La documentación incluye ejemplos de solicitudes y respuestas para cada uno de los endpoints, junto con descripciones de los parámetros y los códigos de estado HTTP correspondientes.

## Librerías Utilizadas

- `spring-boot-starter-data-jpa`: Para la integración de Spring Data JPA.
- `spring-boot-devtools`: Para el desarrollo eficiente de aplicaciones.
- `spring-boot-starter-security`: Para las capacidades de seguridad de Spring Boot.
- `snakeyaml`: Para el procesamiento de archivos YAML.
- `mysql-connector-java`: Para conectar con la base de datos MySQL.
- `lombok`: Para minimizar el código repetitivo de Java.
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: Para la creación y validación de JWTs.

## Requisitos Previos

Para ejecutar este proyecto es necesario contar con:

- Java 17 instalado.
- MySQL instalado localmente.

## Configuración de la Base de Datos

Si no cuenta con una base de datos local, establezca la propiedad 
```properties
spring.jpa.hibernate.ddl-auto=create
```

en el `application.properties`. Además, añada las siguientes líneas en su archivo `data.sql`:

```sql
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (1, 'client', 'active', NOW(), NOW());
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (2, 'owner', 'active', NOW(), NOW());
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (3, 'admin', 'active', NOW(), NOW());
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (4, 'ghost', 'active', NOW(), NOW());
```

Esto creará automáticamente las tablas necesarias y los roles iniciales en la base de datos.

## Variables de Entorno

Para la ejecución del proyecto, asegúrese de establecer las siguientes variables de entorno:

- `EASY_STORE_DB_USER`: Usuario de la base de datos.
- `EASY_STORE_DB_PASSWORD`: Contraseña de la base de datos.
- `EASY_STORE_DB_HOST`: Host de la base de datos.
- `EASY_STORE_DB_PORT`: Puerto de la base de datos.
- `EASY_STORE_DB_NAME`: Nombre de la base de datos.
- `SECRET_JWT_KEY`: Clave secreta para JWT.
- `EASY_STORE_AWS_S3_ACCESS_KEY`: Access key aws con permisos sobre el bucket s3 que almacena las imagenes.
- `EASY_STORE_AWS_S3_SECRET_ACCESS_KEY`: Secret access key aws.
- `EASY_STORE_AWS_S3_EASY_STORE`: Nombre del bucket en que se almacenan las imagenes.
- `EASY_STORE_GHOST_USER`: Username del usuario creado por defecto para usuarios sin cuenta.
- `EASY_STORE_GHOST_PASSWORD`: Password del usuario creado por defecto para usuarios sin cuenta.
- `EMAIL_APP_USERNAME`: Email configurado para enviar emails.
- `EMAIL_APP_PASSWORD`: Contraseña de aplicación del correo configurado.

## Cómo ejecutar el proyecto

Para ejecutar el proyecto, use el siguiente comando:

```bash
./mvnw spring-boot:run
```

O si tiene Maven instalado de forma independiente:

```bash
mvn spring-boot:run
```

