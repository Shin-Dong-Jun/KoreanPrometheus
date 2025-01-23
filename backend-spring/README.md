# 환경설정
## 🛠️ Local 환경 설정 (`application-local.yml`)
`application-local-template.yml` 파일을 복사해서 `application-local.yml`을 생성하세요.
```sh
cp src/main/resources/application-local-template.yml src/main/resources/application-local.yml
```

# DDD 방식
## domain 아래 본인의 디렉토리(e.g. board)에 사용하실 디렉토리를 생성
### e.g. 
- controller
- dto
- entity
- repository
- service

# LLM기반 의류 추천 시스템

## 스프링 - Java, Spring, Mysql(or MariaDB)
### 박영제 - 홈
1. 메인페이지

### 신동준 - 로그인 및 회원가입
1. 스프링 시큐리티
2. JWT

### 이보석 - 게시판
1. 공지사항
   1. 페이지네이션
2. 고객센터
    1. 댓글
    2. 대댓글
### 김동민 - 상품
1. 상품 등록
2. 상품 관리 기능

---

# 최종 배포
0) 로컬서버 반영
1) 상용서버 + nginx + Mysql (or Docker) - 목표
2) 상용서버 + fastapi (or Docker) - 목표
3) 깃액션(CICD) - 희망사항