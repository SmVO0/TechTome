# ---- Build stage ----
# Compiles the app inside Docker so no local Maven/JDK install is required.
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /build
COPY pom.xml .
# Cache dependencies in their own layer: this RUN only re-executes when pom.xml changes.
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Runtime stage ----
FROM amazoncorretto:17.0.18-alpine3.23
WORKDIR /app
EXPOSE 8080
COPY --from=build /build/target/TechTome-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
