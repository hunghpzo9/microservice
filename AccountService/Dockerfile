FROM openjdk:11
EXPOSE 8083
ADD target/point-service.jar point-service.jar
ENTRYPOINT ["java","-jar","/point-service.jar"]