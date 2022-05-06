FROM openjdk:8
LABEL maintainer="person-family.net"
ADD target/person-family-0.0.1-SNAPSHOT.jar person-family.jar
ENTRYPOINT ["java", "-jar", "person-family.jar"]
