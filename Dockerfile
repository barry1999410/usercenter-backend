FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
RUN mkdir /app
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY docker/userCenter-1.0-SNAPSHOT.jar .
# Build a release artifact.

# Run the web service on container startup.
CMD ["java","-jar","/app/userCenter-1.0-SNAPSHOT.jar","--spring.profiles.active=prod"]
