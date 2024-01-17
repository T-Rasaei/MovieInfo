# Scale
Typically, 10-15k requests per second can be handled by one web server for a dynamic website, but it depends totally on complexity of website/web application.
if number of users/agents/consumers grows we can use load balancer.
Load balancer contains multiple web servers and just forwards incoming requests to one of them to distribute.

Note. Reading from external api is expensive. We cache fraction of data(recently accessed) to the local database. If data is not there in local database,  then read from external api and save it in local database for next time.
