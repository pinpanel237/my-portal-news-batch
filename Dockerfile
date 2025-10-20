FROM eclipse-temurin:17-jre-alpine
WORKDIR /news-batch
COPY ./build/libs/my-portal-news-batch-0.0.1-SNAPSHOT.jar my-portal-news-batch-0.0.1.jar
ENTRYPOINT ["java", "-jar", "my-portal-news-batch-0.0.1.jar"]