FROM maven:3.9.9-amazoncorretto-21-debian AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/*.jar api-gateway.jar
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]