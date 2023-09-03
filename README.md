# KU Wongnai - Review Service

Handle review from users. (This only contain basic CRUD operation, lot of things will come in the future.)

## Setup

Run the following command to start MySQL Server at port 3306.

```sh
docker-compose up -d
```

Run the following command to start the application.

```sh
mvn spring-boot:run
```

## API

Service run at http://localhost:8091

### Find All Reviews

> GET -> http://localhost:8091/api/reviews

### Find Review By ID

> GET -> http://localhost:8091/api/reviews/{id}

### Create Review

> POST -> http://localhost:8091/api/reviews

```json
{
  "title": "Good",
  "content": "test",
  "rating": 3
}
```

### Update Review

> PUT -> http://localhost:8091/api/reviews/{id}

```json
{
  "title": "Good",
  "content": "test",
  "rating": 4
}
```

### Delete Review

> DELETE -> http://localhost:8091/api/reviews/{id}
