FROM openjdk:11-jdk
EXPOSE 8080
ARG JAR_FILE=target/bookquotes-1.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]