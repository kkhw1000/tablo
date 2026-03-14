---
name: frontend-engineer
description: Frontend 구현, 수정, 리뷰용 스킬. 이 저장소의 `frontend/` 또는 연결된 frontend workspace에서 page, component, styling, state update, UX 동작, form, client-side validation, API integration, frontend test 관련 요청이 들어오면 사용한다.
---

# Frontend Engineer

## Operating Rule

수정 전에 기존 UI 패턴을 먼저 확인한다. 사용자가 새로운 방향을 명시하지 않는 한 현재 design system을 유지한다.

## Repository Context

- 현재 프론트엔드 작업 기본 위치는 `frontend/`다.
- frontend 작업은 다음 두 경우 중 하나로 본다:
  1. 이 저장소의 `frontend/` 아래에서 작업하는 경우
  2. 사용자가 제공한 별도 frontend workspace에서 작업하는 경우
- `frontend/`가 아직 비어 있거나 초기 상태라면 있는 척하지 않는다. 먼저 범위를 정의하거나, 초기 구조를 명시적으로 만든다.

## Default Workflow

1. 영향받는 screen, component tree, style, API touchpoint를 식별한다.
2. UI code를 바꾸기 전에 state flow, loading/error handling, user action을 확인한다.
3. component 경계가 명확하고 state update를 예측할 수 있게 UI 변경을 구현한다.
4. layout 변경이 포함되면 desktop과 mobile 동작을 함께 확인한다.
5. 환경이 가능하면 관련 check나 build command를 실행한다.

## Implementation Standard

- 과도하게 추상화된 helper보다 읽기 쉬운 component를 우선한다.
- 수정한 코드 안에서 API contract 가정은 명시적으로 드러나게 유지한다.
- empty, loading, failure state를 부가 처리로 보지 말고 핵심 동작으로 다룬다.
- label, focus order, actionable feedback 같은 기본 접근성은 유지한다.

## Review Focus

깨진 interaction, stale state, 누락된 edge state, API mismatch, layout regression, 기존 패턴과 충돌하는 styling 변경을 우선적으로 본다.

## Handoff Rule

frontend 작업이 정의되지 않은 backend contract에 의존하면, request/response shape를 임의로 만들기 전에 `product-planner`를 사용하거나 backend code를 먼저 확인한다.
