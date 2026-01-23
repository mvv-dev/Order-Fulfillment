# Order Fulfillment — Microservices (Java/Spring)

Sistema de **processamento de pedidos** baseado em **arquitetura de microserviços** e **mensageria** (event-driven), com **orquestração via Saga** para garantir consistência em um fluxo distribuído.

O objetivo do projeto é demonstrar, na prática, conceitos de:
- **Microservices + DDD (Bounded Contexts)**
- **Mensageria (eventos e comandos)**
- **Saga Orchestration**
- **Idempotência, Retry/Backoff e Dead Letter Queue (DLQ)**
- **Segurança com Keycloak (OAuth2/OIDC)**
- Observabilidade e boas práticas de arquitetura

---

## Domínio (visão de alto nível)

Um **pedido** nasce como `PENDING` e só pode ser `CONFIRMED` se:
- o **estoque** for reservado com sucesso
- o **pagamento** for aprovado com sucesso

Caso alguma etapa falhe, o pedido é `CANCELLED` e eventuais reservas são liberadas.

---

## Serviços (planejados)

- **Cards Service**: gerenciamento de cartões e tipos de cartão
- **Orders Service**: criação e consulta de pedidos
- **Inventory Service**: catálogo e disponibilidade de produtos (reserva/liberação)
- **Payments Service**: validação de pagamento e emissão de eventos
- **Orchestrator Service**: implementação da **Saga** (consome eventos e emite comandos)

---

## Segurança (Keycloak)

A autenticação/autorização será centralizada no **Keycloak** (OAuth2/OIDC).  
Os microserviços atuarão como **Resource Servers**, validando JWT e extraindo o usuário via claim `sub`.

- O `sub` (Keycloak User ID) será utilizado como identificador do usuário no domínio (ex.: `user_id`).

---

## Eventos e Comandos (visão inicial)

O fluxo será organizado com:
- **Eventos**: fatos que aconteceram (ex.: `order.created`, `inventory.reserved`, `payment.approved`)
- **Comandos**: pedidos para executar uma ação (ex.: `reserve.inventory`, `process.payment`, `confirm.order`)

A Saga (Orchestrator) consumirá eventos e publicará comandos para manter o fluxo linear e explícito.

---

## Stack / Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL (por microserviço)
- RabbitMQ (mensageria)
- Keycloak (OAuth2/OIDC)
- Docker (infra local) *(a ser adicionado)*
- Observabilidade (logs/metrics) *(a ser adicionado)*


---

## Status

🚧 Em construção — foco atual:
- Bootstrap dos serviços
- Modelagem e endpoints iniciais (Cards e Orders)
- Integração com Keycloak
- Mensageria e Saga (orquestração)


