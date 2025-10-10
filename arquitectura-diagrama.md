# Diagrama de Arquitectura - Event Sourcing POC (Actualizado)

## Descripción del Proyecto
Este proyecto implementa un ejemplo del patrón **Event Sourcing** para el manejo de ventas, donde los eventos se almacenan como fuente de verdad y se mantienen vistas materializadas para consultas rápidas. **Versión actualizada** con funcionalidades de reconstrucción de estado histórico.

## Diagrama de Arquitectura

```mermaid
graph TB
    %% Cliente
    Client[Cliente/Postman]
    
    %% Controllers
    subgraph "Capa de Presentacion"
        SEC[SaleEventController<br/>POST /event/sale/create<br/>POST /event/sale/change-state]
        SVC[SaleViewController<br/>GET /view/sale/states<br/>GET /view/sale/states/before<br/>GET /view/sale/last<br/>GET /view/sale/last/before]
    end
    
    %% Services
    subgraph "Capa de Dominio"
        PSS[ProcessingSaleService<br/>createSale<br/>changeStateOfSale]
        SVS[SalesViewService<br/>getAllSaleStatesById<br/>getSaleStatesBeforeDate<br/>getLastSaleState<br/>getSaleStateAsOfDate]
        States[States Enum<br/>CREATED PAYMENT DISPATCH COMPLETE]
    end
    
    %% Gateways
    subgraph "Puertos Interfaces"
        PE[ProcessingEvents<br/>createSale<br/>changeStateOfSale]
        PSE[PublishSaleEvent<br/>publish]
        SVG[SalesViewGateway<br/>getAllEventsByIdSale<br/>getAllEventsByIdSaleBeforeDate<br/>getCurrentSaleState]
    end
    
    %% Adapters
    subgraph "Adaptadores Infraestructura"
        PESA[ProcessingEventsSaleAdapter<br/>Auto-publicacion de eventos]
        PSEA[PublishSaleEventAdapter<br/>Actualiza vista materializada]
        SVA[SalesViewAdapter<br/>Consultas historicas]
    end
    
    %% Repositories
    subgraph "Repositorios"
        SES[SaveEventSale<br/>save]
        SVR[SalesViewRepository<br/>Consultas por fecha<br/>Estado actual]
    end
    
    %% Database
    subgraph "Base de Datos PostgreSQL"
        SET[(sale_event<br/>Event Store)]
        SVT[(sale_view<br/>Vista Materializada<br/>Historico completo)]
    end
    
    %% Conexiones Cliente -> Controllers
    Client --> SEC
    Client --> SVC
    
    %% Conexiones Controllers -> Services
    SEC --> PSS
    SVC --> SVS
    
    %% Conexiones Services -> Gateways
    PSS --> PE
    PSS --> PSE
    SVS --> SVG
    
    %% Conexiones Gateways -> Adapters
    PE --> PESA
    PSE --> PSEA
    SVG --> SVA
    
    %% Conexiones Adapters -> Repositories
    PESA --> SES
    PESA --> PSE
    PSEA --> SVR
    SVA --> SVR
    
    %% Conexiones Repositories -> Database
    SES --> SET
    SVR --> SVT
    
    %% Estados del flujo
    States --> PSS
    States --> SVS
    
    %% Estilos
    classDef controller fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef service fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef gateway fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef adapter fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef repository fill:#fce4ec,stroke:#880e4f,stroke-width:2px
    classDef database fill:#f1f8e9,stroke:#33691e,stroke-width:2px
    
    class SEC,SVC controller
    class PSS,SVS,States service
    class PE,PSE,SVG gateway
    class PESA,PSEA,SVA adapter
    class SES,SVR repository
    class SET,SVT database
```

## Flujo de Event Sourcing Actualizado

### 1. Comando (Write Operations) - Con Auto-publicación
```mermaid
sequenceDiagram
    participant C as Cliente
    participant SEC as SaleEventController
    participant PSS as ProcessingSaleService
    participant PE as ProcessingEvents
    participant PESA as ProcessingEventsSaleAdapter
    participant SES as SaveEventSale
    participant PSE as PublishSaleEvent
    participant PSEA as PublishSaleEventAdapter
    participant SVR as SalesViewRepository
    participant DB1 as sale_event Table
    participant DB2 as sale_view Table
    
    C->>SEC: POST /event/sale/create
    SEC->>PSS: createSale()
    PSS->>PE: createSale(eventEntity)
    PE->>PESA: createSale(eventEntity)
    PESA->>SES: save(eventEntity)
    SES->>DB1: INSERT INTO sale_event
    DB1-->>SES: Event saved
    SES-->>PESA: Success
    
    Note over PESA: Auto-publicación
    PESA->>PSE: publish(saleEvent)
    PSE->>PSEA: publish(saleEvent)
    PSEA->>SVR: save(saleView)
    SVR->>DB2: INSERT INTO sale_view
    DB2-->>SVR: View updated
    SVR-->>PSEA: Success
    PSEA-->>PSE: Success
    PSE-->>PESA: Success
    
    PESA-->>PE: Success
    PE-->>PSS: Success
    PSS-->>SEC: saleId
    SEC-->>C: 200 OK + saleId
```

