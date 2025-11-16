# Recipe Management System

A Spring Boot-based application for managing recipes with batch processing support. The project leverages Spring Batch to read JSON recipe data, map it to entities, and save it to a MySQL database. It also provides REST APIs for searching recipes with filtering and pagination.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [License](#license)

---

## Features

- Load recipes from a JSON file using Spring Batch.
- Store recipes in MySQL database.
- Search recipes with filters:
  - Calories (<,>,<=,>=,=)
  - Title (partial match)
  - Cuisine (equals)
  - Total time (<,>,<=,>=,=)
  - Rating (<,>,<=,>=,=)
- Paginated API results.
- Uses `@Embeddable` Nutrients entity to store recipe nutrition data.
- Spring Boot + Spring Data JPA integration.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Batch
- Spring Data JPA
- MySQL 8
- Maven
- Lombok
- Jackson for JSON parsing

---

## Project Structure
```
com.ganesh.recipe
│
├── batch
│ ├── RecipeItemReader.java
│ ├── RecipeMapper.java
│ ├── RecipeItemProcessor.java
│ ├── RecipeItemWriter.java
│ └── JsonMapLoader.java
│
├── config
│ └── BatchConfig.java
│
├── entity
│ ├── Recipe.java
│ └── Nutrients.java
│
├── repository
│ └── RecipeRepository.java
│
├── service
│ ├── RecipeService.java
│ └── RecipeServiceImpl.java
│
└── controller
| └── RecipeController.java
|___RecipeApplication.java

```

---

## Setup and Installation

1. Clone the repository:

```bash
git clone https://github.com/<your-username>/recipe-management.git
cd recipe-management
```

2. Configure MySQL:

Create a database named recipedb.

Update application.yml with your DB credentials.
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/recipedb
    username: root
    password: 1234
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

3. Add your JSON file (recipes.json) in src/main/resources.
---
## Running the Application
```
mvn spring-boot:run
```

The application will automatically create Spring Batch default tables in MySQL on first run.

The batch job will read recipes from the JSON file and save them to the database.

---
## API Endpoints
1. Save Recipes from JSON
```
URL: /api/saveJson

Method: POST

Request Body: None

Response: JSON confirmation of batch job completion

curl -X POST http://localhost:8080/api/saveJson
```
2. Search Recipes (Paginated)
```
URL: /api/recipes/search

Method: GET

Query Parameters:

calories (e.g., <=400)

title (partial match)

cuisine

total_time (e.g., >=30)

rating (e.g., >=4.5)

page (default: 0)

limit (default: 10)

Example Request:

curl "http://localhost:8080/api/recipes/search?calories=<=400&title=pie&rating=>=4.5&page=0&limit=5"
```

Response:
```
{
  "content": [
    {
      "id": 1,
      "title": "Apple Pie",
      "cuisine": "American",
      "rating": 4.6,
      "prep_time": 30,
      "cook_time": 50,
      "total_time": 80,
      "serves": "4",
      "nutrients": {
        "calories": "350",
        "carbohydrateContent": "45g",
        "proteinContent": "5g",
        "fatContent": "15g",
        "saturatedFatContent": "8g",
        "unsaturatedFatContent": "7g",
        "sodiumContent": "200mg",
        "cholesterolContent": "50mg",
        "fiberContent": "3g",
        "sugarContent": "25g"
      }
    }
  ],
  "page": 0,
  "size": 5,
  "totalElements": 20,
  "totalPages": 4
}
```
---

## Notes

Spring Batch automatically creates its default metadata tables (BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, etc.) when the application starts for the first time.

Nutrients JSON fields must match the Nutrients class properties, otherwise Jackson may throw errors (e.g., unrecognized fields).

---

## License

This project is licensed under the MIT License.
