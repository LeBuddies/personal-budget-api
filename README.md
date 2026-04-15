# 💰 개인 가계부 REST API

> 부산은행 취업 포트폴리오 프로젝트  
> Spring Boot 3.x + PostgreSQL + JWT 기반 개인 가계부 백엔드 API 서버

---

## 🛠️ 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| ORM | Spring Data JPA |
| DB | PostgreSQL 15 |
| 인증 | Spring Security + JWT |
| 문서화 | Springdoc OpenAPI (Swagger) |
| 빌드 | Gradle |

---

## 📁 프로젝트 구조

```
src/main/java/com/lebuddies/budget/
├── BudgetApplication.java
├── common/
│   ├── response/         # 공통 API 응답 포맷
│   └── exception/        # 글로벌 예외 처리
├── config/
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
└── domain/
    ├── user/             # 회원가입 / 로그인
    ├── category/         # 카테고리 관리
    ├── transaction/      # 수입/지출 내역
    └── statistics/       # 월별 통계
```

---

## 🚀 실행 방법

### 1. PostgreSQL DB 생성

```sql
CREATE DATABASE budget_db;
```

### 2. application.yml 환경 설정

`src/main/resources/application.yml` 에서 DB 정보 수정:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/budget_db
    username: 본인_DB_유저명
    password: 본인_DB_비밀번호
```

### 3. 프로젝트 실행

```bash
git clone https://github.com/LeBuddies/personal-budget-api.git
cd personal-budget-api
./gradlew bootRun
```

또는 IntelliJ에서 `BudgetApplication.java` 실행

### 4. API 문서 확인

```
http://localhost:8080/swagger-ui.html
```

---

## 📐 API 목록

### 인증
| Method | URL | 설명 |
|--------|-----|------|
| POST | /api/auth/signup | 회원가입 |
| POST | /api/auth/login | 로그인 (JWT 발급) |

### 카테고리
| Method | URL | 설명 |
|--------|-----|------|
| GET | /api/categories | 전체 카테고리 조회 |
| POST | /api/categories | 카테고리 생성 |
| PUT | /api/categories/{id} | 카테고리 수정 |
| DELETE | /api/categories/{id} | 카테고리 삭제 |

### 거래 내역
| Method | URL | 설명 |
|--------|-----|------|
| GET | /api/transactions | 내 거래 내역 전체 조회 |
| POST | /api/transactions | 거래 내역 등록 |
| PUT | /api/transactions/{id} | 거래 내역 수정 |
| DELETE | /api/transactions/{id} | 거래 내역 삭제 |

### 통계
| Method | URL | 설명 |
|--------|-----|------|
| GET | /api/statistics/monthly | 월별 수입/지출 통계 |
| GET | /api/statistics/category | 카테고리별 지출 비율 |

---

## 🔐 인증 방법

로그인 후 발급된 JWT 토큰을 모든 요청 헤더에 포함:

```
Authorization: Bearer {accessToken}
```
