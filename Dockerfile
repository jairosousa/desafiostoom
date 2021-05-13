FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD target/desafio.jar desafio.jar
ENTRYPOINT ["java","-jar","/desafio.jar"]