### 2. Consulta Histórica (Read Operations) - Nueva Funcionalidad
```mermaid
sequenceDiagram
    participant C as Cliente
    participant SVC as SaleViewController
    participant SVS as SalesViewService
    participant SVG as SalesViewGateway
    participant SVA as SalesViewAdapter
    participant SVR as SalesViewRepository
    participant DB as sale_view Table
    
    C->>SVC: GET /view/sale/{id}/states/before?date=2024-01-01
    SVC->>SVS: getSaleStatesBeforeDate(id, date)
    SVS->>SVG: getAllEventsByIdSaleBeforeDate(id, date)
    SVG->>SVA: getAllEventsByIdSaleBeforeDate(id, date)
    SVA->>SVR: findAllBySaleIdAndLastEventDateBeforeOrderByLastEventDateAsc(id, date)
    SVR->>DB: SELECT * FROM sale_view WHERE sale_id = ? AND last_event_date < ?
    DB-->>SVR: Historical events
    SVR-->>SVA: List<SaleView>
    SVA-->>SVG: List<SaleEventEntity>
    SVG-->>SVS: List<SaleEventEntity>
    SVS-->>SVC: List<SaleEventEntity>
    SVC-->>C: 200 OK + Historical JSON
```

### 3. Reconstrucción de Estado (Event Sourcing Core)
```mermaid
sequenceDiagram
    participant C as Cliente
    participant SVC as SaleViewController
    participant SVS as SalesViewService
    participant SVG as SalesViewGateway
    participant SVA as SalesViewAdapter
    participant SVR as SalesViewRepository
    participant DB as sale_view Table
    
    C->>SVC: GET /view/sale/{id}/last/before?date=2024-01-01
    SVC->>SVS: getSaleStateAsOfDate(id, date)
    SVS->>SVG: getAllEventsByIdSaleBeforeDate(id, date)
    SVG->>SVA: getAllEventsByIdSaleBeforeDate(id, date)
    SVA->>SVR: findAllBySaleIdAndLastEventDateBeforeOrderByLastEventDateAsc(id, date)
    SVR->>DB: SELECT * FROM sale_view WHERE sale_id = ? AND last_event_date < ? ORDER BY last_event_date ASC
    DB-->>SVR: All events before date
    SVR-->>SVA: List<SaleView>
    SVA-->>SVG: List<SaleEventEntity>
    SVG-->>SVS: List<SaleEventEntity>
    
    Note over SVS: Aplica eventos secuencialmente<br/>para reconstruir estado
    SVS->>SVS: Reconstruct state from events
    SVS-->>SVC: SaleViewEntity (reconstructed)
    SVC-->>C: 200 OK + Reconstructed State
```

## Estados de la Venta

```mermaid
stateDiagram-v2
    [*] --> CREATED
    CREATED --> PAYMENT
    PAYMENT --> DISPATCH
    DISPATCH --> COMPLETE
    COMPLETE --> [*]
    
    note right of CREATED : Venta creada
    note right of PAYMENT : Pago procesado
    note right of DISPATCH : Despachada
    note right of COMPLETE : Completada
```

## Tecnologías Utilizadas

- **Framework**: Spring Boot 3.5.6
- **Lenguaje**: Java 21
- **Base de Datos**: PostgreSQL 17.2
- **ORM**: Spring Data JPA + Hibernate
- **Build Tool**: Gradle
- **Contenedorización**: Docker Compose
- **Logging**: Log4j2

## Nuevas Funcionalidades Implementadas

### 1. **Consultas Históricas**
- `GET /view/sale/{id}/states` - Todos los estados de una venta
- `GET /view/sale/{id}/states/before?date=...` - Estados antes de una fecha
- `GET /view/sale/{id}/last` - Último estado de una venta
- `GET /view/sale/{id}/last/before?date=...` - Estado reconstruido hasta una fecha

### 2. **Auto-publicación de Eventos**
- Los eventos se publican automáticamente al guardarse
- Actualización inmediata de la vista materializada
- Sincronización garantizada entre event store y vista

### 3. **Reconstrucción de Estado**
- Implementación del patrón Event Sourcing puro
- Aplicación secuencial de eventos para reconstruir estado
- Capacidad de "viajar en el tiempo" del estado de las ventas

## Patrones Arquitectónicos Implementados

1. **Event Sourcing**: Los eventos se almacenan como fuente de verdad
2. **CQRS**: Separación entre comandos (eventos) y consultas (vistas)
3. **Hexagonal Architecture**: Separación clara entre dominio e infraestructura
4. **Repository Pattern**: Abstracción del acceso a datos
5. **Gateway Pattern**: Interfaces para la comunicación entre capas
6. **Materialized Views**: Vistas optimizadas para consultas históricas
7. **State Reconstruction**: Reconstrucción de estado a partir de eventos

