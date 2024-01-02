FROM openjdk:17
COPY target/movieinfo-0.0.1-SNAPSHOT.jar MovieApp.jar
ENTRYPOINT ["java","-jar","MovieApp.jar"]
EXPOSE 8080