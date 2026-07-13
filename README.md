# Today's Casting Backend

Today's Casting 서비스의 Spring Boot 백엔드 서버입니다.

## 기술 스택

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring Security
- MySQL 8
- Flyway
- Swagger / Springdoc OpenAPI
- Docker, Docker Compose

## 필요 환경

- Java 21
- Docker Desktop
- Docker Compose

## 환경변수 설정

프로젝트 루트에서 `.env.example` 파일을 복사해 `.env` 파일을 생성합니다.

Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

macOS / Linux:

```bash
cp .env.example .env
```

기본 로컬 환경변수는 다음과 같습니다.

```env
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=local

MYSQL_DATABASE=todays_casting
MYSQL_USER=todays
MYSQL_PASSWORD=change-me
MYSQL_ROOT_PASSWORD=change-me

LOCAL_DB_URL=jdbc:mysql://localhost:3306/todays_casting?serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true
LOCAL_DB_USERNAME=todays
LOCAL_DB_PASSWORD=change-me

PROD_DB_URL=jdbc:mysql://rds-endpoint:3306/todays_casting?serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useSSL=false
PROD_DB_USERNAME=prod-user
PROD_DB_PASSWORD=change-me

TZ=Asia/Seoul
```

`.env` 파일에는 실제 비밀번호, API Key 같은 민감한 값이 들어갈 수 있으므로 Git에 올리지 않습니다.

## Docker Compose 실행

Spring Boot 애플리케이션과 MySQL을 함께 실행합니다.

```bash
docker compose up --build -d
```

컨테이너 상태 확인:

```bash
docker compose ps
```

애플리케이션 로그 확인:

```bash
docker compose logs -f app
```

컨테이너 종료:

```bash
docker compose down
```

## 로컬 확인 URL

- 서버 상태 확인: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## MySQL 접속 정보

일반 개발에서는 아래 계정을 사용합니다.

```text
Host: 127.0.0.1
Port: 3306
Database: todays_casting
Username: .env MYSQL_USER / LOCAL_DB_USERNAME
Password: .env MYSQL_PASSWORD / LOCAL_DB_PASSWORD
```

root 계정은 MySQL 컨테이너 초기화와 긴급 DB 관리 용도로만 사용합니다.

```text
Username: root
Password: .env MYSQL_ROOT_PASSWORD
```

## Flyway 마이그레이션

DB 마이그레이션 파일 위치:

```text
src/main/resources/db/migration
```

파일명 규칙:

```text
V1__init.sql
V2__create_users.sql
V3__create_daily_records.sql
```

현재 JPA 설정은 다음과 같습니다.

```yml
ddl-auto: validate
```

따라서 테이블 변경은 Hibernate 자동 생성이 아니라 Flyway 마이그레이션 파일을 통해 관리합니다.

## GitHub 업로드

원격 저장소:

```text
https://github.com/likelion14th-hackathon/backend.git
```

처음 업로드할 때:

```bash
git init
git add .
git commit -m "chore: initial backend setup"
git branch -M main
git remote add origin https://github.com/likelion14th-hackathon/backend.git
git push -u origin main
```

`.env`는 `.gitignore`에 포함되어 있으므로 Git에 올라가지 않습니다.

## AWS EC2 1차 배포

현재 구조는 RDS 없이 EC2 내부에서 Docker Compose로 Spring Boot 애플리케이션과 MySQL을 함께 실행하는 방식입니다.

1. EC2 인스턴스를 생성합니다.
   - Ubuntu 22.04 또는 24.04 권장
   - 해커톤 초기에는 `t3.micro` 또는 `t2.micro` 사용 가능

2. 보안 그룹 인바운드 규칙을 설정합니다.
   - SSH: `22`
   - 백엔드 API: `8080`
   - 추후 Nginx/HTTPS 적용 시: `80`, `443`

3. EC2에 접속합니다.

```bash
ssh -i <키페어파일.pem> ubuntu@<EC2_PUBLIC_IP>
```

4. Docker, Docker Compose, Git을 설치합니다.

```bash
sudo apt update
sudo apt install -y docker.io docker-compose-plugin git
sudo systemctl enable docker
sudo systemctl start docker
sudo usermod -aG docker ubuntu
```

권한 적용을 위해 SSH 접속을 끊고 다시 접속합니다.

5. 프로젝트를 clone합니다.

```bash
git clone https://github.com/likelion14th-hackathon/backend.git
cd backend
```

6. `.env` 파일을 생성합니다.

```bash
cp .env.example .env
```

필요하면 `.env` 값을 수정합니다.

```bash
nano .env
```

7. Docker Compose로 실행합니다.

```bash
docker compose up --build -d
```

8. 배포 상태를 확인합니다.

```bash
docker compose ps
docker compose logs -f app
```

브라우저에서 아래 주소를 확인합니다.

```text
http://<EC2_PUBLIC_IP>:8080/actuator/health
http://<EC2_PUBLIC_IP>:8080/swagger-ui.html
```

## 참고 사항

- 현재 배포 구조는 Docker Compose 내부 MySQL을 사용합니다.
- RDS는 추후 필요할 때 `PROD_DB_URL`, `PROD_DB_USERNAME`, `PROD_DB_PASSWORD`를 외부 DB 기준으로 변경해 도입합니다.
- Redis, S3, Kakao Login, Gemini, GitHub Actions CI/CD는 관련 기능 구현 시점에 추가합니다.
