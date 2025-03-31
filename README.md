# PAWONG (포옹)
포옹이란?

내 위치를 기반으로 반려동물 돌봄 서비스를 매칭할 수 있는 플랫폼입니다.

동물의 발을 뜻하는 `Paw`와 서로 안아주는 행위인 `포옹`의 합성어로 반려동물과 돌봄 이웃을 따뜻하게 연결해 주는 플랫폼이라는 의미를 담고 있습니다.

반려동물을 키우는 보호자와 동네 이웃 펫시터를 연결하여 안전하고 신뢰할 수 있는 돌봄 서비스를 제공합니다.

## 구현한 기능
* 회원가입, 로그인, 로그아웃 (보호자/펫시터)
* 마이페이지 회원 정보 조회

## 앞으로 구현할 기능
* 소셜 로그인 (카카오, 네이버, 구글)
* 이미지 업로드 기능
* 반려동물 프로필 관리
* 실시간 위치 기반 펫시터 매칭
* 실시간 채팅
* 예약 및 결제 시스템
* AWS 배포

## 기술 스택
### Backend
* Java 17
* Spring Boot 3.x
* Spring Security
    * JWT 기반 인증/인가
    * Redis를 활용한 토큰 관리
* Spring Data JPA
    * QueryDSL
* Database
    * MySQL 8.0
    * Redis
* API Documentation
    * Swagger (SpringDoc)

### DevOps
* Docker

### Testing
* JUnit 5
* Mockito

## 프로젝트 구조도
```
src/main/java/com/back/
├── controller/             # API 엔드포인트 정의
│   ├── MemberController      # 회원 관련 API
│   ├── MyPageController      # 마이페이지 API
│   └── PetController         # 반려동물 관련 API
│
├── domain/                 # 도메인 엔티티 정의
│   ├── Member                # 회원 엔티티
│   └── Pet                   # 반려동물 엔티티
│
├── dto/                    # 데이터 전송 객체
│   ├── member/               # 회원 관련 DTO
│   ├── mypage/               # 마이페이지 관련 DTO
│   └── TokenDto              # JWT 토큰 DTO
│
├── service/                # 비즈니스 로직 처리
│   ├── MemberService         # 회원 관련 비즈니스 로직
│   ├── MyPageService         # 마이페이지 관련 비즈니스 로직
│   └── PetService            # 반려동물 관련 비즈니스 로직
│
├── repository/             # 데이터베이스 접근 계층
│   ├── querydsl/             # QueryDSL 구현체
│   ├── MemberRepository      # 회원 데이터 접근
│   └── PetRepository         # 반려동물 데이터 접근
│
└── global/                 # 공통 설정 및 유틸리티
    ├── config/               # 스프링 설정 (Security, JWT, CORS 등)
    ├── exception/            # 예외 처리 관련
    ├── jwt/                  # JWT 인증 관련
    ├── response/             # API 응답 형식 정의
    └── util/                 # 유틸리티 클래스
```