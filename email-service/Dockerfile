FROM openjdk:14

WORKDIR ./usr/app

EXPOSE 7800

ARG JAR_FILE=mail-service.jar
ADD ./target/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "mail-service.jar"]
