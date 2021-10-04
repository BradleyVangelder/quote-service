FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY target/quote-service-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]