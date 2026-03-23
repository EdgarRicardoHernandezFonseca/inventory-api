# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar jar generado
COPY --from=builder /app/target/*.jar app.jar

# Puerto de Spring Boot
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]