FROM adoptopenjdk/openjdk17:latest
VOLUME /tmp
EXPOSE 8080
COPY target/bantads-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]