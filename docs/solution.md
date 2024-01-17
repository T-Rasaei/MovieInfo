## Movie Information Api
Movie Information Api is a Java-based project developed using Spring Boot (version: 3.2.0) and POstgreSql.
It provides a platform for users to show movie information,check a movie has oscar (based on this [API](http://www.omdbapi.com/) and this [CSV](https://backbase.atlassian.net/wiki/download/attachments/2930016591/academy_awards.csv?version=1&modificationDate=1620402580285&cacheVersion=1&api=v2&download=true) file that
contains winners from 1927 until 2010), rate movies, and receive top 10 best movie based on rating and imdb score.

Here is what this little application demonstrates:
- Full integration with the latest Spring Framework: inversion of control, dependency injection, etc.
- Writing a RESTful service using annotation
- Spring Data Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations.
- Automatic CRUD functionality against the data source using Spring Repository pattern
- Demonstrates MockMVC test framework with associated libraries

### Features
* register user with http://localhost:8080/api/v1/user/create endpoint
* authenticate user with http://localhost:8080/api/v1/user/authenticate endpoint
* find exact title of movie with http://localhost:8080/api/v1/movie/searchtitle endpoint
* check a movie has oscar? with http://localhost:8080/api/v1/movie/Oscar endpoint
* list top-10 movies with http://localhost:8080/api/v1/movie/top10Movies endpoint
* rate to movie with http://localhost:8080/api/v1/movie/rating endpoint