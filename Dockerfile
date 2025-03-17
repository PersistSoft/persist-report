FROM maven:3.9.9-amazoncorretto-17-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean install
FROM openjdk:17
VOLUME /tmp
COPY --from=builder /app/target/reports-0.0.1-SNAPSHOT.jar persist-report.jar
ENTRYPOINT ["java", "-jar", "/persist-report.jar"]
