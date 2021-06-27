FROM maven:3.6-jdk-11-slim as build-container
WORKDIR /workdir
COPY pom.xml /workdir/
RUN ["mvn", "verify", "clean", "--fail-never"]
COPY . /workdir/
RUN mvn clean install -DskipTests=true

#from adoptopenjdk/openjdk11
from openjdk:8-slim
MAINTAINER Mihalcea Mihai
COPY entrypoint.sh /usr/local/bin/
RUN apt update -y \
    && apt install apt-utils -y \
    && apt install -y net-tools \
    && apt auto-remove -y \
    && chmod +x /usr/local/bin/entrypoint.sh \
    && mkdir /app


COPY --from=build-container /workdir/target/*.jar /app/
WORKDIR /app
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["springboot-postgres-k8s-0.0.1-SNAPSHOT.jar"]
