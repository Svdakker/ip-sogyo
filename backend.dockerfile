FROM openjdk:21

WORKDIR /individual-project

CMD ["./gradlew", "clean", "build"]

COPY domain/build/libs/domain-plain.jar domain-plain.jar

COPY persistence/build/libs/persistence-plain.jar persistence.plain.jar

COPY api/build/libs/api.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]