# Build Stage
FROM gradle:8.3-jdk17 AS build

# Build 작업 디렉토리 설정
WORKDIR /app

# gradle 복사
COPY build.gradle settings.gradle ./

# 소스코드파일 복사
COPY . /app

# gradle build
RUN gradle build -x test --no-daemon

# Run Stage
FROM eclipse-temurin:17.0.10_7-jre
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar /app/TinU-0.0.1-SNAPSHOT.jar

# 컨테이너 실행 시 노출할 포트
EXPOSE 8080

# JAR 파일 실행
ENTRYPOINT ["java"]
CMD ["-jar", "-Dspring.profiles.active=prod", "TinU-0.0.1-SNAPSHOT.jar"]
