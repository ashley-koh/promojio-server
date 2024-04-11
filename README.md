# PromoJio Server

<div align="center">
  <div>
    <img src="https://img.shields.io/badge/-Docker-black?style=for-the-badge&logoColor=white&logo=docker&color=61DAFB" alt="docker" />
    <img src="https://img.shields.io/badge/-Spring Boot-black?style=for-the-badge&logoColor=white&logo=spring-boot&color=3CC10E" alt="spring boot" />
    <img src="https://img.shields.io/badge/-Mongo DB-black?style=for-the-badge&logoColor=white&logo=mongodb&color=91CCB3" alt="docker" />
  </div>

  <p align="center">Self-hosted web server for <a href=https://github.com/zayne-siew/PromoJio>PromoJio</a></p>
</div>

## Team 54

| Member                | Student Number |
|-----------------------|----------------|
| Ashley Koh Jia Jhin   | 1004197        |
| Siew Rui Ze, Zayne    | 1007180        |
| Tan Yan Lin, Charlese | 1007075        |
| Khoo Jing Heng        | 1007221        |
| Allison Yee Wen Chyi  | 1007020        |
| Asli Robin Rufo       | 1007212        |
| Austin Isaac          | 1007099        |

## Documentation of Endpoints

1. [User Endpoints](https://github.com/ashley-koh/promojio-server/blob/main/docs/user_endpoints.md)
1. [Promo Endpoints](https://github.com/ashley-koh/promojio-server/blob/main/docs/promo_endpoints.md)

## Requirements

1. [IntelliJ Idea Community Edition](https://www.jetbrains.com/idea/download/?section=windows) - recommended (scroll down to black banner)\
Other IDEs (e.g. VSCode) also viable
2. [Docker](https://docs.docker.com/engine/install/) - follow the installation instructions from the official documentation

## Local Development Setup
1. Select `Get from Version Control` when creating new project.
2. Paste `https://github.com/ashley-koh/promojio-server.git` and select `Clone`
3. Run the following command in a terminal:
```cmd
docker compose up -d mongodb mongo-express
```
4. An instance of MongoDB and MongoExpress should now be running in the background
5. Build and run this git repo in IntelliJ Idea (or your IDE of choice) to run this server
6. Connect to Spring Web Server on `http://localhost:8080`
7. View MongoExpress on `http://localhost:8081`

## Production Setup
1. Update `application.properties` to use mongodb container network
```properties
spring.data.mongodb.uri=mongodb://rootuser:rootpass@mongodb:27017
```
2. In a terminal on the directory of this project, run:
```cmd
mvn clean install
mvn package
docker image build -t ashleykoh24/promojio-server .
docker compose up -d
```
3. Connect to Spring Web Server on: `http://yourserverip:8080`
4. View MongoExpress on: `http://yourserverip:8081`

(Note: This server is currently unsecured)