# Person-Family

# Postman
Start Postman and import PersonFamily collection:

`File -> Import -> Kodtest.softhouse.postman_collection.json`

# Running docker

`docker run -p 8080:8080 person-family`

## Changing the backend and creating new docker
* mvn package
* change jar, in docker file change:
    * ADD person-family-0.0.1-SNAPSHOT.jar person-family -> ADD target/person-family-0.0.1-SNAPSHOT.jar person-family
* docker build -t person-family:latest .
* docker run -p 8080:8080 person-family

## H2 in-memory database

Every new row will get an incremented id starting at 1.

There is cascade delete on family, it will remove all people that are stored in family.

Everything is stored in an in-memory database meaning when the application will be shutdown everything gets removed.
There are some test data inserted that can be found in SoftHouseBackend/src/main/resources/data.sql
