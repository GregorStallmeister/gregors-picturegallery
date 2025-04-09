FROM eclipse-temurin:23
EXPOSE 8080
COPY backend/target/gregors-picturegallery.jar /gregors-picturegallery.jar

ENTRYPOINT ["java", "-jar", "/gregors-picturegallery.jar"]