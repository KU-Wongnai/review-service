# KU Wongnai - Review Service

Handle review from users.

## Setup

Copy `.env.example` to `.env` and change the value to your environment.

Add your `JWT_SECRET` to `.env` file, which should come from user-service.

```sh
JWT_SECRET=
```

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

Every route require Authorization header with JWT token. (Except GET /api/reviews/\*\*)

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
  "rating": 4,
  "images": [
    {
      "imageUrl": "https://www.wongnai.com/blog/content/uploads/2019/10/cover-1.jpg"
    },
    {
      "imageUrl": "https://www.wongnai.com/blog/content/uploads/2019/10/cover-1.jpg"
    }
  ]
}
```

### Delete Review

> DELETE -> http://localhost:8091/api/reviews/{id}

### Like Review

If user already like the review, it will unlike the review.

> POST -> http://localhost:8091/api/reviews/{id}/like

### Create Comment

> POST -> http://localhost:8091/api/reviews/{id}/comment

```json
{
  "content": "test"
}
```

### Update Comment

> PUT -> http://localhost:8091/api/reviews/comment/{commentId}

```json
{
  "content": "test"
}
```

### Delete Comment

> DELETE -> http://localhost:8091/api/reviews/comment/{commentId}
