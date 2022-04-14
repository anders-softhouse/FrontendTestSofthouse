FROM openjdk:8
LABEL maintainer="person-family.net"
ADD person-family-0.0.1-SNAPSHOT.jar person-family
ENTRYPOINT ["java", "-jar", "person-family"]
