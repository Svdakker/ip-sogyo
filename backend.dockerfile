FROM openjdk:21

WORKDIR /individual-project

COPY docker/domain-plain.jar domain-plain.jar

COPY docker/persistence-plain.jar persistence.plain.jar

COPY docker/api.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]