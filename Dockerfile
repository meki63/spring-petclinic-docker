FROM openjdk:8-jdk-alpine

COPY target/spring-petclinic-2.7.0-SNAPSHOT.jar spring-petclinic-2.7.0-SNAPSHOT.jar

ENV PORT 8080
EXPOSE $PORT

ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","spring-petclinic-2.7.0-SNAPSHOT.jar"]