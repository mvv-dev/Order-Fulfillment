# Order Fulfillment --- Distributed Microservices with Saga Orchestration

## Overview

This project is a complete implementation of a **distributed Order
Fulfillment system** built with **Java + Spring Boot**, applying:

-   Domain-Driven Design (DDD)
-   Hexagonal Architecture (Ports & Adapters)
-   Event-Driven Architecture
-   Saga Orchestration Pattern
-   RabbitMQ Messaging
-   Service Discovery (Eureka)
-   API Gateway
-   OAuth2 / OIDC (Keycloak)
-   PostgreSQL (Database per service)
-   Docker-ready structure

The goal is to demonstrate real-world distributed system design, strong
separation of concerns, and orchestration of business flows across
multiple microservices.

------------------------------------------------------------------------

# Architecture Overview

The system follows a **microservices architecture** where each service:

-   Owns its own database
-   Owns its own domain model
-   Communicates asynchronously via RabbitMQ
-   Is discoverable via Eureka
-   Is routed through an API Gateway
-   Is secured via OAuth2 JWT tokens

### Services

-   **orders-service**
-   **products-service (inventory)**
-   **payments-service**
-   **cards-service**
-   **saga-service (orchestrator)**
-   **eureka-server**
-   **api-gateway**

Each service is independently deployable.

------------------------------------------------------------------------

# Domain-Driven Design (DDD)

The project is structured using DDD concepts:

## 1. Bounded Contexts

Each microservice represents a bounded context:

-   Orders Context
-   Products Context
-   Payments Context
-   Cards Context
-   Saga Context

No service shares domain logic with another.

## 2. Layered Structure per Service

Each service is structured as:

    application/
    domain/
    infra/

### domain/

Contains: - Entities - Value Objects - Domain Enums - Domain Rules

No framework dependencies.

### application/

Contains: - Use Cases - Ports (interfaces) - Payload definitions
(commands/events) - Controllers (if applicable)

Coordinates business flow but does not depend on infrastructure.

### infra/

Contains: - JPA entities - Repositories (Spring Data) - RabbitMQ
consumers - RabbitMQ publishers - Mappers - External integrations

Implements application ports.

------------------------------------------------------------------------

# Hexagonal Architecture (Ports & Adapters)

Each service uses **Hexagonal Architecture**:

Core (Domain + Application) is isolated from infrastructure.

### Ports

Defined in `application.port`:

-   Repository ports
-   Event publisher ports
-   Command publisher ports

### Adapters

Implemented in `infra`:

-   JPA repositories
-   RabbitMQ publishers
-   RabbitMQ consumers

This ensures:

-   Testability
-   Replaceable infrastructure
-   Framework-independent business logic

------------------------------------------------------------------------

# Event-Driven Architecture

Services communicate exclusively via **RabbitMQ** using:

-   Events (facts that happened)
-   Commands (requests to perform an action)

All messages follow a common envelope structure:

``` json
{
  "messageId": "uuid",
  "name": "event.orders.created",
  "type": "EVENT",
  "correlationId": "orderId",
  "causationId": "previousMessageId",
  "source": "orders-service",
  "occurredAt": "timestamp",
  "payload": { ... }
}
```

## Message Fields

-   messageId → unique message identifier
-   correlationId → order identifier (used to track saga flow)
-   causationId → previous message that triggered this message
-   type → COMMAND or EVENT
-   payload → business data

------------------------------------------------------------------------

# Saga Orchestration

The project implements **Orchestrated Saga Pattern**.

The `saga-service` controls the flow.

## Order Flow

1.  Order emits: `event.orders.solicited`
2.  Saga sends: `command.products.check_items`
3.  Products emits:
    -   `event.products.items_checked`
4.  Saga decides:
    -   If stock available → `command.orders.create`
    -   If not → `command.orders.cancel`
5.  Orders emits `event.orders.created`
6.  Saga sends `command.products.reserve_items`
7.  Saga sends `command.payments.process`
8.  Payments sends `command.cards.debit`
9.  Cards emits:
    -   `event.card.debit.succeeded`
    -   or `event.card.debit.failed`
10. Saga:

-   On success → `command.orders.confirm`
-   On failure → `command.orders.cancel` +
    `command.products.release_inventory`

The saga guarantees consistency across services.

------------------------------------------------------------------------

# RabbitMQ

RabbitMQ is used for:

-   Asynchronous communication
-   Decoupled services
-   Reliable message delivery

Each service:

-   Listens to its queue
-   Publishes to exchanges
-   Uses routing keys per event/command type

------------------------------------------------------------------------

# Service Discovery (Eureka)

-   All services register in Eureka
-   API Gateway resolves services dynamically
-   Enables horizontal scaling

------------------------------------------------------------------------

# API Gateway

Acts as:

-   Entry point for external requests
-   JWT validation
-   Route forwarding to services

------------------------------------------------------------------------

# Security (OAuth2 / OIDC)

Authentication handled via **Keycloak**.

-   API Gateway validates JWT
-   Services act as Resource Servers
-   User ID extracted from JWT claim (`sub`)

User identity is propagated into domain layer when needed.

------------------------------------------------------------------------

# Database Strategy

Each microservice has:

-   Its own PostgreSQL database
-   Its own schema
-   No shared tables

This guarantees bounded context integrity.

------------------------------------------------------------------------

# Consistency Model

The system follows:

-   Eventual consistency
-   Distributed transaction avoidance
-   Saga-based compensation strategy

------------------------------------------------------------------------

# Technologies

-   Java 17
-   Spring Boot
-   Spring Data JPA
-   PostgreSQL
-   RabbitMQ
-   Spring AMQP
-   Eureka (Netflix OSS)
-   Spring Cloud Gateway
-   Keycloak (OAuth2 / OIDC)
-   Docker-ready configuration

------------------------------------------------------------------------

# Design Principles Applied

-   Separation of concerns
-   Clean Architecture
-   Ports & Adapters
-   Domain isolation
-   Database per service
-   Asynchronous communication
-   Orchestrated Saga
-   Explicit command/event contracts

------------------------------------------------------------------------

# Project Status

Fully functional distributed workflow including:

-   Order creation
-   Stock validation
-   Inventory reservation
-   Payment processing
-   Card debit
-   Order confirmation
-   Order cancellation with compensation

------------------------------------------------------------------------

# Purpose

This project was built to demonstrate:

-   Advanced distributed systems design
-   Real-world Saga implementation
-   Strong DDD application
-   Enterprise-ready architecture patterns

------------------------------------------------------------------------

# Author

Marcos Vinicius

------------------------------------------------------------------------
