FROM eclipse-temurin:17 as builder
WORKDIR application
COPY target/team-0.0.1-SNAPSHOT.jar team.jar
RUN java -Djarmode=layertools -jar team.jar extract



FROM eclipse-temurin:17
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
