# Sports Betting Settlement Trigger Service

## Overview
This service simulates sports betting event outcome handling and bet settlement using Kafka (for event outcomes) and a mocked RocketMQ producer (logs) for bet settlements. It exposes a simple REST API to publish event outcomes and includes a Kafka consumer to process them, matching against in-memory (H2) bets and producing settlement messages for winners.

## What’s Implemented
- REST API endpoint to publish a sports event outcome to Kafka (topic: `event-outcomes`).
- Kafka producer and consumer with configuration externalized to `application.properties`.
- In-memory H2 database with seed data for bets.
- Winner filtering: only bets that predicted the actual winner are sent to settlements.
- RocketMQ producer mocked via logging (no actual RocketMQ setup).

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Kafka
- Spring Data JPA + H2 (in-memory)
- Lombok
- MapStruct
- Docker Compose (for Kafka/ZooKeeper)
- Gradle (via Gradle Wrapper: ./gradlew)

## Project Structure (key parts)
- `controller/SportEventController.java`: REST endpoint to publish outcomes.
- `kafka/KafkaProducer.java`: Publishes `SportEventOutcome` objects to Kafka (JSON-serialized via Spring Kafka).
- `kafka/KafkaConsumer.java`: Consumes `SportEventOutcome` objects from Kafka and triggers handling.
- `service/SportEventServiceImpl.java`: Matches bets by eventId, filters winners, and builds settlement messages.
- `repository/BetRepository.java`: JPA repository.
- `rocketmq/RocketMQProducer.java`: Mock RocketMQ producer (logs payloads).
- `domain/`: Bet, SportEventOutcome, BetSettlementMessage (domain models).
- `dto/`: BetDto, SportEventOutcomeDto (API layer DTOs).
- `mapper/`: MapStruct mappers for DTO/Domain/Entity transformations.
- `entity/BetEntity.java`: JPA entity for bets.
- `seed/DataInitializer.java`: Seeds some bets into H2 at startup.

## Prerequisites
- Java 17+
- Docker & Docker Compose (for Kafka)
- No local Gradle installation required; use the provided Gradle Wrapper (./gradlew)

## How to Run
1) Start Kafka locally via Docker Compose:

```bash
docker compose up -d
```

This starts ZooKeeper and Kafka listening on `localhost:9092`.

Create the Kafka topic:

```bash
docker exec -it <kafka-container-id> kafka-topics \
  --bootstrap-server localhost:9092 \
  --create \
  --topic event-outcomes \
  --partitions 1 \
  --replication-factor 1
```

2) Build and run the app (from project root):

```bash
./gradlew clean bootRun
```

3) Verify H2 console (optional):
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (empty)

## Configuration
All relevant properties are externalized in `src/main/resources/application.properties`:

```properties
kafka.bootstrap-servers=localhost:9092
kafka.consumer.group-id=bet-settlement-group
kafka.topics.event-outcomes=event-outcomes
rocketmq.topics.bet-settlements=bet-settlements
```

## API Usage
- Publish a sport event outcome:

```http
POST http://localhost:8080/sport-event/outcomes
Content-Type: application/json
```

Body example:

```json
{
  "eventId": "EVT-1",
  "eventName": "Event One",
  "eventWinnerId": "WIN-1"
}
```

Behavior:
- The API publishes a `SportEventOutcome` object to Kafka topic `event-outcomes` using the `eventId` as key (Spring Kafka JSON-serializes the object).
- The Kafka consumer receives the `SportEventOutcome` object, loads bets by `eventId` from H2, filters only winning bets (`bet.eventWinnerId == sportEventOutcome.eventWinnerId`), and sends settlement messages via the mocked RocketMQ producer.
- Settlement messages are logged with prefix: `[MOCK ROCKETMQ]`.

- List all bets (seeded):

```http
GET http://localhost:8080/bets
```

## Seed Data
The application seeds three bets on startup (see `seed/DataInitializer.java`):
- (betId=1, userId=101, eventId=EVT-1, market=MKT-1, predictedWinner=WIN-1, amount=10)
- (betId=2, userId=102, eventId=EVT-1, market=MKT-1, predictedWinner=WIN-2, amount=20)
- (betId=3, userId=103, eventId=EVT-2, market=MKT-2, predictedWinner=WIN-3, amount=15)

## Example Flow
- Publish outcome for `EVT-1` with `eventWinnerId` `WIN-1` (see sample above).
- The consumer will find 2 bets for `EVT-1` but only `betId=1` is a winner; only that settlement will be logged.

## Notes & Assumptions
- RocketMQ producer is intentionally mocked (logs). No RocketMQ installation is required.
- Kafka configuration uses `localhost:9092` by default; see `docker-compose.yml` for a quick local cluster.

## Potential Improvements (Next Steps)
- Add integration tests for Kafka flow with Testcontainers.
- Extend settlement payload and persistence of settlement results.
- Add basic validation and error handling for the API.
- Add OpenAPI/Swagger docs for the REST API.

## Testing
- Run all tests:

```bash
./gradlew test
```

- Run one test class:

```bash
./gradlew test --tests "com.example.sportsbettingsettlement.*YourTestClassName*"
```

- Reports: `build/reports/tests/test/index.html`

## Troubleshooting
- If the API returns errors when publishing to Kafka, ensure Docker Compose stack is up and Kafka listens on `localhost:9092`.
- Check application logs for `[MOCK ROCKETMQ]` lines after publishing an outcome.
