FROM amazoncorretto:17.0.18-alpine3.23
WORKDIR /app
EXPOSE 8080
ENV DB_USER="USER"
ENV DB_PASS="PASS"
#ENV SPRING_DATASOURCE_URL="jdbc:mysql://docker.for.mac.host.internal:3306/tech_tome_app?createDatabaseIfNotExist=true"
ENV SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/tech_tome_app?createDatabaseIfNotExist=true"
COPY TechTomeMVC/TechTome/target/TechTome-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]