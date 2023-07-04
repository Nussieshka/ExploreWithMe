FROM amazoncorretto:11
COPY service/target/*.jar stats-service.jar
ENTRYPOINT ["java", "-jar", "stats-service.jar"]