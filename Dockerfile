FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/GestionInventario-0.0.1-SNAPSHOT.war inventoryapp-app.war

ENTRYPOINT ["java", "-jar", "inventoryapp-app.war"]