# AdoptOpenJDK 21 버전을 기반으로 하는 도커 이미지
FROM adoptopenjdk:21-jdk-hotspot-bionic

# 작업 디렉토리 설정 (필요에 따라 변경)
WORKDIR /app

# Gradle 관련 파일들을 복사
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle

# 어플리케이션 소스 코드를 복사
COPY src /app/src

# Gradle 빌드를 수행
RUN ./gradlew build

# 어플리케이션 실행 명령어 (필요에 따라 변경)
CMD ["java", "-jar", "build/libs/ACCC-project-0.0.1-SNAPSHOT.jar"]
