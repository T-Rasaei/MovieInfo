## How To Use
You use swagger link or curl command in shell for create request.
1. For first time , create user to access token for authenticate.
("Create user" section in swagger link)
```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/user/create' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "your userName",
  "password": "your password"
}
```
this command return a token.This token need for other section. 
2. If you register before, you authenticate and use other sections. if you forgot your token use "authenticate" section.
("Access to token" section in swagger link)
```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/user/authorize' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "your userName",
  "password": "your password"
}'
```
this command return a token based on username and password that register before.This token need for other section.
3. Find exact title of movie and then use correct title for other section.
("Find title of movie" section in swagger link)
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/movie/searchtitle/titanic' \
  -H 'Authorization: Bearer yourToken'
```
the output of this command is list of movies that contains your title that you have given.
4. Check a movie has oscar or not ?
("Has an Oscar?" section in swagger link)
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/movie/Oscar/titanic?year=1996' \
  -H 'Authorization: Bearer yourToken'
```
this command return true or false.
5. Show list of top 10 movie bas on rating and ordered by imdb score.
("List of top 10 movies" section in swagger link)
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/movie/top10Movies' \
  -H 'Authorization: Bearer YourToken'
```
6. Rate to the movie.
("Rate the movie" section in swagger link)
```shell
  curl -X 'PUT' \
  'http://localhost:8080/api/v1/movie/rating?title=titanic&year=1996&type=movie&rate=THREE' \
  -H 'Authorization: Bearer YourToken'
```