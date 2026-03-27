## Skills
`Skill`은 따라야 할 로컬 작업 지침 묶음이며, 각 스킬은 `SKILL.md` 파일에 정의됩니다. 아래는 현재 세션에서 사용할 수 있는 스킬 목록입니다. 각 항목에는 이름, 설명, 파일 경로가 포함되어 있으므로 필요한 경우 원문을 직접 열어 확인합니다.

### Available skills
- backend-engineer: `backend/` 아래 Spring Boot 백엔드 구현과 리뷰에 사용합니다. 요청이 controller, service, entity, repository, validation, exception handling, database 변경, API contract, backend test를 포함하면 적용합니다. (file: C:/Users/USER-PC/Desktop/Tablo/skills/backend-engineer/SKILL.md)
- frontend-engineer: `frontend/` 아래 프론트엔드 구현과 리뷰에 사용합니다. 요청이 screen, component, styling, state 관리, API integration, UX 동작, frontend test를 포함하면 적용합니다. (file: C:/Users/USER-PC/Desktop/Tablo/skills/frontend-engineer/SKILL.md)
- product-planner: 기능 기획과 범위 정의에 사용합니다. 요청이 requirement, user flow, policy, acceptance criteria, MVP 범위, 우선순위, backend/frontend handoff planning을 포함하면 적용합니다. (file: C:/Users/USER-PC/Desktop/Tablo/skills/product-planner/SKILL.md)

### How to use skills
- Discovery: 위 목록이 현재 세션에서 사용 가능한 스킬 전체입니다. `name`, `description`, `file path`를 기준으로 어떤 스킬을 써야 하는지 판단합니다. 실제 스킬 본문은 각 경로의 파일에 있습니다.
- Trigger rules: 사용자가 스킬 이름을 직접 언급했거나 (`$SkillName` 또는 일반 텍스트), 작업 내용이 위 설명과 명확히 일치하면 해당 턴에서 반드시 그 스킬을 사용합니다. 여러 스킬이 동시에 맞으면 모두 사용하되, 다음 턴으로 자동 이월하지 않습니다.
- Missing/blocked: 사용자가 언급한 스킬이 목록에 없거나 파일을 읽을 수 없으면 그 사실만 짧게 알리고, 가능한 최선의 대체 방식으로 계속 진행합니다.
- How to use a skill (progressive disclosure):
  1. 사용할 스킬을 정했으면 해당 `SKILL.md`를 엽니다. 워크플로를 따르는 데 필요한 만큼만 읽습니다.
  2. `SKILL.md`에 상대 경로가 나오면, 먼저 위에 적힌 해당 스킬 디렉터리를 기준으로 경로를 해석합니다.
  3. `references/` 같은 추가 폴더가 있으면 요청 처리에 필요한 파일만 선택해서 읽습니다.
  4. `scripts/`가 있으면 긴 코드를 다시 쓰기보다 실행하거나 patch 해서 활용하는 쪽을 우선합니다.
  5. `assets/`나 template이 있으면 새로 만들기보다 재사용을 우선합니다.
- Coordination and sequencing:
  - 여러 스킬이 적용될 수 있으면 요청을 처리하는 데 필요한 최소 조합만 고르고, 사용할 순서를 짧게 밝힙니다.
  - 어떤 스킬을 왜 쓰는지 한 줄로 알립니다.
- Context hygiene:
  - 컨텍스트를 불필요하게 키우지 않습니다. 긴 내용은 그대로 붙여넣기보다 요약합니다.
  - 참조 파일을 깊게 따라가기보다, 우선 `SKILL.md`에서 직접 연결된 파일만 엽니다.
- Safety and fallback: 스킬을 깔끔하게 적용할 수 없으면 이유를 짧게 설명하고, 차선책을 선택해 계속 진행합니다.

### Repository structure
- 이 저장소는 monorepo 형태로 관리합니다.
- 백엔드 코드는 `backend/` 아래에 둡니다.
- 프론트엔드 코드는 `frontend/` 아래에 둡니다.
- 공통 작업 규칙과 스킬 진입점은 루트 `AGENTS.md`와 `skills/`에서 관리합니다.

### Commit message convention
- Git 커밋 메시지는 `feat:`, `fix:`, `chore:` 같은 타입 prefix는 유지합니다.
- prefix 뒤 설명은 한글로 작성합니다.
- 예시: `feat: 로그인 예외 처리 추가`, `fix: 주문 상태 갱신 버그 수정`
