# Softhouse / test / frontend : `person-family`
Docker image ( local ) and online application ( remote ) gives access to a backend for the person-family frontend test.


## Postman
Start Postman and import your request collection of choice:

* `File -> Import -> Postman_remote_collection.json`  
   * Tip-1: click on collection -> variables -> userId to set this request header for the entire collection.  
   * Tip-2: Use your email address for this value.  
  
or  
  
* `File -> Import -> Postman_local_collection.json`  
   * Use if running a Docker container on your own computer.   


## Running the backend as a local docker container

### Starting your container
* `docker run -dp 8080:8080 images4softhouse/person-family`

### Stopping your container
* `docker stop` UNIQUE-ID-OF-YOUR-CONTAINER-HERE   

## H2 in-memory database

Every new row will get an incremented id starting at 1.

There is cascade delete on family, it will remove all people that are stored in family.

Everything is stored in an in-memory database meaning that when the application is shutdown, everything gets removed.
The test data can be found in src/main/resources/data.sql   

## Updating the backend, building and running a new Docker container.
`Optional`

If you would like to update the docker image for your ( local )  backend:

* Change directory to the root of the project
* `mvn package`
* `docker build -t person-family:latest .`
* `docker run -dp 8080:8080 person-family`

Note: You will only be judged by the original version of this backend
