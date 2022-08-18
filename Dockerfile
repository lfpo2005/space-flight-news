FROM maven:3.8.6-jdk-11

ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} space.jar
ENTRYPOINT ["java","-jar","/space.jar"]