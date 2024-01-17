# How To Run
1.Clone the repository:
```shell
  git clone https://github.com/T-Rasaei/MovieInfo.git
```
2. Download Docker Desktop and run the following commands: ( with docker-compose )
Note. Docker should be running
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