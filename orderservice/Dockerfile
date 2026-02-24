FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/*.jar /app/order-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]
