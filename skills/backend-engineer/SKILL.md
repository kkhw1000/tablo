---
name: backend-engineer
description: Spring Boot backend 구현, 수정, 리뷰용 스킬. 이 저장소의 `backend/` 아래에서 controller, service, entity, repository, database mapping, validation, exception handling, API contract, business rule, backend test 관련 요청이 들어오면 사용한다.
---

# Backend Engineer

## Operating Rule

변경안을 제시하기 전에 관련 백엔드 코드를 먼저 읽는다. 사용자가 기획만 요청한 경우가 아니라면 추상적인 설계 메모보다 실제 구현을 우선한다.

## Repository Context

- 현재 Spring Boot 백엔드 프로젝트는 `backend/` 아래에 있다.
- 주요 애플리케이션 코드는 `backend/src/main/java/org/example/tablo` 아래에 있다.
- 빌드는 Spring Boot 4.0.3과 Java 21을 사용한다.
- 설치된 starter는 Web MVC, Validation, Security, JPA, Redis, AMQP다.
- 사용자가 다른 서비스를 명시하지 않는 한, `backend/`를 백엔드 source of truth로 취급한다.

## Default Workflow

1. 영향 범위에 있는 package를 찾고, 요청 경로에 해당하는 controller, service, domain, repository, DTO, exception 코드를 읽는다.
2. 수정 전에 request/response shape를 end-to-end로 추적한다.
3. 요구사항을 만족하는 가장 작은 단위의 일관된 backend 변경을 구현한다.
4. 기존 test coverage가 있거나, 회귀 가능성이 있는 로직을 건드렸다면 test를 추가하거나 갱신한다.
5. 환경이 허용하면 대상 범위 중심으로 검증을 실행한다.

## Repository Expectations

- 프로젝트가 아직 스켈레톤 단계라면, root package에 클래스를 흩뿌리지 말고 명확한 package 경계를 제안하고 만든다.
- 새 백엔드 코드는 `org.example.tablo` 아래에서 feature 기준 또는 책임 기준 package로 정리하는 쪽을 우선한다.
- 저장소에서 이미 사용 중인 Spring 관례는 유지한다.
- 하나의 클래스나 서비스는 한 가지 변경 이유에 집중한다.
- controller는 HTTP 입출력과 인증/인가 경계에 집중한다.
- service는 여러 객체를 조합하는 유스케이스 흐름과 orchestration에 집중하고, entity를 포함한 domain code는 상태 변화와 불변식 유지에 집중한다.
- repository는 persistence 접근에 집중한다.
- enum, status, exception 변경은 API 동작 변경으로 간주하고 caller 영향을 함께 확인한다.
- persistence를 수정할 때는 entity mapping, repository query, transaction boundary를 함께 검증한다.
- 클린 코드 원칙을 추상 구호로 쓰지 말고, 명확한 이름, 작은 단위의 메서드, 중복 억제, 불필요한 추상화 회피로 드러낸다.
- 성능은 실제 병목이 생기기 쉬운 쿼리 수, 불필요한 조회, 컬렉션 처리 비용을 먼저 점검하고, 근거 없는 과도한 최적화는 피한다.
- service 계층에는 요청 흐름과 주요 상태 변화를 추적할 수 있는 로그를 남기되, 비밀번호, 토큰 원문, 민감정보는 로그에 남기지 않는다.

## Review Focus

behavioral regression, 잘못된 상태 전이, 깨진 API contract, 누락된 validation, N+1 스타일 data access 문제, 부족한 test coverage를 우선적으로 본다.
