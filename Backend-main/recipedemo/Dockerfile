# 1. 기본 JDK 이미지 설정
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 이름을 변수로 처리
ARG JAR_FILE=build/libs/recipedemo-0.0.1-SNAPSHOT.jar

# 4. JAR 파일 복사
COPY ${JAR_FILE} app.jar

# 5. 환경 변수 추가 (필요 시)
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# 6. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
#  애플리케이션 실행 로그
  #ENTRYPOINT를 CMD로 바꾸면 디버깅 및 로그 확인이 더 쉬워질 수 있습니다.
  #
  #dockerfile
  #코드 복사
  #CMD ["java", "-jar", "app.jar"]

  #ENTRYPOINT와 CMD의 차이는:
     #
     #ENTRYPOINT는 기본 실행 명령으로 고정되며 덮어쓸 수 없습니다.
     #CMD는 컨테이너 실행 시 덮어쓸 수 있는 기본 실행 명령입니다.



#FROM openjdk:17
#COPY build/libs/recipedemo.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
