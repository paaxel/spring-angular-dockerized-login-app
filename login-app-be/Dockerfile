FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

RUN apk update && apk upgrade

COPY ./docker-resources/wait-and-init.sh /opt/wait-and-init.sh

RUN chmod +x /opt/wait-and-init.sh

RUN chown -R spring:spring /opt

USER spring:spring

ARG JAR_FILE=./web/target/*.jar

COPY ${JAR_FILE} app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]