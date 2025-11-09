FROM eclipse-temurin:17-alpine-3.22
WORKDIR /news-batch
COPY ./build/libs/my-portal-news-batch-0.0.1-SNAPSHOT.jar my-portal-news-batch-0.0.1.jar
ENTRYPOINT ["java", "-jar", "my-portal-news-batch-0.0.1.jar"]