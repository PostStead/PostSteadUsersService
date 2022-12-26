FROM eclipse-temurin:17-jre-alpine
EXPOSE 8081
COPY target/poststeaduserservice-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
