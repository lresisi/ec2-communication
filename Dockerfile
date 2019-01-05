FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG WAR_FILE=target/assignment-web-0.1.0.war
COPY ${WAR_FILE} app.war
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]
