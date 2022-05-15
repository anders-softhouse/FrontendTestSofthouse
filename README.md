# Person-Family backend for the Softhouse Front-End test.

# Postman
Start Postman and import your request collection of choice:

`File -> Import -> Postman_remote_collection.json` ( Tip: set your "userId"-header on the entire request collection )
or
`File -> Import -> Postman_local_collection.json` ( Use if running a Docker container on your own computer )

Note: 
* Default id in this collection is 1.
* There are som test data that could be found using `/api/persons` and `/api/families` calls 

# Running docker

* `docker build -t person-family:latest .`
* `docker run -p 8080:8080 person-family`
    * Can also run on different port for example `docker run -p 8081:8080 person-family`

## H2 in-memory database

Every new row will get an incremented id starting at 1.

There is cascade delete on family, it will remove all people that are stored in family.

Everything is stored in an in-memory database meaning when the application will be shutdown everything gets removed.
The inserted test data can be found in SoftHouseBackend/src/main/resources/data.sql

## Optional: Changing the backend and creating new docker

If you for whatever reason want to change anything in the backend to test or something similar follow the following instructions:

* `mvn package`
  * This will create a new jar file, it will be named person-family-0.0.1-SNAPSHOT.jar and will be located in the target folder
* Change in docker file change to read the path to the newly created jar:
    * ADD person-family-0.0.1-SNAPSHOT.jar person-family -> ADD target/person-family-0.0.1-SNAPSHOT.jar person-family
* `docker build -t person-family:latest`
* `docker run -p 8080:8080 person-family`
  * Can also run on different port for example `docker run -p 8081:8080 person-family`

Note: You will only be judged by the original version of this backend
