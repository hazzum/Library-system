FROM maven:3-eclipse-temurin-17-alpine AS deps

WORKDIR /app
COPY pom.xml /app

RUN mvn go-offline:resolve-dependencies


FROM maven:3-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY --from=deps /root/.m2/repository /root/.m2/repository
COPY . /app

RUN mvn package -DskipTests -o

FROM maven:3-eclipse-temurin-17-alpine AS dev

WORKDIR /app
COPY --from=deps /root/.m2/repository /root/.m2/repository
COPY ./docker-entrypoint.sh /docker-entrypoint.sh

RUN apk add inotify-tools
RUN chmod +x /docker-entrypoint.sh

EXPOSE 3000

ENTRYPOINT ["/docker-entrypoint.sh"]