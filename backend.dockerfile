FROM openjdk:21

WORKDIR /individual-project

CMD ["./gradlew", "clean", "bootJar"]

COPY api/build/libs/api.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]