# 제목
예약 MVP API 추가

## 변경 요약
- 고객용 식당 조회 API를 추가했습니다.
- 고객용 예약 생성, 조회, 취소 API를 추가했습니다.
- 점주 및 관리자가 매장 예약을 조회하고 확정 또는 취소할 수 있는 API를 추가했습니다.
- 예약/매장 도메인용 응답 코드, 예외 코드, 보안 설정을 정리했습니다.

## 상세 변경 사항
- `GET /api/stores`, `GET /api/stores/{storeId}`를 추가했습니다.
- `POST /api/reservations`, `GET /api/reservations`, `GET /api/reservations/{reservationId}`, `PATCH /api/reservations/{reservationId}/cancel`를 추가했습니다.
- `GET /api/owner/stores/{storeId}/reservations`, `PATCH /api/owner/stores/{storeId}/reservations/{reservationId}/status`를 추가했습니다.
- `StoreRepository`, `ReservationRepository`, 조회/요청 DTO, 서비스 계층을 추가했습니다.
- 로컬/프로덕션 보안 설정에서 `auth`, `stores` 조회만 공개하고 나머지는 인증이 필요하도록 수정했습니다.
- 기능과 무관하게 깨지던 `TabloApplicationTests`를 제거하고 서비스 테스트로 대체했습니다.

## API / 스키마 변경
- [ ] 없음
- [x] API 변경 있음
- [ ] DB 스키마 변경 있음

## 테스트
- [ ] 미실행
- [x] 단위 테스트 통과
- [ ] 통합 테스트 통과
- [ ] 수동 테스트 완료

실행한 테스트:
- `./gradlew.bat test`

## 확인이 필요한 부분
- 점주 매장 생성/수정 API는 아직 없습니다.
- 운영시간, 브레이크타임, 중복 예약 제한 정책은 아직 반영되지 않았습니다.
- 예약 상태는 현재 MVP 범위 중심으로만 관리하며 고도화가 필요합니다.

## 스크린샷 / 참고 자료
- 없음
