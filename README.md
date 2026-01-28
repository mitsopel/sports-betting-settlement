Sports Betting Settlement Trigger Service

Overview
This service simulates sports betting event outcome handling and betEntity settlement using Kafka (for event outcomes) and a mocked RocketMQ producer (logs) for betEntity settlements. It exposes a simple REST API to publish event outcomes and includes a Kafka consumer to process them, matching against in-memory (H2) bets and producing settlement messages for winners.

What’s Implemented
- REST API endpoint to publish a sports event outcome to Kafka (topic: event-outcomes).
- Kafka producer and consumer.
- In-memory H2 database with seed data for bets.
- Winner filtering: only bets that predicted the actual winner are sent to settlements.
- RocketMQ producer mocked via logging (no RocketMQ setup required).

Tech Stack
- Java 17
- Spring Boot 3
- Spring Kafka
- Spring Data JPA + H2 (in-memory)
- Lombok
- Docker Compose (for Kafka/ZooKeeper)

Project Structure (key parts)
- controller/SportEventOutcomeController.java: REST endpoint to publish outcomes.
- kafka/SportEventOutcomeKafkaProducer.java: Publishes outcome JSON to Kafka.
- kafka/SportEventOutcomeKafkaConsumer.java: Consumes from Kafka and triggers handling.
- service/SportEventOutcomeServiceImpl.java: Matches bets by eventId and filters winners.
- repository/BetRepository.java: JPA repository.
- rocketmq/BetSettlementProducer.java: Mock RocketMQ producer (logs payloads).
- seed/DataInitializer.java: Seeds some bets into H2 at startup.

Prerequisites
- Java 17+
- Docker & Docker Compose (for Kafka)

How to Run
1) Start Kafka locally via Docker Compose:
   docker compose up -d

   This starts ZooKeeper and Kafka listening on localhost:9092.

2) Build and run the app (from project root):
   ./gradlew clean bootRun

3) Verify H2 console (optional):
   - URL: http://localhost:8080/h2-console
   - JDBC URL: jdbc:h2:mem:testdb
   - Username: sa
   - Password: (empty)

API Usage
- Publish a sport event outcome:
  POST http://localhost:8080/sport/event-outcomes
  Content-Type: application/json
  Body example:
  {
    "eventId": "EVT-1",
    "eventName": "Event One",
    "eventWinnerId": "WIN-1"
  }

  Behavior:
  - The API publishes the JSON to Kafka topic event-outcomes using the eventId as key.
  - The Kafka consumer reads the message, loads bets by eventId from H2, filters only winning bets (betEntity.eventWinnerId == eventWinnerId), and sends settlement messages via the mocked RocketMQ producer.
  - Settlement messages are logged with prefix: [MOCK ROCKETMQ]

- List all bets (seeded):
  GET http://localhost:8080/bets

Seed Data
The application seeds three bets on startup (see seed/DataInitializer.java):
- (betId=1, userId=101, eventId=EVT-1, market=MKT-1, predictedWinner=WIN-1, amount=10)
- (betId=2, userId=102, eventId=EVT-1, market=MKT-1, predictedWinner=WIN-2, amount=20)
- (betId=3, userId=103, eventId=EVT-2, market=MKT-2, predictedWinner=WIN-3, amount=15)

Example Flow
- Publish outcome for EVT-1 with eventWinnerId WIN-1 (see sample above).
- The consumer will find 2 bets for EVT-1 but only betId=1 is a winner; only that settlement will be logged.

Notes & Assumptions
- RocketMQ producer is intentionally mocked (logs). No RocketMQ installation is required.
- Kafka configuration is minimal and uses localhost:9092; see docker-compose.yml for a quick local cluster.
- Topics and group ids are currently hardcoded in code for simplicity (event-outcomes, group betEntity-settlement-group).

Potential Improvements (Next Steps)
- Externalize Kafka topic names, group ids, and bootstrap servers to application.properties.
- Add integration tests for Kafka flow with Testcontainers.
- Extend settlement payload and persistence of settlement results.
- Add basic validation and error handling for the API.
- Add OpenAPI/Swagger docs for the REST API.

Troubleshooting
- If the API returns errors when publishing to Kafka, ensure Docker Compose stack is up and Kafka listens on localhost:9092.
- Check application logs for [MOCK ROCKETMQ] lines after publishing an outcome.
