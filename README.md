# Library System

Library System App project

## Getting Started

### To run the application, you must follow the following instructions

#### 1- Create an `application.properties` file under `/src/main/resources` to store environment variables required to run this application

Here's an example of how you should configure your `application.properties` file:

```
#
# JDBC properties
#
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=[user_name]
spring.datasource.password=[password]
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update

#
# Spring Data REST properties
#
server.port=3000
server.servlet.context-path=/api

# App Properties
library.app.jwtSecret=[secret key]
library.app.saltRounds=10
library.app.jwtExpirationMs= 86400000
spring.mvc.dispatch-options-request=true
```

#### 2- Ensure you have Docker and Docker Compose installed and running, and then run the following command:

- `docker-compose up` or `sudo docker compose up` depending on your OS.
