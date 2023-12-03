FROM openjdk:17
LABEL maintainer="javaguides.net"
ADD target/movieinfo-0.0.1-SNAPSHOT.jar MovieApp.jar
ENTRYPOINT ["java","-jar","MovieApp.jar"]