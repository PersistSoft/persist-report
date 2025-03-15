FROM openjdk:17
VOLUME /tmp
ADD ./target/reports-0.0.1-SNAPSHOT.jar persist-report.jar
ENTRYPOINT ["java", "-jar" , "/persist-report.jar"]