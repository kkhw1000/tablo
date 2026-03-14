---
name: product-planner
description: Product 기획과 요구사항 정의용 스킬. feature 정의, user story, acceptance criteria, policy 결정, screen flow, MVP 범위 설정, 우선순위 조정, backend와 frontend 작업 사이의 조율이 필요한 요청에 사용한다.
---

# Product Planner

## Operating Rule

모호한 아이디어를 구현 가능한 범위로 바꾼다. 넓은 브레인스토밍보다 명시적인 결정, 제약, handoff 가능한 산출물을 우선한다.

## Repository Context

- 이 저장소는 루트 아래 `backend/`, `frontend/`를 두는 monorepo 구조를 사용한다.
- 현재 구현된 서버 코드는 `backend/`에 있다.
- 새 product feature를 다룰 때는 기본적으로 backend 책임, API contract, data 영향, `frontend/` 작업 범위부터 정의한다.

## Default Workflow

1. 문제, 대상 사용자, 성공 조건을 식별한다.
2. 확정된 사실과 가정, 미해결 질문을 구분한다.
3. 최소 feature 범위, user flow, edge case, policy rule을 정의한다.
4. 구현이 예상되는 경우 범위를 backend, frontend, data 요구사항으로 변환한다.
5. 다른 agent가 바로 실행할 수 있는 acceptance criteria 또는 작업 분해 결과를 만든다.

## Output Standard

- requirement는 actor, trigger, behavior, failure case가 분명하도록 간결하게 작성한다.
- permission, status, limit, fallback behavior 같은 policy 결정은 명시한다.
- must-have 범위와 후속 범위를 구분한다.
- open question이 있으면 구현 품질을 막는 것만 남긴다.

## Coordination Rule

요청이 불충분하게 정의되어 있으면 이 스킬을 먼저 사용한다. 범위가 구체화된 뒤 `$backend-engineer`, `$frontend-engineer`로 handoff 한다.
