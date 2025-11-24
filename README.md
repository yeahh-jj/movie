# Movie Reservation & Review Web Application

> **Spring Boot · MyBatis · REST API · JS 기반 영화 예매 & 리뷰 웹 서비스**  
> 관리자는 영화를 등록·관리하고, 사용자는 영화 조회 → 예매 → 리뷰까지 가능한 풀스택 프로젝트입니다.  
> 백엔드는 Spring REST API, 프론트는 HTML/CSS/JS로 직접 구현했습니다.

---

## 주요 기능

### 관리자(Admin)

- **영화 등록**
    - 제목 / 감독 / 장르 / 줄거리 / 상영 시간 등록
- **영화 관리**
    - 영화 목록 조회
    - 영화 수정 / 삭제
- **상영 일정 등록**
    - 날짜, 시간, 상영관 입력

---

### 사용자(User)

#### 1) 영화 조회

- 전체 영화 목록 조회
- 상세 페이지에서 줄거리, 감독, 러닝타임 확인
- 평균 평점 표시

#### 2) 예매 기능

- 영화 선택 → 상영관 → 시간 선택
- 좌석 중복 예약 방지
- 예매 내역 확인 가능

#### 3) 리뷰 기능

- **예매한 영화만 리뷰 작성 가능** (선관람 후 리뷰)
- 평점(0.5 단위), 리뷰 텍스트 작성
- 리뷰 수정 / 삭제
- 중복 리뷰 작성 방지

#### 4) 리뷰 조회

- 영화별 리뷰 목록 조회
- 평균 평점 자동 계산

---

## 기술 스택

### Backend

- Spring Boot 3.x
- Spring MVC
- MyBatis
- PostgreSQL / H2 (개발용)
- Lombok
- Gradle

### Frontend

- HTML / CSS
- Vanilla JavaScript
- Bootstrap

### Deploy & Infra

- Railway (배포)
- PostgreSQL (Railway / Supabase)
- GitHub (CI 연동)

---

## 구현 주요 포인트

### REST API 기반 분리

- 프론트는 HTML+JS
- 백엔드는 순수 REST API

### 세션 로그인 방식

- `session.setAttribute("userId")`
- 관리자 계정은 email/password 하드코딩으로 식별

### MyBatis 직접 작성

- mapper.xml 직접 작성
- camelCase 자동 매핑

### 예외 처리(GlobalExceptionHandler)

- IllegalArgumentException 공통 처리
- JSON 에러 응답 통일

### 리뷰 정책 완료 구현

- 예매한 사용자만 리뷰 작성 가능
- 본인 리뷰만 수정/삭제
- 중복 리뷰 방지

---

## 실행 방법

### 배포 주소(모바일 환경 최적화 버전)

**https://movie-production-2cbc.up.railway.app/movie/login.html**

### 로컬 실행

```bash
./gradlew bootRun
