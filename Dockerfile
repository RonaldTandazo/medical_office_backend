# --- Etapa 1: Construcción (Build) ---
FROM gradle:8.7-jdk22 AS build
WORKDIR /app

# Copiamos los archivos de configuración de Gradle primero (para aprovechar el cache de Docker)
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
COPY src ./src

# Damos permisos de ejecución al wrapper y compilamos
# Usamos bootJar (específico de Spring Boot) para generar el ejecutable
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# --- Etapa 2: Ejecución (Run) ---
FROM eclipse-temurin:22-jre-jammy
WORKDIR /app

# En Gradle, el archivo generado suele estar en build/libs/
COPY --from=build /app/build/libs/*.jar app.jar

# Configuración de memoria para el plan gratuito de Render
ENV JAVA_OPTS="-Xms128m -Xmx300m -XX:+UseG1GC"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
