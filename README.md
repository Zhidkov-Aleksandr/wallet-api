# Wallet API Application

REST-сервис для управления балансом кошельков.
Предназначен для обработки операций пополнения и списания средств с учётом конкурентного доступа
и корректной обработки ошибок.

Проект реализован в рамках тестового задания и ориентирован на надёжную работу под нагрузкой
(многократные запросы к одному кошельку).

## Стек технологий

Backend:
- Java 17
- Spring Boot 3.5.9
- Spring Web
- Spring Data JPA
- Hibernate (optimistic locking)

База данных:
- PostgreSQL 15

Миграции:
- Liquibase

Сборка и контейнеризация:
- Maven
- Docker
- Docker Compose

Тестирование:
- JUnit 5
- Spring Boot Test
- MockMvc
- H2 (in-memory)

## Быстрый старт

### 1. Запуск локально через Docker Compose

Проект поставляется с готовым `docker-compose.yml`, который поднимает все необходимые сервисы:
- wallet-api — REST-приложение на Spring Boot
- postgres — база данных PostgreSQL

```bash
# Сборка и запуск всех сервисов
docker compose up --build -d

# Приложение будет доступно по адресу:
# http://localhost:8080
```
