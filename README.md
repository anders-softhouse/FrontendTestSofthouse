# Person-Family backend for the Softhouse Front-End test.

# Postman
Start Postman and import your request collection of choice:

`File -> Import -> Postman_remote_collection.json`  
	* Tip-1: click on collection -> variables -> userId to set this request header for the entire collection.  
	* Tip-2: Use your email address for this value.  
  
or  
  
`File -> Import -> Postman_local_collection.json`  
	* Use if running a Docker container on your own computer.  

# Running docker

* `docker build -t person-family:latest .`
* `docker run -p 8080:8080 person-family`

## H2 in-memory database

Every new row will get an incremented id starting at 1.

There is cascade delete on family, it will remove all people that are stored in family.

Everything is stored in an in-memory database meaning when the application will be shutdown everything gets removed.
The inserted test data can be found in SoftHouseBackend/src/main/resources/data.sql

## Optional: Changing the backend and creating new docker

If you for whatever reason want to change anything in the backend to test or something similar follow the following instructions:

* Change directory to root of project
* `mvn package`
* `docker build -t person-family:latest .`
* `docker run -p 8080:8080 person-family`

Note: You will only be judged by the original version of this backend
