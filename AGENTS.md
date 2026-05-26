# AGENTS.md — atlas-bank

Proyecto educativo Spring Boot con arquitectura hexagonal, seguridad OAuth2 (Keycloak) y pruebas de arquitectura con ArchUnit.

## Comandos

```bash
# Compilar (sin tests)
./mvnw clean compile -DskipTests

# Tests completos (incluye ArchUnit)
./mvnw clean test

# Test de un solo archivo
./mvnw test -Dtest=AccountDomainTest

# Solo tests de arquitectura
./mvnw test -Dtest="**/archtest/**"

# Ejecutar aplicación local (requiere Keycloak)
# 1. levantar infra: docker compose -f docker/docker-compose.yml up -d
# 2. luego: ./mvnw spring-boot:run
```

## Arquitectura hexagonal (`com.atlas.bank.atlas_bank`)

- **`domain/`** — Java puro (sin Spring). Modelos, servicios, eventos, excepciones, validaciones y estrategias.
- **`application/`** — Casos de uso (commands/queries), puertos (interfaces en `port/in/`, `port/out/`), servicios, facades y validación Cross-cutting.
- **`infrastructure/`** — Adaptadores REST (`adapter/in/rest/`), repositorios JPA (`adapter/out/`), config Spring (`config/`), listeners de eventos (`listener/`).

Reglas ArchUnit verificadas en `src/test/java/**/archtest/`:
- `domain` no depende de `infrastructure`, `application`, Spring, ni Spring Security.
- `application` no depende de `infrastructure` (solo usa puertos/interfaces).
- Los `*Controller` deben estar en `infrastructure.adapter.in.rest`.
- Los `*UseCase` deben estar en `application.port.in` y ser interfaces.
- Sin dependencias circulares entre paquetes ni módulos del dominio.

## Dependencias clave

- **Spring Boot** (4.0.4) + **Java 21**
- **H2** (memoria, `create-drop`) — consola en `/h2-console`
- **Keycloak 26** (OAuth2 resource server) — `docker compose -f docker/docker-compose.yml up -d`
- **MapStruct 1.6.3** (DTO mapping) + **Lombok** + **lombok-mapstruct-binding 0.2.0**
- **Caché** habilitado via `@EnableCaching`
- **ArchUnit 1.3.0** para tests de arquitectura

## API REST

Base: `/api/v1`

| Método | Ruta | Roles |
|--------|------|-------|
| POST | `/accounts` | ADMIN |
| GET | `/accounts` | ADMIN |
| GET | `/accounts/{id}` | USER, ADMIN |
| POST | `/transactions/transfer` | USER, ADMIN |
| GET | `/transactions/{id}/transactions` | USER, ADMIN |

## Security

- Keycloak realm: `atlas-bank` (issuer: `http://localhost:8181/realms/atlas-bank`)
- Roles del claim `realm_access.roles` se mapean como GrantedAuthority (prefijo `ROLE_` agregado por Spring Security).
- Admin: `admin`, User: `admin` (por defecto el converter de Keycloak).
- Endpoints `/h2-console/**` abiertos (sin auth).

## Convenciones

- **Domain es Java puro** — sin imports de Spring, Spring Security, ni infraestructura.
- **Nombres de paquete compuesto**: `com.atlas.bank.atlas_bank` (con guion bajo).
- **Lombok** en TODAS las clases del dominio (`@Getter`, `@Setter`, `@Builder`, `@RequiredArgsConstructor`, etc.).
- **Value Objects** en `domain.model.shared`: `Money`, `Currency`, `Email`.
- **Estrategias** de cálculo en `domain.strategy.fee.*` (FeeCalculator interface + implementaciones).
- **Validación del dominio** separada en `domain.validation.*`.
- **Commands/Queries** como objetos simples en `application.command/application.query`.
- **ReadModels** inmutables para consultas (no entidades JPA).
