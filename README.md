# MioBook — Internet Engineering Project

A collaborative web application with a React client and a Java/Spring Boot backend. The backend is organized into domain, application, infrastructure, and Web API modules, with MySQL persistence, Redis sessions, and Google OAuth configuration.

## Structure

| Path | Purpose |
| --- | --- |
| `server/Domain/` | Domain entities |
| `server/Application/` | Application services |
| `server/Infra/` | Persistence and authentication utilities |
| `server/WebApi/` | HTTP application and configuration |
| `webclient/` | React browser application |
| `prod/` | Original container deployment configuration |

## Backend

Requirements: JDK 21, Maven, MySQL, and Redis.

Review `server/WebApi/src/main/resources/application.properties`. Supply database, Redis, Google OAuth, and JWT settings through the environment. This public copy replaces credential values with Spring environment placeholders.

```sh
cd server
mvn package
java -jar WebApi/target/web-api-1.0-SNAPSHOT.jar
```

The default backend uses port 8080 and the `/api` context path. Configure the OAuth redirect URI and provider metadata to match your development environment.

## Frontend

In another terminal:

```sh
cd webclient
npm ci
npm start
```

Review the client API URLs and proxy settings when connecting it to your backend.

## Container configuration

The original `prod/docker-compose.yml` references prebuilt project images and local Docker secret files. Those secret files are not included. Provide your own images and secrets or adapt the configuration to your environment.

`JWT_SECRET_KEY` must be a strong value of at least 32 bytes. This snapshot loads it from the environment instead of publishing the original signing value.

## Credits and source

This is a public portfolio copy of the shared project [MMDSour/IE-MewoBook](https://github.com/MMDSour/IE-MewoBook), prepared for Sobhan Alaeddini's account. Original authorship and existing license notices are retained. See [SOURCE.md](SOURCE.md) for the source revision and publication notes.
