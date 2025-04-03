FROM maven:3.9.9-amazoncorretto-21-debian AS builder

WORKDIR /builder

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /builder/target/*.jar ./config-server.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-server.jar"]