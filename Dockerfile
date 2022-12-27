FROM eclipse-temurin:17-jre-alpine
EXPOSE 8081
COPY target/poststeaduserservice-1.0.0.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
