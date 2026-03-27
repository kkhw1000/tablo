package org.example.tablo.reservation.dto;

import java.time.LocalDateTime;

public record ReservationDetailResponse(
        Long id,
        Long userId,
        String userName,
        String userPhoneNumber,
        String status,
        int partySize,
        LocalDateTime reservedAt,
        String requestNote,
        LocalDateTime canceledAt,
        LocalDateTime seatedAt,
        LocalDateTime createdAt,
        ReservationStoreResponse store,
        ReservationTableResponse table
) {
}
