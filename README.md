# API de Gestión de Fondos - Prueba Técnica BTG Pactual

Esta es la implementación de la prueba técnica para el rol de Ingeniero de Desarrollo Back End. La aplicación es una API REST construida con Java y Spring Boot, diseñada para una arquitectura serverless en AWS.

## 🚀 Arquitectura
La solución utiliza una arquitectura serverless nativa de la nube para garantizar la escalabilidad, el bajo costo y un mantenimiento reducido.

`Cliente (Web/Móvil) -> Amazon API Gateway -> AWS Lambda (Spring Boot App) -> Amazon DynamoDB`

- **API Gateway**: Actúa como el punto de entrada seguro para todas las peticiones HTTP.
- **AWS Lambda**: Contiene la lógica de negocio de la aplicación Spring Boot.
- **Amazon DynamoDB**: Base de datos NoSQL gestionada para la persistencia de datos.

## 💻 Tecnologías Utilizadas
- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3
- **Base de Datos**: Amazon DynamoDB (con AWS SDK v2 para Java)
- **Gestión de Dependencias**: Maven
- **Pruebas**: JUnit 5 y Mockito
- **Despliegue**: AWS CloudFormation

## ⚙️ Cómo Ejecutar Localmente

### Prerrequisitos
1.  Java 17 o superior instalado.
2.  DynamoDB Local funcionando en el puerto 8000 (ya sea con el JAR o con Docker).

### Ejecución
1.  Clona este repositorio.
2.  Abre el proyecto en tu IDE preferido (Spring Tool Suite, IntelliJ IDEA).
3.  Ejecuta la clase principal `FundsApiApplication.java`.
4.  La aplicación se iniciará en `http://localhost:8080`.

## 🌐 Estructura de la API REST

| Método | URL | Descripción |
| :--- | :--- | :--- |
| `POST` | `/clients/{clientId}/subscriptions` | Suscribe al cliente a un nuevo fondo. |
| `DELETE` | `/clients/{clientId}/subscriptions/{fundId}` | Cancela la suscripción de un cliente a un fondo. |
| `GET` | `/clients/{clientId}/transactions` | Obtiene el historial de transacciones del cliente. |

## ☁️ Proceso de Despliegue con AWS CloudFormation
La infraestructura completa se define como código en el archivo `template.yaml`.

1.  **Empaquetar la aplicación**:
    ```bash
    mvn clean package
    ```
2.  **Subir el artefacto**: Sube el archivo `target/funds-api-0.0.1-SNAPSHOT.jar` a un bucket de S3 previamente creado.

3.  **Desplegar el Stack**: Ejecuta el siguiente comando de la AWS CLI, reemplazando los placeholders con tus valores.
    ```bash
    aws cloudformation deploy \
      --template-file template.yaml \
      --stack-name btg-funds-api \
      --parameter-overrides CodeBucketName=NOMBRE_DE_TU_BUCKET CodeObjectKey=NOMBRE_DE_TU_JAR \
      --capabilities CAPABILITY_IAM
    ```

## 🛡️ Seguridad
La estrategia de seguridad se detalla en el archivo `SECURITY.md`.