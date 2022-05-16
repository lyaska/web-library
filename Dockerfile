FROM openjdk:11
EXPOSE 8080
COPY  ./target/library-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java","-war","app.war"]