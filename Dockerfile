FROM adoptopenjdk:11-jdk-hotspot as build
WORKDIR /app
COPY . .
RUN ./gradlew clean build
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]