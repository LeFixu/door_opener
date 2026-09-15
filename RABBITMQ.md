# Local RabbitMQ flow

The API publishes a JSON `door.updated` event after saving a door. The consumer
binds a durable `door-update-logger` queue to the `door.events` topic exchange
and logs each received event.

## Run locally

Start RabbitMQ from the repository root:

```sh
docker compose up -d
```

The management UI is available at <http://localhost:15672> with `guest` / `guest`.

Start the consumer in another terminal:

```sh
cd consumer
mvn spring-boot:run
```

Start the API with its development profile:

```sh
cd api
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

After an authenticated `PUT /door`, the consumer logs the event ID, door ID,
new state, and timestamp. The API test suite does not require RabbitMQ because
its publisher is mocked in the application test.

This first version is deliberately database-first rather than atomic: a
database save can succeed even if publishing fails. A transactional outbox is
the next step when delivery guarantees matter.

Stop the broker with `docker compose down`; add `-v` when you also want to
remove its persisted queue data.