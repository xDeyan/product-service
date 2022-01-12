FROM maven:3.8.4-eclipse-temurin-17-alpine AS build
RUN mkdir /product-service
COPY . /product-service
WORKDIR /product-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
COPY --from=build /product-service/target/product-service-0.0.1-SNAPSHOT.jar /app/product-service-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD [ "java", "-jar", "product-service-0.0.1-SNAPSHOT.jar" ]