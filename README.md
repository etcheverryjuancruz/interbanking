# Interbanking API

## Descripción
Interbanking API es un sistema desarrollado en **Spring Boot** que gestiona empresas y transferencias bancarias. Está basado en **arquitectura hexagonal**, lo que lo hace modular y escalable.

---

##  Tecnologías Utilizadas
- **Java 17**
- **Spring Boot 3.4.3**
- **PostgreSQL**
- **Hibernate** (JPA)
- **Maven**
- **Docker & Docker Compose**
- **Swagger (OpenAPI)**



## 🔧 Instalación y Ejecución
### Requisitos previos
- **Docker & Docker Compose** instalado
- **Java 21**
- **Maven**

### 🚀 Construcción y Ejecución con Docker Compose
1️⃣ **Construir y levantar los contenedores**
```bash
docker-compose up --build -d
```


5️⃣ **Detener y eliminar los contenedores**
```bash
docker-compose down
```

---

## 📖 API Endpoints
La documentación completa de la API está disponible a través de **Swagger**.
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Docs**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

---

## 🛠️ Desarrollo y Testing
### Construcción del JAR
```bash
./mvnw clean package
```

### Ejecutar Pruebas
```bash
./mvnw test
```

---
