# Stage 1: build with Gradle
FROM gradle:8-jdk17 AS builder
WORKDIR /home/gradle/project

# copy files
COPY --chown=gradle:gradle . .

# ensure gradlew is executable (fix permission issues)
RUN chmod +x ./gradlew

# Debug: list files (comment out later)
RUN ls -la /home/gradle/project

# try to print gradle version to ensure wrapper works
RUN ./gradlew --version

# Build: prefer bootJar, fallback to build
RUN set -ex && \
    if ./gradlew clean bootJar -x test; then \
      echo "bootJar succeeded"; \
    else \
      echo "bootJar failed, trying build"; \
      ./gradlew clean build -x test; \
    fi

# Stage 2: runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# copy jar (use wildcard; if multiple match, Docker will fail — usually one)
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
