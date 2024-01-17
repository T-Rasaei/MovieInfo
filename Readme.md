## Movie Information Api
Movie Information Api is a Java-based project developed using Spring Boot (version: 3.2.0) and POstgreSql . It provides a platform for users to show movie information, rate movies, and receive top 10 best movie based on rating and imdb score.

Here is what this little application demonstrates:

    Full integration with the latest Spring Framework: inversion of control, dependency injection, etc.
    Writing a RESTful service using annotation
    Spring Data Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations.
    Automatic CRUD functionality against the data source using Spring Repository pattern
    Demonstrates MockMVC test framework with associated libraries

Here are some endpoints you can call:
Get information about Movie, rating, etc.

* http://localhost:8080/api/v1/user/create
* http://localhost:8080/api/v1/user/authenticate
* http://localhost:8080/api/v1/movie/searchtitle
* http://localhost:8080/api/v1/movie/Oscar
* http://localhost:8080/api/v1/movie/top10Movies
* http://localhost:8080/api/v1/movie/rating

# Features
* register user
* authenticate user
* find exact title of movie
* check a movie has oscar?
* list top-10 movies
* rate to movie

# Installation
1.Clone the repository:
```shell
  git clone https://github.com/T-Rasaei/MovieInfo.git
```
2. Download Docker Desktop and run the following commands: ( with docker-compose )
```shell
# download postgresql image
docker pull postgresql:latest
```
```shell
# run the project
 docker compose up
```
Once the application runs you should see something like this
```shell
movieApp      | 2024-01-17T06:22:10.989Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
movieApp      | 2024-01-17T06:22:11.002Z  INFO 1 --- [           main] c.m.movieinfo.MovieinfoApplication       : Started MovieinfoApplication in 5.234 seconds (process running for 5.944)
```
3. Run the server and browse to http://localhost:8080/swagger-ui/index.html

# How To Use Project
You use swagger link or curl command in shell for create request.
1. For first time , create user. 
```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/user/create' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "your userName",
  "password": "your password"
}
```
2. If you register before, you authenticate and use other sections. if you forgot your token use "authenticate" section.
```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/user/authorize' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "your userName",
  "password": "your password"
}'
```
3. for find exact title of movie use "Find title of movie" section. the output of this section is list of movies that contains your title that you given.
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/movie/searchtitle/titanic' \
  -H 'Authorization: Bearer yourToken'
```
4. For check a movie has oscar or not you can use "Has an Oscar?" section.
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/movie/Oscar/titanic?year=1996' \
  -H 'Authorization: Bearer yourToken'
```
5. For show list of top 10 movie bas on rating and ordered by imdb score use "List of top 10 movies" section.
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/movie/top10Movies' \
  -H 'Authorization: Bearer YourToken'
```
6. For rate to the movie use "Rate the movie" section.
```shell
  curl -X 'PUT' \
  'http://localhost:8080/api/v1/movie/rating?title=titanic&year=1996&type=movie&rate=THREE' \
  -H 'Authorization: Bearer YourToken'
```

# How To Test
For this project wrote 23 unit test and 7 integration test uses JUnit tests and Testcontainers to run them against actual databases running in containers.
Run the command to run the tests.
```shell
mvn test
```
Note: need install openjdk-17


