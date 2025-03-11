# Guía de Uso: Aplicación de Interbanking

## Índice

1. [Iniciando la Aplicación](#iniciando-la-aplicación)
2. [Datos Cargados Inicialmente](#datos-cargados-inicialmente)
3. [Accediendo a la Base de Datos](#accediendo-a-la-base-de-datos)
4. [Operaciones Disponibles](#operaciones-disponibles)
   - [Gestión de Empresas](#gestión-de-empresas)
   - [Gestión de Transferencias](#gestión-de-transferencias)
6. [Uso de Postman](#uso-de-postman)

## Iniciando la Aplicación

Para iniciar la aplicación:

1. Abra una terminal en el directorio del proyecto
2. Ejecutar: `mvn spring-boot:run`
3. La aplicación levanta en: http://localhost:8080

## Datos Cargados Inicialmente

La aplicación carga automáticamente los siguientes datos al iniciar:

### Empresas (Companies)
```sql
INSERT INTO companies (tax_id, business_name, join_date) VALUES ('20443441575', 'Company A', '2025-03-10');
INSERT INTO companies (tax_id, business_name, join_date) VALUES ('20443441574', 'Company B', '2025-02-20');
```

### Transferencias (Transfers)
```sql
INSERT INTO transfers (company_tax_id, debit_account, credit_account, amount, transfer_date) 
VALUES ('20443441575', '123', '456', 1000.00, '2025-02-28 10:35:16.881');

INSERT INTO transfers (company_tax_id, debit_account, credit_account, amount, transfer_date) 
VALUES ('20443441574', '789', '012', 500.00, '2025-02-28 11:00:00.000');
```

Estos datos están definidos en el archivo `data.sql` que se encuentra en `src/main/resources/` y se cargan automáticamente al iniciarse la aplicación.

## Accediendo a la Base de Datos

Para ver los datos almacenados en la base de datos:

1. Abra su navegador web vaya a: http://localhost:8080/h2-console
3. Complete los campos con la siguiente información:
   - JDBC URL: `jdbc:h2:mem:interbankingdb;MODE=PostgreSQL`
   - Usuario: `interbanking`
   - Contraseña: `pass`
4. Haga clic en "Conectar"


## Operaciones Disponibles

### Gestión de Empresas

La API ofrece las siguientes operaciones para gestionar empresas:

#### Registrar una nueva empresa
- **Método**: POST
- **URL**: `/companies`
- **Descripción**: Permite registrar una nueva empresa en el sistema. Si no se proporciona la fecha de adhesión, se asignará la fecha actual.
- **Datos requeridos**: CUIT/Tax ID y nombre de la empresa

#### Obtener empresas filtradas
- **Método**: GET
- **URL**: `/companies?filter=TIPO_FILTRO`
- **Descripción**: Obtiene una lista de empresas según el filtro seleccionado.
- **Filtros disponibles**:
  - `JOINED_LAST_MONTH`: Empresas que se adhirieron en el último mes
  - `WITH_TRANSFERS_LAST_MONTH`: Empresas que realizaron transferencias en el último mes

### Gestión de Transferencias

La API ofrece las siguientes operaciones para gestionar transferencias:

#### Registrar una nueva transferencia
- **Método**: POST
- **URL**: `/transfers`
- **Descripción**: Este endpoint se agrego con la finalidad de facilitar las pruebas. Permite registrar una transferencia entre cuentas bancarias. Si no se especifica la fecha de transferencia, se asignará la fecha actual.
- **Datos requeridos**: CUIT de la empresa, cuenta de débito, cuenta de crédito y monto

## Uso de Postman

Para probar los servicios de la API:

1. Abra Postman
2. Importe el archivo de colección proporcionado en la carpeta `/postman/`
3. Encontrará carpetas organizadas por tipo de operación

### Ejemplos de uso con Postman:

**Registrar una nueva empresa**:
1. Seleccione la operación "Registrar Empresa"
2. El cuerpo de la solicitud debe tener este formato:
   ```json
   {
     "taxId": "30999888777",
     "businessName": "Nueva Empresa",
     "joinDate": "2025-03-11"
   }
   ```
3. Haga clic en "Enviar"

**Registrar una nueva transferencia**:
1. Seleccione la operación "Registrar Transferencia"
2. El cuerpo de la solicitud debe tener este formato:
   ```json
   {
     "companyTaxId": "20443441575",
     "debitAccount": "123456",
     "creditAccount": "789012",
     "amount": 1500.50,
     "transferDate": "2025-03-11T10:30:00"
   }
   ```
3. Haga clic en "Enviar"


