# ---- Build stage ----
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Cache de dependencias: copiamos primero el wrapper y el pom para
# aprovechar la cache de capas de Docker cuando solo cambia el código fuente.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

# Copiamos el código y construimos el jar (sin correr tests dentro de la imagen)
COPY src/ src/
RUN ./mvnw -q -B clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Usuario no root por seguridad
RUN groupadd -r spring && useradd -r -g spring spring

COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
