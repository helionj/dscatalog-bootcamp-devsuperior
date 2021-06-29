FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/dscatalog.jar dscatalog.jar
ENTRYPOINT ["java","-jar","/dscatalog.jar"]