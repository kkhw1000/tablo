package org.example.tablo.reservation.dto;

public record ReservationTableResponse(
        Long id,
        String name,
        int capacity
) {
}
