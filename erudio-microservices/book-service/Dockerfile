FROM maven:3.9.9-amazoncorretto-21-debian AS builder

WORKDIR /builder

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /builder/target/*.jar ./book-service.jar

ENTRYPOINT ["java", "-jar", "book-service.jar"]