package org.example.tablo.reservation.dto;

import java.time.LocalDateTime;

public record ReservationSummaryResponse(
        Long id,
        String status,
        int partySize,
        LocalDateTime reservedAt,
        String requestNote,
        LocalDateTime canceledAt,
        LocalDateTime createdAt,
        ReservationStoreResponse store,
        ReservationTableResponse table
) {
}
