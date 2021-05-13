# Projeto Desafio Stoom

O Projeto consiste em desenvolver api Rest que disponibiliza serviço para salvar, buscar, atualizar e deletar um endereço e faz comunicação com serviço de geolocalização do Google.

## Tecnologias utilizada

* Spring Boot
  * Spring Data JPA
  * Spring Web

* Google Geocoding API

* H2 Database
* Lombok
* JUnit
* Mockito
* Docker

## Docker

* Antes Gerar build do projeto 
```
mvnw clean package
```

* Imagem
```
docker build -t desafio:v1 .
```

* Container
```
docker run -p 8080:8080 --name desafio desafio:v1
```

* Docker-Compose
Na pasta docker esta o arquivo docker-compose.yml

Criar a imagem
```
docker-compose build
```
Start container
```
docker-compose up -d
```
