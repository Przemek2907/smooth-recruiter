FROM openjdk:14

WORKDIR ./usr/app

EXPOSE 8100

ARG JAR_FILE=app.jar
ADD ./target/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "app.jar"]
