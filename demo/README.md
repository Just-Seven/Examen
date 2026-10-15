# API de Huertos Urbanos

API REST para registrar y consultar parcelas monitoreadas. Usa Spring Boot, PostgreSQL y Docker Compose.

## Requisitos

- Docker Desktop con Docker Compose habilitado.
- Postman.

## Iniciar la aplicación

Abre PowerShell en la carpeta `demo` del proyecto y ejecuta:

```powershell
docker compose up --build
```

La primera ejecución puede tardar mientras Docker descarga las imágenes y construye la API. Espera a que el servicio `app` termine de iniciar. La API queda disponible en `http://localhost:8080` y PostgreSQL en el puerto `5432`.

Compose crea la base `huertos` al iniciar PostgreSQL por primera vez y ejecuta `src/main/resources/db/init/01-schema.sql` para crear la tabla `parcels`. También puedes crear la base y la tabla en una instalación local de PostgreSQL con `psql -U postgres -f database.sql` desde esta carpeta. Ese comando requiere permisos para crear bases de datos.

Para detener los contenedores, presiona `Ctrl+C` y ejecuta:

```powershell
docker compose down
```

Los datos se guardan en el volumen `postgres_data`, por lo que se conservan al detener y volver a iniciar los contenedores. Para borrar también esos datos de prueba, ejecuta `docker compose down -v`.

## Probar en Postman

Usa `http://localhost:8080` como URL base. Para las solicitudes con JSON, selecciona **Body → raw → JSON** en Postman y establece `Content-Type: application/json`.

### 1. Registrar una parcela

- Método: `POST`
- URL: `http://localhost:8080/api/v1/parcels`
- Body:

```json
{
  "name": "Huerto Central",
  "location": {
    "latitude": 19.4326,
    "longitude": -99.1332
  },
  "soilType": "LIMOSO",
  "ownerOrLead": "Comunidad del Centro"
}
```

Una creación correcta devuelve **201 Created** y la parcela guardada, incluido su `id` y `createdAt`. Copia el `id` para usarlo en la consulta individual.

Valores aceptados para `soilType`: `ARCILLOSO`, `LIMOSO` y `ARENOSO`.

### 2. Listar parcelas

- Método: `GET`
- URL: `http://localhost:8080/api/v1/parcels`

Devuelve **200 OK** y un arreglo JSON con las parcelas registradas. Si aún no se ha creado ninguna, devuelve `[]`.

### 3. Consultar una parcela

- Método: `GET`
- URL: `http://localhost:8080/api/v1/parcels/1`

Reemplaza `1` por el `id` devuelto al crear la parcela. Devuelve **200 OK** y el detalle, o **404 Not Found** si no existe ese ID.

## Comprobar errores de validación

Envía de nuevo el `POST` con algún dato incorrecto. Por ejemplo, una latitud mayor que 90:

```json
{
  "name": "Huerto de prueba",
  "location": {
    "latitude": 120,
    "longitude": -99.1332
  },
  "soilType": "LIMOSO",
  "ownerOrLead": "Comunidad"
}
```

La respuesta será **400 Bad Request**, con un mensaje general y un objeto `details` que identifica el campo inválido. También puedes probar con `name` vacío, coordenadas faltantes o `soilType` distinto de los valores admitidos. Un `soilType` desconocido no es JSON válido para el enum y devuelve un mensaje personalizado de solicitud inválida.

## Variables de entorno

Compose usa estos valores predeterminados para desarrollo local:

| Variable | Valor predeterminado |
| --- | --- |
| `DATABASE_PASSWORD` | `huertos` |
| `DATABASE_USERNAME` | `huertos` |
| `DATABASE_URL` | `jdbc:postgresql://postgres:5432/huertos` dentro de Compose |

Para cambiar la contraseña, define `DATABASE_PASSWORD` en el entorno antes de ejecutar `docker compose up --build`.
