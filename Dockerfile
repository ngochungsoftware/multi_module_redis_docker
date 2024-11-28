#Create a lightweight container with only the necessary artifacts
FROM openjdk:21
#Copy the built JAR file from the previous stage into the current stage
COPY blogger-core/target/blogger-core-1.0-SNAPSHOT.jar blogger-core-1.0-SNAPSHOT.jar

ENTRYPOINT  ["java", "-jar", "/blogger-core-1.0-SNAPSHOT.jar"]