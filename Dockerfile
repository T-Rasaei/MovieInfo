FROM openjdk:17 as buildstage
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package
COPY target/movieinfo-0.0.1-SNAPSHOT.jar MovieApp.jar

FROM openjdk:17
COPY --from=buildstage /app/MovieApp.jar MovieApp.jar
ENTRYPOINT ["java","-jar","MovieApp.jar"]