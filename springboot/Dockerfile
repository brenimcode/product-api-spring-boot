# Etapa 1: Construção do projeto usando uma imagem base do Ubuntu
FROM ubuntu:latest AS build 

RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \ 
    maven            

WORKDIR /app         

COPY . .             

RUN mvn clean install -DskipTests

# Etapa 2: Execução da aplicação usando uma imagem mais leve
FROM openjdk:17-jdk-slim 

EXPOSE 8080          

COPY --from=build /app/target/springboot-0.0.1.jar app.jar 

ENTRYPOINT [ "java", "-jar", "app.jar" ]
