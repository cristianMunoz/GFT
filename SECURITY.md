# Estrategia de Seguridad de la API de Fondos

Este documento define los procesos de autenticación, autorización, perfilamiento por roles y encriptación para la API, cumpliendo con los requisitos de la prueba técnica.

## 1. Autenticación (¿Quién eres?)
El proceso para verificar la identidad de un usuario se delega al servicio **AWS Cognito**.

* **Flujo**: El cliente se autentica contra un *User Pool* de Cognito con sus credenciales.
* **Resultado**: Si la autenticación es exitosa, Cognito emite un **JSON Web Token (JWT)**. Este token es la credencial digital que el cliente usará para todas las peticiones futuras a la API.

## 2. Autorización (¿Qué puedes hacer?)
La autorización se implementa en dos capas para máxima seguridad.

* **Capa 1 (Perímetro - API Gateway)**: Cada petición a la API debe incluir el JWT en la cabecera `Authorization`. **Amazon API Gateway** se configura con un "Autorizador de Cognito" que valida automáticamente la firma y la fecha de expiración del token. Si el token es inválido, la petición es rechazada antes de llegar a la aplicación.
* **Capa 2 (Aplicación - Spring Boot)**: Dentro de la aplicación, se realizan verificaciones a nivel de lógica de negocio. La más importante es validar que el `clientId` presente en la URL de la petición coincida con el `clientId` contenido dentro del token JWT. Esto previene que un usuario pueda realizar acciones sobre las cuentas de otros.

## 3. Perfilamiento por Roles
El sistema está diseñado para soportar múltiples roles (perfiles) mediante **Grupos de Cognito**.

* **Definición**: Se pueden crear grupos como `CLIENTS_GROUP` y `ADMINS_GROUP` en Cognito.
* **Aplicación**: Los roles del usuario se incluyen en el JWT. El backend, usando Spring Security, puede restringir el acceso a endpoints específicos basándose en estos roles (por ejemplo, un endpoint `/admin` solo sería accesible para usuarios del `ADMINS_GROUP`).

## 4. Encriptación
La protección de los datos se garantiza de extremo a extremo.

* **Encriptación en Tránsito**: Asegurada por defecto mediante el uso de **HTTPS (TLS)** en Amazon API Gateway. Toda la comunicación entre el cliente y el servidor viaja cifrada.
* **Encriptación en Reposo**: Garantizada por defecto por **Amazon DynamoDB**, que encripta toda la información almacenada en disco de manera automática, protegiendo los datos aunque alguien obtuviera acceso físico a la infraestructura.