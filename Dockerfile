FROM openjdk:19
WORKDIR /app
COPY ./target/trp-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "trp-0.0.1-SNAPSHOT.jar"]