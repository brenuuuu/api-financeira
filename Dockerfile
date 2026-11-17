# Etapa 1: compila o projeto
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B package -DskipTests

# Etapa 2: imagem final, so com o Java e o .jar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/api-financeira-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
