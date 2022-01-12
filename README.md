<!-- @format -->

# PRODUCT-SERVICE

**docker image build . -t user-service:1.0.0-SNAPSHOTTT**

## How to run

To build and run locally, make sure u have [JDK 17](https://adoptium.net/) installed:

- run **./mvnw spring-boot:run** in the root of the project folder
- navigate to http://localhost:8080/swagger-ui/index.html to view the swagger ui.

To build and run as a docker container:

- run **docker image build . -t user-service:latest**
- run **docker container run -p 8080:8080 user-service:latest**
- navigate to http://localhost:8080/swagger-ui/index.html to view the swagger ui.
