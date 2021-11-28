FROM openjdk:17
ADD target/webforumapp-0.0.1-SNAPSHOT.jar webforumapp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT java -jar -Dspring.profiles.active=dev webforumapp-0.0.1-SNAPSHOT.jar