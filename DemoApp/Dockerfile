FROM openjdk:10-slim
ADD target/SpringBootActuatorScalingK8s-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
